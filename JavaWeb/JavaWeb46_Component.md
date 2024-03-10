# 组件化编程Component

1. 在大型应用开发的时候，页面可以划分成很多部分，往往不同的页面，也会有相同的部分。例如可能会有相同的头部导航
2. 但是如果每个页面都独自开发，这无疑增加了我们开发的成本。所以我们会把页面的不同部分拆分成独立的组件，然后在不同页面就可以共享这些组件，避免重复开发

组件（Component）是 Vue.js 最强大的功能之一，可以提高复用性

* 组件也是一个 vue 实例，也包括：data、methods、生命周期函数等
* 组件渲染需要 html 模板，所以增加了 template 属性，值就是 HTML 模板
* data 是一个函数，不再是一个对象，这样每次引用组件都是独立的对象/数据



普通方式下，每个组件都要单独编写方法，复用性低



## 实现方式：全局组件模式

全局组件可以在多个Vue实例中使用。对于全局组件，任何 vue 实例都可以直接在 HTML 中通过组件名称来使用组件

```html
<div id="app">
    <!--使用全局组件-->
    <counter></counter>
</div>
<script src="vue.js"></script>
<script>
    //定义一个全局组件名称为counter，花括号{}内定义组件相关内容
    //template执行该组件的界面，因为会引用到数据池的数据，需要使用模板字符串
    //需要把组件视为一个Vue实例，也有自己的数据池（使用方法返回）和方法池
    Vue.component("counter", {
        template: `<button v-on:click="click">按钮点击次数{{count}}次</button>`,
        data() {//使用方法返回数值，保证每个组件数据独立
            return {
                count: 0
            }
        },
        methods: {
            click() {
                this.count++;
            }
        }
    })
    new Vue({
        el: "#app",
    })
</script>
```



## 实现方式：局部组件模式

可以把常用的组件，定义在某个commons.js中，当某个页面需要使用时，直接import即可

```html
<div id="app">
    <!--使用全局组件-->
    <my_counter></my_counter>
    <my_counter></my_counter>
</div>
<script src="vue.js"></script>
<script>
    //定义一个组件，名称为buttonCounter，在Vue实例引入
    const buttonCounter = {
        template: `<button v-on:click="click">按钮点击次数{{count}}次</button>`,
        data() {//使用方法返回数值，保证每个组件数据独立
            return {
                count: 0
            }
        },
        methods: {
            click() {
                this.count++;
            }
        }
    }
    new Vue({
        el: "#app",
        components: {//这个my_counter是只能在当前Vue实例中使用的局部组件
            'my_counter': buttonCounter
        }
    })
</script>
```

## 使用细节

1. 如果方法体，只有简单count++，那么可以进行简写
2. 组件定义需要放置在new Vue() 前，否则组件注册会失败