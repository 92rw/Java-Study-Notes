# Vue2 脚手架模块化开发

Vue Cli脚手架：[Home | Vue CLI (vuejs.org)](https://cli.vuejs.org/zh/)

传统开发模式的问题：开发效率低，不够规范，维护性差

## 项目环境搭建

搭建Vue脚手架工程，需要使用到NPM(node package manager),npm 是随node.js安装的一款包
管理工具，类似Maven。所以我们需要先安装Node 10.16.3版本，其他配置如下

```powershell
node -v
#安装webpack和webpack-cli
npm install webpack@4.41.2 webpack-cli -D

npm install -g @vue/cli@4.0.3

vue -V
```

vue安装完成后，进入需要部署项目的目录，使用webpack创建vue脚手架项目

```powershell
vue init webpack vue_quickstart#项目名称可自行定义
#继续操作命令行指令
? Project name vue_quickstart
? Project description A Vue.js project
? Author 92rw
? Vue build standalone
? Install vue-router? Yes
? Use ESLint to lint your code? No#代码规范化监测
? Set up unit tests No
? Setup e2e tests with Nightwatch? No
? Should we run `npm install` for you after the project has been created? (recommended) npm

#安装完成后
cd vue_quickstart
npm run dev

#通过Ctrl+C退出程序，关闭命令行工具
```

将项目文件夹拖入IDEA，在上方进行配置

新建npm项目，Scripts配置为dev，点击确定即可

## 项目结构分析

config目录：项目配置，例如 index.js 配置项目端口

node_modules目录：项目依赖的模块（主要是 .js 文件），在 package.json 文件的 devDependencies 进行配置

src目录：assets目录存放资源（图片，css等），components目录存放自定义组件，router目录存放路由文件，App.vue是项目的主体单页（显示路由的视图），main.js是核心文件（创建Vue实例，指定el，router，component，template）

index.html项目首页，通过定义的div id="app"，渲染后的Vue实例会挂载到这个页面

package.json指定项目依赖的模块，类似Java Maven项目的pom.xml

## Vue请求页面的执行流程

[入门 | Vue Router (vuejs.org)](https://router.vuejs.org/zh/guide/)

重要文件：components/HelloWorld.vue，router/index.js，App.vue，main.js，index.html

1. main.js
   * 入口js
   * 创建了Vue实例
   * 指定 el，挂载到 id="app" 的div
   * 指定router，从router目录引入
   * 指定components: { App }，将App.vue文件，并作为Vue实例的App属性引入
   * 指定template: '&lt;App/>'
2. router/index.js
   * 创建一个默认导出的 Route 对象，供其他文件调用
   * Route 对象的 routes 属性维护了一个路由表，可以指定多个路由（访问路径）
   * 解析浏览器请求的 url 对应的 path，定位到对应的 component（访问默认地址时，对应的 component 是 HelloWorld，因此找到 components/HelloWorld.Vue）
3. components/HelloWorld.vue
   * 自定义组件
   * 可以显示页面
   * 进行编译，得到视图
   * 将编译后的视图（页面）返回给 main.js，供template中的App调用

4. App.vue
   * 项目的主体单页
   * 配置了 &lt;img> 属性，
   * 在 &lt;template>代码块中的 &lt;router-view>，可以显示router路由后的视图/页面
   
5. index.html
   * 项目首页
   * 在&lt;body> 组件定义了 &lt;div id="app">&lt;/div>
   * 当Vue实例创建并渲染完成后，挂载到div上

## main.js解读

由于Vue默认生成的项目代码，使用了很多简写，造成理解困难

整个页面染过程中，main.js是中心，也是连接各个组件，路由器的关键

```js
import Vue from 'vue'
import App from './App'//完整写法是import App from './App.vue'
import router from './router'//具体写法是import router from './router/index.js'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',//在index.html中的挂载位置
  router,//完整写法是router: router
  components: { App },//属性名和值变量一致，因此使用简写
  template: '<App/>'//需要和components中的属性名一致
})

```

Vue模板解读：需要在router/index.js文件中配置路由并import才能查看

```vue
<!--模板，表示页面视图html-->
<template>
  <div>
    <h1>{{ msg }}</h1>
    <table>
      <tr>
        <td>第1行第1列</td>
        <td>第1行第2列</td>
        <td>第1行第3列</td>
      </tr>
      <tr>
        <td rowspan="2">第2行第1列</td><!--跨行-->
        <td>第2行第2列</td>
        <td>第2行第3列</td>
      </tr>
      <tr>
        <td>第3行第2列</td>
        <td>第3行第3列</td>
      </tr>
      <tr>
        <td rowspan="2">第4行第1列</td>
        <td>第4行第2列</td>
        <td>第4行第3列</td>
      </tr>
      <tr>
        <td>第5行第2列</td>
        <td>第5行第3列</td>
      </tr>
    </table>
  </div>
</template>

<!--定义数据和操作方法，默认导出-->
<script>
export default {
  name: "Hello",
  data() {
    return {
      msg: "Welcome"
    }
  }
}
</script>

<!--css样式代码，可以修饰页面视图-->
<style scoped>
div {
  width: 900px;
  background-color: aquamarine;
  margin: 0 auto;/*0代表上下边距，auto代表左右居中*/
}
h1 {
  color: mediumvioletred;
}
table, tr, td {
  border: 1px solid red;
  width: 600px;
  height: 40px;
  border-collapse: collapse;/*边框合并成细线*/
}
table{
  margin: 0 auto;/*表格居中*/
  border-collapse: collapse;
}
</style>

```

