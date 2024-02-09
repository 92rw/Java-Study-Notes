# 网上家具商城项目（Ajax）

## 验证注册用户名

注册会员时，如果名字已经注册过，当光标离开输入框，提示会员名已经存在，否则提示不存在

思路分析：DAO层的queryMemberByUsername方法，Service层的isUserExist方法可以直接调用。前端使用ajax将注册名发送到服务器，web层新建方法返回json格式的结果。小程序/手机端，对于后端程序员只需要根据需求返回对应格式的数据接口即可

说明：引入gson-2.2.4.jar包，可以将List和Map快速转成json格式，方便前后端数据交换。当前后端不使用特殊字符时，后端返回数据可以不使用setContentType方法

```java
HashMap<String, Object> resultMap = new HashMap<>();
//使用put方法加入参数
String resultJson = new Gson().toJson(resultMap);
resp.getWriter().write(resultJson);
```

在前端构建相关函数

```javascript
$("#username").blur(function () {
    //向后端发出get请求，而且返回数据是json格式，调用getJSON方法
    $.getJSON("memberServlet",
        {
            "action": "checkUserName",
            "username": this.value,
            "date": new Date()
        },
        function (data) {
            console.log("data=", data)
            console.log(typeof data.isNameExist)
            if (data.isNameExist) {
                $("span.errorMsg").text("该用户名已被占用");
            } else {
                $("span.errorMsg").text("该用户名可使用");
            }
        }
    )
})
```

功能扩展：在输入验证码后，校验是否正确并显示



## 添加购物车

在用户点击“Add to Cart”后，后端伙伴更新购物车数据并重定向回原地址

```java
String referer = req.getHeader("Referer");
resp.sendRedirect(referer);
```



修改前，前端页面和购物车相关的代码如下

```jsp
<script type="text/javascript">
    $(function () {
        //给按钮绑定单击事件
        $("button.add-to-cart").click(function () {
            //获取到所在项的商品编号
            var furnId = $(this).attr("furnId");
            if (furnId == 0) {
                return false;
            }
            //向后端发出请求
            location.href = "cartServlet?action=addItem&id=" + furnId;
        })
    })
</script>

<%--中间省略相关的业务代码--%>

<a href="views/customer/cart.jsp"
   class="header-action-btn header-action-btn-cart pr-0">
    <i class="icon-handbag"> 购物车</i>
    <c:if test="${not empty sessionScope.cart.amount and sessionScope.cart.amount != 0}"><span class="header-action-num">${sessionScope.cart.amount}</span></c:if>
</a>
```

这种方式会刷新整个页面，数据传输开销大。因此可以用Ajax技术局部更新到购物车，后端修改后不需要重定向，仅返回json数据包

```java
Map<String, Object> resultMap = new HashMap<>();
resultMap.put("cartAmount",cart.getAmount());
String resultJson = new Gson().toJson(resultMap);
resp.getWriter().write(resultJson);
```



经过验证，这种方法在用户登录后有效，但是对于没有登录的用户，访问后端的请求会被过滤器拦截，过滤器会请求转发到登录页面，但是ajax暂时没有权限处理这个链接

使用aiax向后台发送请求跳转页面无效的原因

1. 主要是服务器得到是ajax发送过来的request.也就是说这个请求不是浏览器请求的，而是ajax请求的，所以servlet对request进行请求转发或重定向都不能影响浏览器的跳转
2. 这时出现请求转发和重定向失效的同题
3. 解决方案：如果想要实现跳转，可以返回url，在浏览器执行 window.location(url)



利用ajax请求特有的请求头 XMLHttpRequest 特性，新建一个 WebUtils 类，在 isAjaxRequest 方法中读取这个请求头参数，供过滤器定向过滤

```java
public class WebUtils {
    public static boolean isAjaxRequest(HttpServletRequest req){
        return "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
    }
}

```

在AuthFilter过滤器的doFilter方法中，增加判断请求头参数的业务代码

```java
Member member = (Member) request.getSession().getAttribute("member");
if (null == member) {//用户没有登录
    if (WebUtils.isAjaxRequest(request)){
        //按照Json格式返回url
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("url","views/member/login.jsp");//注意这里的链接前方不带斜杠
        String resultJson = new Gson().toJson(resultMap);
        servletResponse.getWriter().write(resultJson);
        return;
    }
    request.getRequestDispatcher("/views/member/login.jsp").forward(servletRequest, servletResponse);
    return;//注意需要执行返回的代码
}
```



前端页面同步修改（由于ajax不涉及到session改变，将导致用户登录后购物车一直显示红点，此问题当前无法解决）

```jsp
<script type="text/javascript">
    $(function () {
        //给按钮绑定单击事件
        $("button.add-to-cart").click(function () {
            //获取到所在项的商品编号
            var furnId = $(this).attr("furnId");
            if (furnId == 0) {
                return false;
            }
            //向后端发出请求
            //location.href = "cartServlet?action=addItem&id=" + furnId;
            $.getJSON("cartServlet",{
                    action: "addItemByAjax",
                    id: furnId,
                },
            function (data) {
                if (data.url == undefined) {//请求没有被过滤器拦截，正常更新购物车
                    $("span.header-action-num").text(data.cartAmount)
                } else {//过滤器拦截请求，并返回下一步访问地址
                    location.href = data.url;//访问过滤器提供的地址
                }
            })
        })
    })
</script>

<%--中间省略相关的业务代码--%>

<a href="views/customer/cart.jsp"
   class="header-action-btn header-action-btn-cart pr-0">
    <i class="icon-handbag"> 购物车</i>
    <c:if test="${not empty sessionScope.member}"><span class="header-action-num">0</span></c:if>
</a>
```

