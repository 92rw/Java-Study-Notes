# 生命周期和监听函数（钩子函数）

1. Vue实例有一个完整的生命周期，也就是说从开始创建、初始化数据、编译模板、挂载DOM、渲染-更新-渲染、卸载等一系列过程，我们称为Vue实例的生命周期
2. 钩子函数（监听函数）：Vue实例在完整的生命周期过程中（比如设置数据监听、编译模板、将实例挂载到DOM、在数据变化时更新DOM等），也会运行叫做生命周期钩子的函数
3. 钩子函数的作用就是在某个阶段，给程序员一个做某些处理的机会



## Vue的生命周期

[Vue 实例 — Vue.js (vuejs.org)](https://v2.cn.vuejs.org/v2/guide/instance.html#生命周期图示)

1) new Vue()
    new了一个Vue的实例对象，此时就会进入组件的创建过程。
2) Init Events & Lifecycle
    初始化组件的事件和生命周期函数
3) **beforeCreate()**
    组件创建之后遇到的第一个生命周期函数，这个阶段data和methods以及DOM结构都未被初始化，也就是获取不到data的值，不能调用methods中的函数
4) Init injections & reactivity
   这个阶段中，初始化data和methods中的方法，页面未渲染
5) **created()**
   * 这个阶段组件的data和methods中的方法已初始化结束，可以访问，但是DOM结构未初始化，页面未渲染。
   * 在这个阶段，经常会发起Ajax请求获取后端数据，更新数据池且不影响页面渲染
6) 编译模板结构
7) **beforeMount()**
    当模板在内存中编译完成，此时内存中的模板结构还未渲染至页面上，看不到真实的数据
8) Create vm.$el and replace 'el' with it
    这一步，再把内存中渲染好的模板结构替换至真实的DOM结构也就是页面上
9) **mounted()**
    此时，页面渲染好，用户看到的是真实的页面数据，生命周期创建阶段完毕，进入到了运行中的阶段（监听）
10) 生命周期运行中（数据发生变化时）
    * **beforeUpdate()**：当执行此函数，数据池的数据是新的，但是页面是旧的
    * Virtual DOM re-render and patch：根据最新的data数据，重新渲染内存中的模板结构，并把渲染好的模板结构，替换至页面上
    * **updated()**：页面已经完成了更新，此时，data数据和页面的数据都是新的

11) beforeDestroy()
当执行此函数时，组件即将被销毁，但是还没有真正开始销毁，此时组件的data、methods数据或方法还可被调用

12) Teardown()
注销组件和事件监听
13) destroyed()
组件已经完成了销毁

## 监听函数

从页面审查元素输出可以得到结论：可以在created方法执行时获取后端数据，更新到前端页面

```html
<div id="app">
    <span id="num">{{num}}</span>
    <button @click="num++">点击👍支持️</button>
    <h2>{{name}}，有{{num}}次点赞</h2>
</div>
<script src="./vue.js"></script>
<script>
    new Vue({
        el:"#app",
        data:{
            name:"🫰比心",
            num: 0
        },
        methods:{
            show() {
                return this.name;
            },
            add() {
                this.num++;
            }
        },
        beforeCreate() {
            console.warn("执行 beforeCreate 方法");
            console.log("当前数据池的数据尚未加载",this.name,"，",this.num);
            console.log("自定义方法 this.show() 加载报错，无法找到方法");
            console.log("用户页面DOM未渲染", document.getElementById("num").innerText);
        },
        created() {
            console.warn("执行 created 方法");
            console.log("当前数据池的数据已加载",this.name,"，",this.num);
            console.log("自定义方法 this.show() 可调用",this.show());
            console.log("用户页面DOM未渲染", document.getElementById("num").innerText);
            //此时可以发出Ajax请求，接收后端数据并更新到data数据池
        },
        beforeMount(){
            console.warn("执行 beforeMount 方法");
            console.log("当前数据池的数据已加载",this.name,"，",this.num);
            console.log("自定义方法 this.show() 可调用",this.show());
            console.log("用户页面DOM未渲染", document.getElementById("num").innerText);
        },
        mounted() {
            console.warn("执行 mounted 方法");
            console.log("当前数据池的数据已加载",this.name,"，",this.num);
            console.log("自定义方法 this.show() 可调用",this.show());
            console.log("用户页面DOM已渲染", document.getElementById("num").innerText);
        },
        beforeUpdate() {
            console.warn("执行 beforeUpdate 方法");
            console.log("用户页面DOM未更新", document.getElementById("num").innerText);
        },
        updated() {
            console.warn("执行 updated 方法");
            console.log("用户页面DOM已更新", document.getElementById("num").innerText);
        },
    })
</script>
```

