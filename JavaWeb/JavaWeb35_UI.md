# 网上家具商城项目（用户操作）

## 用户登录

1. 会员登录成功后，在页面上方显示username
2. 如果用户成功登录，网站首页显示订单管理和安全退出页面
3. 如果用户没有登录过，网站首页显示登录/注册超链接

代码实现

后端修改 MemberServlet 的 login 方法，对登陆成功的用户添加 session

前端将 login_ok.html 改为 login_ok.jsp，显示用户名和菜单选项Header Action

```jsp
<!-- Header Action Start -->
<div class="col align-self-center">
    <div class="header-actions">
        <!-- Single Wedge Start -->
        <div class="header-bottom-set dropdown">
            <a>欢迎：${sessionScope.member.username}</a>
        </div>
        <div class="header-bottom-set dropdown">
            <a href="#">订单管理</a>
        </div>
        <div class="header-bottom-set dropdown">
            <a href="memberServlet?action=logout">安全退出</a>
        </div>
        <!-- Single Wedge End -->
    </div>
</div>
<!-- Header Action End -->
```

修改 index.jsp，利用 jstl 标签和 EL 表达式，结合session的属性进行显示

```jsp
<c:if test="${not empty sessionScope.member}">
    <div class="header-bottom-set dropdown">
        <a>欢迎：${sessionScope.member.username}</a>
    </div>
    <div class="header-bottom-set dropdown">
        <a href="#">订单管理</a>
    </div>
    <div class="header-bottom-set dropdown">
        <a href="memberServlet?action=logout">安全退出</a>
    </div>
</c:if>
```

## 安全退出

后端 web 层，在 MemberServlet 中增加 logout 方法，将 session 注销，并重定向到 index.jsp

```java
protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //销毁当前用户的session
    req.getSession().invalidate();
    //默认放回网站首页的index.jsp文件
    resp.sendRedirect(req.getContextPath());
}
```

## 验证码

表单重复提交情况：

1. 提交完表单。服务器使用请求转发进行页面跳转。用户刷新（F5），会发起最后一次的请求，造成表单重复提交问题。解决：用重定向
2. 用户正常提交，由于网络延迟等原因，未收到服务器的响应，这时，用户着急多点了几次提交操作，也会造成表单重复提交。解决：验证码
3. 用户正常提交，服务器也没有延迟，但是提交完成后，用户回退浏览器。重新提交。也会造成表单重复提交，解决：验证码
4. 恶意注册，使用可以批量发送Http的工具，比如Postman，Jemeter等..，使用验证码防护

### 代码实现

在 lib 目录中导入外部包 kaptcha-2.3.2.jar，配置web.xml文件

```xml
<servlet>
    <servlet-name>KaptchaServlet</servlet-name>
    <servlet-class>com.google.code.kaptcha.servlet.KaptchaServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>KaptchaServlet</servlet-name>
    <url-pattern>/kaptchaServlet</url-pattern>
</servlet-mapping>
```



后端新增 KaptchaServlet 返回验证码，并保存到Session中，将验证码图片以 HTTP 方式返回。在 MemberServlet 的 register方法中取出验证码值，和服务器端的进行核对。

* 为防止重复提交，从session中取出验证码后，立刻将验证码从session中删除。
* Kaptcha 在 session 中传入了 Date() 属性，可用于后端过滤掉填写时间过长的表单（要求重新填写）

前端页面向服务器申请验证码并显示，用户注册时先在浏览器端进行验证，验证通过后提交给服务器。修改 login.jsp

从 Servlet 中获取验证码图片 codeImg 并显示

```jsp
<input type="text" id="code" name="user-name" style="width: 50%" placeholder="验证码"/>&emsp;&emsp;
<img id="codeImg" alt="" src="kaptchaServlet" width="130px" height="48px">
```

利用jQuery，给 验证码图片、注册按钮绑定单击事件：

```javascript
$("#codeImg").click(function () {
    //当url没有变化时，浏览器不会向后端发出新的请求
    //为了保证浏览器向后台发送新请求，传入时间作为变化参数
    this.src="kaptchaServlet?d=" + new Date();
})

$("#sub-btn").click(function () {
    var codeText = $("#code").val();
    codeText = $.trim(codeText);
    if (codeText == null || codeText == "") {
        $("span.errorMsg").text("验证码不能为空");
        return false;
    }
})
```

后端校验用户提交的表单数据，和session中存放的数据

```java
//获取验证码并校验
String captcha = request.getParameter("code").trim();
String token = (String)request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
//立即删除session的验证码，防止被重复使用
request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
//如果是第二次提交，token可能为空，需要排除这种情况
if (token != null && token.equalsIgnoreCase(captcha)) {...}
```

编写代码时尝试过使用 [EasyCaptcha](https://gitee.com/ele-admin/EasyCaptcha) 项目的验证码，但是调试时报错。调用时的逻辑没有变化

### 前端页面显示

当前在前端页面时，将错误提示显示在会员登录 Tab 中，应当改为显示在用户注册 Tab 中。

1）在后端添加active属性

2）前端body区域给用户注册的tab设置id

```jsp
<a id="register_tab" data-bs-toggle="tab" href="#lg2">
    <h4>会员注册</h4>
</a>
```

3）在页面加载完成后，绑定模拟单击的事件

```javascript
if ("${requestScope.active}" == "register") {
    $("#register_tab")[0].click();
}
```

