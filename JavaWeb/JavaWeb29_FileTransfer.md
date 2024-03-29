# 文件上传下载

## 基本介绍

1. 文件的上传和下载，是常见的功能，后面项目就使用了文件上传下载。
2. 如果是传输大文件，一般用专门工具或者插件
3. 文件上传下载需要使用到两个包（commons-fileupload-1.2.1.jar，commons-io-1.4.jar），需要导入



## 文件上传

### 基本原理

对请求头进行抓包，可以看到前端使用Post方式提交。

前端页面采用表单提交，action：提交的目标地址，method：指定为post，

enctype：编码类型encodetype，

* 默认是application/x-www-form-urlencoded 即url编码。url编码一般适用于文本数据，不适合二进制文件数据的提交
* 提交二进制文件需要指定为 multipart/form-data，表示表单提交的数据由多个部分组成，可提交二进制数据和文本数据

```html
<form action="fileUploadServlet" method="post" enctype="multipart/form-data">
    图片预览：<img src="1.png" alt="" width="200" height="200" id="prevView">
    <input type="file" name="pic" id="" value="" onchange="prev(this)"/>
    图片说明：<input type="file" name="name"><br/>
    <input type="submit" value="上传"/>
</form>
```

此时HTTP请求头的形式有变化，在请求体中包含文件类型和文件数据

服务器端接收时，需要实现这些功能：

* 判断是不是文件表单

* 判断表单提交的各个表单项是什么类型
  * 对于普通表单项，按照文本方式处理
  * 对于文件表单项（二进制数据），使用IO技术进行处理
* 把表单提交的文件数据，保存到服务端的指定目录

代码实现：FileUploadServlet.java

### 注意事项

1. 如果将文件都上传到一个目录下，当上传文件很多时，会造成访问文件速度变慢，因此可以将文件上传到不同目录。比如一天上传的文件，统一放到以年月日命名的文件夹，比如19190810文件夹。该功能利用工具类实现
2. 一个完美的文件上传，要考虑的因素很多，比如断点续传、控制图片大小，尺寸，份片上传，防止恶意上传等，在项目中，可以考虑使用Webuploader组件（百度开发）
   http://fex.baidu.com/webuploader/doc/index.html
3. 文件上传功能，在项目中建议有限制的使用，一般用在头像、证明、合同、产品展示等，如果不加限制，会造成服务器空间被大量占用［比如b站评论不能传图片，微信发1次朋友圈最多9张图等]
4. 文件上传，创建web/upload的文件夹，在tomcat启动时，没有在out目录下创建对应的upload文件夹，原因是tomcat不会在out下创建空目录的。解决方式是在upload目录中放一个文件，这个是IDEA+Tomcat的问题，实际开发不会存在
5. 如果文件重名，后端调用write方法会覆盖现有文件，因此保存时需要给文件名加UUID和毫秒数前缀，保证文件唯一性





## 文件下载

### 基本原理

响应头的重要字段：

* Content-Disposition表示下载数据的展示方式，默认是内联形式（网页的一部分），或者以下载文件的方式attachment。
* Content-Type指定返回数据的MIME类型

响应体

* 在网络传输时是图片的原生数据（按照浏览器下载的编码）
* 浏览器下载完图片，解析后显示在页面上

### 注意事项

1.文件下载，比较麻烦的就是文件名中文处理，因此需要针对不同浏览器做不同编码，从而保证文件名称正确

2.对于网站的文件，很多文件使用另存为即可下载，对于大文件（文档，视频），会使用专业的下载工具（迅雷、百度，腾讯，华为网盘等）

3.对于不同的浏览器，在把文件下载完毕后，处理的方式不一样。有些是直接打开文件，有些是将文件下载到本地
/下载目录

编程心得：如果不了解对象是什么结构，可以使用①输出类信息②debug方法

代码实现：FileDownloadServlet.java
