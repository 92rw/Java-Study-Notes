# 模块化编程Modular

* 传统非模块化开发有如下的缺点：（1）命名冲突（2）文件依赖
* Javascript 代码越来越庞大，Javascript 引入模块化编程，开发者只需要实现核心的业务逻辑，其他都可以加载别人已经写好的模块
* Javascript 使用 “模块”(module) 的概念来实现模块化编程，解决非模块化编程问题
* 模块化也是 ES6 的新特性
* 注意导入时不要带文件后缀

## 分类

### CommonJS模块化规范/ES5写法

1.每个js文件就是一个模块，有自已的作用域。在文件中定义的变量、函数、类/对象，都是私有的，对其他js文件不可见
2.CommonJS模块处理的代码

```javascript
//导出模块
module.exports = {
    属性名1:属性名,//可以和文件中的不一样
    属性名//如果属性名不变，可以简写
}
exports = {}

//导入模块
let/const 名称 = require("xx");//文件名的".js"省略
//可以解构化提取目标对象的特定属性
let/const {属性名} = require("xx")
```

案例演示：模块化编程实现以下功能：

* 编写functions.js，该文件有函数，变量，常量，对象，数组
* 在use.js，可以使用到function.js中定义的函数/变量/常量/对象

```html
<!--在html文件中载入JavaScript-->
<script type="text/javascript" src="./function.js"></script>
```



### ES6模块化规范

一般导出

```javascript
//导出模块
export {对象名/函数名/变量名/常量名}//在export语句前定义
export let/const 名称 = {定义的内容}//在定义时直接export

//导入模块
import {名称1,名称2} from "xx"//①导入名称需和导出名称一致，②没有导出的内容无法接收
```

批量导出：可以解决方法名冲突的问题

```javascript
//默认导出
export default {定义的内容}//默认导出时，变量作为代码块的属性，写法和直接赋值略有区别

//导入模块
import 名称 from "xx"
```

## 注意事项

* ES6的模块化无法在Node.js中执行，需要用Babel转码ES5后再执行
* export不仅可以导出对象，一切JS变量都可以导出。比如：基本类型变量、函数、数组、对象
* 没有导出的不能使用
* es6有导出方式较多，不同的导出方式对导入方式也有一定影响

