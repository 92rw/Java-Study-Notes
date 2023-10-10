# Maven

传统的java项目，导入jar包时可能出现以下问题：①需要自行下载②版本不匹配③包之间冲突④包之间依赖。

Maven可以更方便进行项目构建和项目jar包管理，包括：bulid项目，切换jar版本，添加jar删除jar包等。官方网站：[Maven – Welcome to Apache Maven](https://maven.apache.org/)



使用方式：

* 直接使用idea自带的Maven
* 自己下载Maven软件，安装、配置并使用

在maven项目的pom.xml，可以配置项目依赖的jar（指定坐标即可）。maven根据配置，到中央仓库/私服去获取jar，下载到本地仓库。maven项目，会引用本地仓库的jar，完成项目开发。

在maven项直构建生命周期中，每个阶段的执行都有相应的插件完成。各个插件执行过程中，会附带输出内容，比如jar/war/xml/源码。程序员可以使用maven默认的插件，也可以自定义插件，完成定制任务

## 配置Maven项目

使用maven的java项目，在pom.xml文件配置需要的jar包，在设置规则并刷新后，程序会自动从 [maven仓库](https://mvnrepository.com/) 获取需要的包到指定位置，java项目只需要从maven位置导入即可

配置的Maven信息，只对当前项目生效，不会影响其他项目



项目坐标Artifact Coordinates：唯一标识一个项目 ，包含组织编号、项目名、版本号

* 组织编号GroupId：一般是com.公司名

* 项目名ArtifactId

* 版本号Version

Maven设置

* Maven home directory：程序位置

* User settings file：配置文件，比如配置maven镜像

* Local repository：从maven仓库下载的包，本地保存地址

### IDEA个性化配置

File->Settings->Editor->Build,Execution,Deployment->Build Tools->Maven

镜像仓库：先进入Maven home directory对应目录下的conf文件夹，复制settings.xml文件到User settings file对应的目录后，在 &lt;mirrors> 下进行设置

修改本地仓库：

* 全局配置：在 settings.xml文件的 \<localRepository> 标签中指定（路径分隔符号是\）

* 用户配置：勾选Override并修改Local repository（优先级高）

修改Maven：修改Maven home directory，勾选Override并修改User settings file

### 新建web项目

File -> New -> Project ->Maven -> 勾选"Create for archetype" ->选中"org.apache.maven.archetypes:maven-archetype-webapp" -> 单击"Next" ->定义项目名、存放位置、项目坐标 -> 单击"Next" -> 指定项目使用的maven设置

在main文件夹点击右键-> New Directory->选择"java"

如果不进行配置，将无法Create New Servlet

在pom.xml文件的 \<packaging> 标签指定打包方式

## 配置Tomcat

在右上方的Add Configuration中添加Tomcat Server，配置为Local，在Deployment右侧的+号选择Artifact，指定为war exploded。

区别：

* war：先打包成war包，再把该war包部署到服务器上
* war exploded：直接把文件夹、class文件等等移到Tomcat上进行部署。因此这种方式支持热部署，一般在开发的时候都是使用这种模式

在Application context中指定项目的URL

## 用Maven方式引入jar包

修改项目目录下的pom.xml文件

* &lt;properties> 中，&lt;maven.compiler.source> 和 &lt;maven.compiler.target> 修改为 1.8
* &lt;dependencies> 中，引入需要的jar包

```xml
<!-- 解读
	1. 引入servlet-api.jar，用于开发servlet
	2. dependency 标签标示引入一个包
	3. groupId 表示包的开发公司/组织/团队/个人信息 
	4. artifactId 项目名称 
	5. version 版本
	6. scope 包的作用范围：provided 表示 Tomcat 本身有这个jar包，在编译、测试时有效，但是在打包发布时不要带上这个jar包
	7.下载的包，在指定的目录下
	-->
	<dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>dom4j</groupId>
      <artifactId>dom4j</artifactId>
      <version>1.1</version>
    </dependency>
```

右侧边栏选择Maven，点击刷新"Reload All Maven Projects"后，即可在Dependencies中看到安装的jar包，此时左侧External Libraries中也会出现servlet包

