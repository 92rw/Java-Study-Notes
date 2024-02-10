# 网上家具商城项目（上传图片）

1. 后台修改家居，可以点击图片，选择新的图片
2. 使用到文件上传功能，

涉及到Furn属性的img_path路径的变化，Service和DAO层不需要调整代码，主要是FurnServlet的update方法和数据库表的变化

1. 通过id先获取对应的furn对象
2. 如果是表单的普通属性，就对应更新到furn对象
3. 如有文件上传，就将文件上传到指定位置，并给furn对象更新imgPath
4. 调用furnServlce.updateFurn()方法
5. 成功后，请求转发到update_ok.jsp

改进过程：

1. 修改Furn_update.jsp文件，实现点击文件弹出上传窗口的功能。

   * ```jsp
     //修改前的代码
     <form action="manage/furnServlet" method="post">
         <input type="hidden" name="id" value="${requestScope.Furn.id}">
         <input type="hidden" name="pageNo" value="${param.pageNo}">
         <input type="hidden" name="imgPath" value="${requestScope.Furn.imgPath}">
         <input type="hidden" name="action" value="update">
     ```

   * 加入图片上传的css语句，

   * 表单的 enctype 属性默认是 application/x-www-form-urlencoded，需要修改为 multipart/form-data

   * 前端修改enctype后，后端无法直接用getParamater方法获得表单中的属性，因此需修改提交的目标地址

   * ```jsp
     <form action="manage/furnServlet?id=${requestScope.Furn.id}&pageNo=${param.pageNo}&action=update&imgPath=${requestScope.Furn.imgPath}"
           method="post" enctype="multipart/form-data">
     ```

   * 

2. 修改FurnServlet的update方法，

   * ```java
     //修改前的代码
     protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         Furn furn = DataUtils.copyParamToBean(new Furn(), req.getParameterMap());
         if (furn != null) {
             furnService.updateFurn(furn);
             resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
         }
     }
     ```
   
   * 在WebUtils类中定义文件上传路径
   
   * ```java
     //文件上传的路径
     public static String FURN_IMG_DIRECTORY = "assets/images/product-image";
     ```
   
   * 引入commons-fileupload-1.2.1.jar 和 commons-io-1.4.jar 包，通过id获取对应的furn对象，将表单属性更新到furn对象
   
   * 如有文件上传，将图片保存到指定位置，并将新位置赋给furn对象的imgPath属性
   
   * ```java
     protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         //从前端请求中获取商品id和当前页面编号
         int id = DataUtils.parseInt(req.getParameter("id"), 0);
         int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
     
         //从数据库中获取对象
         Furn furn = furnService.queryFurnById(id);
         if (furn == null) {
             //todo 对空商品进行处理
         }
     
         if (ServletFileUpload.isMultipartContent(req)) {
             ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
             servletFileUpload.setHeaderEncoding("utf-8");
     
             try {
                 List<FileItem> list = servletFileUpload.parseRequest(req);
                 for (FileItem fileItem : list) {
                     if (fileItem.isFormField()) {//处理文本（普通表单字段）
                         switch (fileItem.getFieldName()) {
                             case "name":
                                 furn.setName(fileItem.getString("utf-8"));
                                 break;
                             case "maker":
                                 furn.setMaker(fileItem.getString("utf-8"));
                                 break;
                             case "price":
                                 furn.setPrice(new BigDecimal(fileItem.getString()));
                                 break;
                             case "sales":
                                 furn.setSales(new Integer(fileItem.getString()));
                                 break;
                             case "stock":
                                 furn.setStock(new Integer(fileItem.getString()));
                                 break;
                         }
                     } else {//表单项是一个文件
                         String fileName = fileItem.getName();
                         //如果前端没有修改图片，那么此处得到空字符串
     
                         //没有上传图片时，对图片项不做处理
                         if (!"".equals(fileName)) {
                             String relativePath = "/" + WebUtils.FURN_IMG_DIRECTORY;
                             String realPath = req.getServletContext().getRealPath(relativePath);
     
                             //如果不存在该目录，就创建
                             File pathDirectory = new File(realPath);
                             if (!pathDirectory.exists()) {
                                 pathDirectory.mkdirs();
                             }
     
                             //保存文件，并保证文件名的唯一性
                             fileName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + fileName;
                             String fileFullPath = realPath + "/" + fileName;
                             fileItem.write(new File(fileFullPath));
     
                             fileItem.getOutputStream().close();
     
                             furn.setImgPath(WebUtils.FURN_IMG_DIRECTORY + "/" + fileName);
                         }
                     }
                 }
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
         furnService.updateFurn(furn);
         //resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=page&pageNo=" + pageNo);
         req.getRequestDispatcher("/views/manage/update_ok.jsp").forward(req,resp);
     }
     ```
   
3. 新建update_ok.jsp文件，读取session中的数据，以便返回更新前的列表链接

```jsp
<a class="active" href="manage/furnServlet?action=page&pageNo=${param.pageNo}">
<h4>家具信息更新完成，点击返回家具列表</h4>
</a>
```

## 继续改进

1，家居图片都放在一个文件夹，会越来越多，请尝试在assets/images/product-image/目录下自动创建年月日目录，以天为单位来存放上传图片
2.当上传新家居图片，原来的图片就没有用了，应当删除原来的家居图片
