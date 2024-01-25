# 网上家具商城项目（分页导航）

## 分页显示

前端页面需要向后端请求①页码数和②每页显示条数，并且前后的url也会随之变化

编程方法：化紧为简，先死后活

### 后端代码

entity 包新建 Page.java，将分页的各个信息（pageSize，pageNo，items）封装为 JavaBean，因为分页模型中的数据类型可以有多种，因此使用泛型

dao层编写 getTotalRow() 和 getPageItems() 方法，service层编写 page() 方法，返回某页要显示的家具信息

web层获取 pageSize，pageNo，调用 furnService.page() 方法，将需要显示的数据放入 request域，请求转发到furn_manage.jsp

总体来说，不需要增加新的类和方法，主要是利于page对象相关数据来进行分页显示。这里涉及到一点算法，但是并不难

代码实现：

Page 类的参数：总条数、总页数、当前页码、每页显示数量、当前页显示数据，分页导航的字符串

dao层的实现：从数据库中获取总条数、当前页显示数据，赋值给 Page 类对象

```java
    @Override
    public int getTotalRow() {
        String sql = "SELECT COUNT(*) FROM `furn`";
        //得到的数据运行类型是Long型，无法直接向下转型为Integer，会出现cast异常
        return ((Number) queryScalar(sql)).intValue();
    }

    @Override
    public List<Furn> getPageItems(int begin, int pageSize) {
        //begin表示从第几条数据开始获取（从0开始计数），pageSize表示取出记录数
        String sql = "SELECT `id`, `name`, `maker`, `price`, `sales`, `stock`, `img_path` imgPath FROM `furn` LIMIT ?,?";
        return queryMulti(sql, Furn.class, begin, pageSize);
    }
```

* 因为每页显示数不能为0，因此在JavaBean的set方法中执行判断语句

### 前端代码

1. 管理员进入到家居管理后台页面：manage_menu.jsp 中，修改 furn_manage.jsp 的 Get 方法

2. 实现通过分页导航条来进行分页显示，修改 furn_manage.jsp：

   * 原先从返回的furns属性读取数据，改为从后端返回的page属性的item中读取数据
   * 使用 JSTL 标签，完成上一页、下一页、总页码的选框
3. 点击分页导航，可以显示对应页的家居信息

```jsp
<!--  Pagination Area Start 分页导航条 -->
<div class="pro-pagination-style text-center mb-md-30px mb-lm-30px mt-6" data-aos="fade-up">
    <ul>
        <%--显示上一页的条件是，当前页大于1--%>
        <c:if test="${requestScope.page.pageNo > 1}">
            <li><a href="manage/furnServlet?action=page&pageNo=${requestScope.page.pageNo-1}">上一页</a></li>
        </c:if>

        <c:set var="begin" value="1"/>
        <c:set var="end" value="${requestScope.page.totalPage}"/>
        <c:forEach begin="${begin}" end="${end}" var="i">
            <%--如果第i页是当前页，那么使用 class="active" 修饰--%>
            <c:if test="${i == requestScope.page.pageNo}">
                <li><a class="active" href="manage/furnServlet?action=page&pageNo=${i}">${i}</a></li>
            </c:if>
            <c:if test="${i != requestScope.page.pageNo}">
                <li><a href="manage/furnServlet?action=page&pageNo=${i}">${i}</a></li>
            </c:if>
        </c:forEach>

        <%--显示下一页的条件是，当前页小于总页数--%>
        <c:if test="${requestScope.page.pageNo < requestScope.page.totalPage}">
            <li><a href="manage/furnServlet?action=page&pageNo=${requestScope.page.pageNo+1}">下一页</a></li>
        </c:if>
        <li><a>共 ${requestScope.page.totalPage} 页</a></li>
    </ul>
</div>
<!--  Pagination Area End 分页导航条 -->
```



