# HTTP协议

## 说明

1. 超文本传输协议(HTTP，Hyper Text Transfer Protocol)是互联网上应用广泛的一种网络协议。是工作在Tcp/IP协议基础上的，所有的WWW文件都遵守这个标准。
2. http是TCP/IP协议的一个应用层协议，也是web开发的基础。http1.0短连接http1.1长连接

备注：互联网存在其他传输协议如STMP、FTP、POP3等



## 传输的HTTP协议内容

具体说明详见《HTTP常见请求和响应头-说明.pdf》《HTTP响应状态码说明.docx》

### HTTP响应包

#### HTTP  Request Header请求头

```
GET /tomcat01/http?username=1&pwd=1 HTTP/1.1【请求行：请求方式、URI、协议和版本号】
Accept【客户端能接收的数据格式】: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Accept-Encoding【浏览器可接受的数据压缩方式（算法）】: gzip, deflate, br
Accept-Language【浏览器接受的语言】: en-US,en;q=0.9
Connection【处理连接的方式】: keep-alive【keep-alive要求服务器回送数据后，继续保持一段时间连接，closed立即断掉连接】
Cookie: JSESSIONID=82D0CA46C9ECB03B5662724B903B1B8D; username-localhost-8888="2|1:0|10:1689932841|23:username-localhost-8888|44:ZGUyNzhhNmY5NGRmNDYyNmIyYTQxNGQ1ZTY3YmI0NWY=|811e6babe1dfea81094edbb143d004bef52d9081c2b99ab80e1d8d010cc9010b"; _xsrf=2|d65e687c|67bfaf50b415472bbd4fdce1de05e1e3|1689932841; Idea-43f4a349=25e982a1-e8bf-4ae5-a005-38f6d29ff244
Host【请求目标】: localhost:8080【域名:端口】
Referer【发出请求的页面->防盗链】: http://localhost:8080/tomcat01/test/Login.html
Sec-Fetch-Dest: document
Sec-Fetch-Mode: navigate
Sec-Fetch-Site: same-origin
Sec-Fetch-User: ?1
Upgrade-Insecure-Requests: 1
User-Agent【浏览器/客户端信息】: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.188
sec-ch-ua: "Not/A)Brand";v="99", "Microsoft Edge";v="115", "Chromium";v="115"
sec-ch-ua-mobile: ?0
sec-ch-ua-platform: "Windows"
```



```
POST /tomcat01/http HTTP/1.1【POST请求不会把表单数据附加在URI后】
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Accept-Encoding: gzip, deflate, br
Accept-Language: en-US,en;q=0.9
Cache-Control: max-age=0
Connection: keep-alive
Content-Length: 16【发送数据长度（字节）】
Content-Type【表示提交的数据格式】: application/x-www-form-urlencoded【表示URL编码】
Cookie: JSESSIONID=82D0CA46C9ECB03B5662724B903B1B8D; username-localhost-8888="2|1:0|10:1689932841|23:username-localhost-8888|44:ZGUyNzhhNmY5NGRmNDYyNmIyYTQxNGQ1ZTY3YmI0NWY=|811e6babe1dfea81094edbb143d004bef52d9081c2b99ab80e1d8d010cc9010b"; _xsrf=2|d65e687c|67bfaf50b415472bbd4fdce1de05e1e3|1689932841; Idea-43f4a349=25e982a1-e8bf-4ae5-a005-38f6d29ff244
Host: localhost:8080
Origin【请求来源】: http://localhost:8080
Referer: http://localhost:8080/tomcat01/test/Login.html
Sec-Fetch-Dest: document
Sec-Fetch-Mode: navigate
Sec-Fetch-Site: same-origin
Sec-Fetch-User: ?1
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.188
sec-ch-ua: "Not/A)Brand";v="99", "Microsoft Edge";v="115", "Chromium";v="115"
sec-ch-ua-mobile: ?0
sec-ch-ua-platform: "Windows"
```



#### HTTP  Response Header响应头

```
HTTP/1.1 200 OK【响应行：协议、状态码、描述】
Server【服务器信息】: Apache-Coyote/1.1
Accept-Ranges: bytes【支持断点】
ETag【资源标识(token)】: W/"1157-1691060001111"
Last-Modified【返回的资源(图片，css，html)最近修改时间】: Thu, 03 Aug 2023 10:53:21 GMT【用于缓存优化资源】
Content-Type【返回资源的类型】: text/html
Content-Length【返回资源的大小（字节数）】: 1157
Date【服务器响应时间】: Thu, 03 Aug 2023 11:06:46 GMT

【响应体和响应头间有一个空行】
```



```
HTTP/1.1 302 Found
Server: Apache-Coyote/1.1
Location【重定向的目标位置】: test/Curriculum.html
Content-Length: 0
Date: Thu, 03 Aug 2023 11:25:49 GMT
```

