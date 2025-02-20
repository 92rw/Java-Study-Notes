# ElementUI

ElementUI 是组件库，网站快速成型工具

官方文档：[Element - 网站快速成型工具](https://element.eleme.cn/#/zh-CN)



在项目目录下引入插件

```shell
npm i element-ui -S
```

写入 main.js 文件

```javascript
import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';

Vue.use(ElementUI);//使用插件

new Vue({
  el: '#app',
  render: h => h(App)
});
```