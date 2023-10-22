# Java程序打包

在java17版本中使用jpackage采用命令行的方式进行打包

[jpackage - 用于打包自包含 Java 应用程序的工具。由Oracle官方文档翻译 - 哔哩哔哩 (bilibili.com)](https://www.bilibili.com/read/cv14523427/)



在jdk8版本中，可使用自带的bin/javafxpackager.exe

* 点击IDEA项目右上角的Project Structure，进入Artifacts，点击+号→JavaFx Application→From module "项目名"
* 在Layout选项卡，点击 "项目名.jar"→Create Manifest.→选择src目录→指定 Main Class
* 在Java FX选项卡，Application class 指定为Main类，填写Title 和 Vendor，将Native bundle设置为all，Application icon指定图标
* 设置完成后，通过上方菜单栏 Build→Build Artifacts→在Action中选Build
* 此时在 `out/artitacts/项目名/bundles` 目录可找到指定项目名的文件夹，就是目标jar包，自带运行环境



使用NSIS可将程序打包成安装包，配合 HM NIS Edit 使用