### Http响应体

数据类型在Content-Type指定，长度（字节数）在Content-Length指定，

### 细节说明

#### HTTP状态码HTTP Status Code

HTTP有固定的响应代码：

- 1xx：表示一个提示性响应，例如101表示将切换协议，常见于WebSocket连接；
- 2xx：表示一个成功的响应，例如200表示成功，206表示只发送了部分内容；
- 3xx：表示一个重定向的响应，例如301表示永久重定向，303表示客户端应该按指定路径重新发送请求；
- 4xx：表示一个因为客户端问题导致的错误响应，例如400表示因为Content-Type等各种原因导致的无效请求，404表示指定的路径不存在；
- 5xx：表示一个因为服务器问题导致的错误响应，例如500表示服务器内部故障，503表示服务器暂时无法响应。

##### 200：成功返回资源

##### 302：重定向

返回一个新的Location供浏览器访问

##### 304：所请求的资源未修改

当浏览器请求资源的时，服务器会返回该资源的最近修改时间Last-Modified，如果浏览器禁用缓存，这个信息就没有使用，浏览器就每次都会要求返回该资源。
如果浏览器没有禁用缓存，浏览器在请求时，请求头会包含If-Modified-Since，含义：本地存在缓存文件，并展示其的最近修改时间。这时服务器就会比较时间，如果服务器的资源更新，就会返回该资源，如果发现没有修改，就返回304状态码（但是不会返回该资源）

##### 404：服务器无法根据客户端的请求找到资源（网页）。

通过此代码，网站设计人员可设置"您所请求的资源无法找到"的个性页面

##### 500：服务器内部错误，无法完成请求

##### 502：充当网关或代理的服务器，从远端服务器接收到了一个无效的请求



#### MIME 类型

多功能Internet邮件扩充服务 (Multipurpose Internet Mail Extensions) 是HTTP协议中的数据类型，与某一种文件的扩展名对应。浏览器通常使用 MIME 类型（而不是文件扩展名）来确定如何处理URL

MIME 类型通用结构：

```
type/subtype
```

由类型与子类型两个字符串中间用 **/** 分隔而组成，不允许有空格。type 表示可以被分多个子类的独立类别，subtype 表示细分后的每个类型。

| 文件               | 扩展名      | MIME类型                 |
| ------------------ | ----------- | ------------------------ |
| 超文本标记语言文本 | .html，.htm | text/html                |
| 普通文本           | .txt        | text/plain               |
| RTF文本            | .rtf        | application/rtf          |
| GIF图形            | .gif        | image/gif                |
| JPEG图形           | .jpeg，.jpg | image/jpeg               |
| au声音文件         | .au         | audio/basic              |
| MIDI音乐文件       | .mid，.midi | audio/midi，audio/x-midi |
| RealAudio音乐文件  | .ra，.ram   | audio/x-pn-realaudio     |
| MPEG文件           | .mpg，.mpeg | video/mpeg               |
| AVI文件            | .avi        | video/x-msvideo          |
| GZIP文件           | .gz         | application/x-gzip       |
| TAR文件            | .tar        | application/x-tar        |

MIME类型对大小写不敏感，但是传统写法都是小写。

text/plain 表示返回的数据，请浏览器使用文本方式解析
application/x-tar 表示返回的是文件，浏览器就会以下载文件的方式处理

### GET请求和POST请求

#### GET请求的种类

1.form 标签 method=get
2.a 标签
3.link 标签引入 css
4.script 标签引入 js 文件
5.img 标签引入图片
6.iframe 引入html 页面
7.在浏览器地址栏中输入地址后敲回

#### POST请求的种类

1.form 标签 method=post

#### 如何选择

在大部分情况下，我们不需要考虑这个问题，因为业务本身就会自动区别，比如你要显示图片，引入csss这个天然的就是get请求，比如你登录，发帖，上传文件，你就会使用post
传输的数据大小区别
1）get传送的数据量较小。不能大于2KB（不同浏览器不一样）。
2）post传送的数据量较大。一股默认不受限制。

#### 什么情况下使用post请求

1）Post请求是会在浏览器上隐藏参数部分的，在安全要求的部分都会使用到POST请求。如用户登录。数据增删改等等。都会把参数隐藏起来，这样就不会通过你的请求暴露参数格式。比方：del?id=2，别人就能够用del?id=3来删除其它数据。
2）在向server传递数据较大的时候。使用POST，get是有限制的，比如发帖，上传文件

#### 什么情况下使用get方式呢

1）在前台页面展示，比如分页内容等，可以保留传递参数

2）可用来非常好的分享和传播，POST中链接地址是不变化的

#### 建议

1）get方式的安全性较Post方式要差些。包括机密信息的话。建议用Post数据提交方式
2）在做数据查询时。建议用Get方式；而在做数据加入、改动或删除时，建议用Post方式