3. 在管理员进行修改，删除，添加家居后，能够回显原来操作所在页面的数据

   * 修改前端页面超链接，向 get 请求访问的链接加入 pageNo 属性
   
   * 后端通过请求转发，将 pageNo 传递给前端页面（不需要修改display方法的代码）
   
   * 前端页面在表单隐藏域通过 ${param.pageNo} 获取 pageNo（EL表达式的param参数，等价于request.getParamter()方法）
   
   * 后端修改 update()，del()，add() 方法重定向的链接
   
   * ```JAVA
     //修改前
     resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list");
     //修改后
     resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
     ```
   
   * 说明：如果 page 方法已经给 pageSize 设置默认值，那么url中可以不包含pageSize数值

### 用户界面分页显示

web层新建 CustomerFurnServlet，将请求转发到前端在views目录新建的 customer/index.jsp 页面，分页显示家具信息

通过读取page对象的信息，修改index页面的分页导航和显示内容



## 首页搜索

点击搜索按钮，可以输入家居名
正确显示分页导航条，并且要求在分页时，保留上次搜索条件，

dao层编写 方法，getTotalRowByName() 和 getPageItemsByName() 方法，service层编写 pageByName() 方法

web层获取 pageSize，pageNo，调用 furnService.pageByName() 方法，将需要显示的数据放入 request 域，请求转发到 index.jsp

* 数据清洗：用空字符串在数据库查询，返回的结果是所有家居项。如果参数有name但是没有值，接收到的是""，如果参数没有name，接收到的是null，因此将接收到null转换为""
* Page 对象的 url 属性

前端难点：在切换分页时，传入后端的请求需要包含搜索的Name（传入上次搜索条件）

* 在搜索表单中添加隐藏项 action，给输入框加入 name 属性以便后端获取参数
* 利用 Page 类的 url属性，修改前端导航页面的导航

```jsp
<!--  Pagination Area Start 分页导航条 -->
<div class="pro-pagination-style text-center mb-md-30px mb-lm-30px mt-6" data-aos="fade-up">
    <ul>
        <%--显示上一页的条件是，当前页大于1--%>
        <c:if test="${requestScope.page.pageNo > 1}">
            <li><a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo-1}">上一页</a></li>
        </c:if>

        <c:set var="begin" value="1"/>
        <c:set var="end" value="${requestScope.page.totalPage}"/>
        <c:forEach begin="${begin}" end="${end}" var="i">
            <%--如果第i页是当前页，那么使用 class="active" 修饰--%>
            <c:if test="${i == requestScope.page.pageNo}">
                <li><a class="active" href="${requestScope.page.url}&pageNo=${i}">${i}</a></li>
            </c:if>
            <c:if test="${i != requestScope.page.pageNo}">
                <li><a href="${requestScope.page.url}&pageNo=${i}">${i}</a></li>
            </c:if>
        </c:forEach>

        <%--显示下一页的条件是，当前页小于总页数--%>
        <c:if test="${requestScope.page.pageNo < requestScope.page.totalPage}">
            <li><a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo+1}">下一页</a></li>
        </c:if>
        <li><a>共 ${requestScope.page.totalPage} 页</a></li>
        <li><a>共${requestScope.page.totalRow}记录</a></li>
    </ul>
</div>
<!--  Pagination Area End 分页导航条 -->
```

* 没有指定具体地址的占位图片\<img src="#"> 在页面加载时会去请求当前页的url，造成前端多发送一次html请求
* 因为 CustomerFurnServlet 的 page 方法，在前端的运行逻辑和无参的 pageByName 方法一致，因此可以不再使用page方法

## 按条件显示分页导航

1. 如果总页数≤5，就全部显示
2. 如果总页数>5，按照如下规则显示
   * 如果当前页是前3页，就显示1-5
   * 如果当前页是后3页，就显示最后5页
   * 如果当前页是中间页，就显示当前页前2页，当前页，当前页后两页

功能实现：



