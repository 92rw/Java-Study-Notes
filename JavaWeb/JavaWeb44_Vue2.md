# Vue2

易于构建界面的前端框架，核心库只关注视图层

易上手且便于与第三方库和项目整合，支持和其他类库结合使用

开发复杂的单页应用非常方便

是Vue.js的简称，存在Vue 2 和Vue 3 的版本差异

## MVVM

* M：即Mode模型，包括数据和一些基本操作
* V：即view，视图，页面渲染结果
* VM：即View-Model，模型与视图间的双向操作（无需开发人员干涉）DOM Listeners ⇄ Data Bindings


在MVVM之前，开发人员从后端获取需要的数据模型，然后要通过DOM操作Model染到VieW中。而后当用户操作视图，我们还需要通过DoM获取View中的数据，然后同步到Mode中。

而MVVM中的VM要做的事情就是把DOM操作完全封装起来，开发人员不用再关心ModeI和View之间是如何互相影响的

只要我们Model发生了改变，View上自然就会表现出来。当用户修改了view，Model中的数据也会跟着改变。

结果：把开发人员从繁琐的DOM操作中解放出来，把关注点放在如何操作Model上，大大提高开发效率

## 使用细节

为了让IEDA识别Vue代码，需要安装Vue.js插件

* 注意代码顺序，要求div在前，script在后，否则无法绑定数据
* div元素不是必须的，可以是其他元素，例如span，但是一般将vue实例挂载到div。因为div更适合做布局
* div的id由程序员指定，一般写作app

声明式渲染：Vue.js采用简洁的模板语法来声明式地将数据渲染进DOM的系统，做到数据和显示分离

```html
<div id="app">
<!--{{message}}插值表达式，从model的data项获取-->
<!--代码执行时会从data{}匹配数据，若无匹配则输出为空-->
<h1>欢迎<br/>{{message}} {{park}}</h1>
</div>
<script src="vue.js"></script>
<script>
    //创建vue对象
    let vm = new Vue({
        el: "#app",//创建的vue实例挂载到 id=app div下
        data: {//data{} 表示数据池，以k-v形式保存数据
            message: "Welcome to",
            park:"🏞"
        }
    })
    console.log("vm=>",vm);
</script>
```

如果使用jQuery，我们需要先找到div节点，获取到DOM对象，然后进行节点操作。Vue没有繁琐的DOM操作

在新建Vue对象的属性中，el是element简写，定位页面元素使用的是jQuery表达式，data{}表示数据池（model）中的数据

## 数据单向渲染

v-bind指令可以完成基本数据渲染/绑定，简写形式就是一个冒号

插值表达式是用在标签体的，如果给标签属性绑定值，则使用v-bind指令

```html
<div id="app">
    <!--<img src="{{img_path}}">找不到图片-->
    <h1>{{message}}</h1>
    <!--
    使用插值表达式引用data数据池，数据需要在标签体内
    在标签/元素的属性上引用data数据池数据时，不能使用插值表达式
    使用v-bind完成图片引用，可以简写为一个冒号
    -->
    <img v-bind:src="img_path" v-bind:width="width">
    <img :src="img_path" :width="width">
</div>
<script src="vue.js"></script>
<script>
    //创建vue对象
    let vm = new Vue({
        el: "#app",
        data: {
            message: "你好啊",
            img_path:"default.jpg",
            width: 300
        }
    })
</script>
```

## 数据双向绑定

某一个位置数值改变，可以影响到其他位置

```html
<div id="app">
    <h1>{{message}}</h1>
    <!--说明：v-bind是数据单向渲染，data数据池的变化会影响view
    v-model="hobby.val" 是数据双向渲染，data和view互相影响
	data数据池绑定的数据变化，会影响view  底层机制是 Data Bindings
	view关联的元素值变化，会影响data数据池 底层机制是 DOM Listeners
    -->
    <input type="text" v-model="hobby.val"><br/><br/>
    <input type="text" :value="hobby.val"><br/><br/>
    <p>你输入的爱好是：{{hobby.val}}</p>
</div>
<script src="vue.js"></script>
<script>
    //创建vue对象：这个vue实例也可以不接收，作为匿名对象使用。此处是为了方便调试
    let vm = new Vue({
        el: "#app",
        data: {
            message: "请输入你的爱好",
            hobby: {
                val: "🎳⛳⚽🏀"
            }
        }
    })
</script>
```

## 事件绑定

使用 v-on 进行事件处理，比如：v-on:click 表示处理鼠标点击事件

事件调用的方法定义在vue对象声明的methods节点中

```html
<div id="app">
    <h1>{{message}}</h1>
    {{content}}<br/><br/>
    <button v-on:click="sayNo()">点击输出</button>
    <!--v-on:click 表示给button元素绑定click事件，sayNo表示绑定的方法-->
    <button v-on:click="sayAlert">点击输出</button>
    <!--如果方法没有参数，可以省略方法的小括号()，这种写法需要浏览器支持-->
    <button @click="sayStop">点击输出</button>
    <!--v-on:可以简写为@，这种写法需要浏览器支持-->
    <button>点击输出</button><br/><br/>
    控制台输入vm，查看元素可绑定的事件
</div>
<script src="vue.js"></script>
<script>
    let vm = new Vue({
        el: "#app",
        data: {
            message: "Vue事件处理演示",
            content: 'v-on:事件类型="方法名"'
        },//方法写在method，数据写在data
        methods: {
            sayNo() {
                console.warn("🚫");
            },
            sayAlert() {
                console.warn("⚠");
            },
            sayAlert() {
                console.warn("⛔");
            }
        }
    })
</script>
```

同一个Vue对象，可以在method方法池中传入data数据池的变量

```html
<div id="app">
    <h1>{{message}}</h1>
    <button v-on:click="addOne">点击+1</button>
    <button @click="count += 2">点击+2</button>
    <p>当前计数为：{{count}}</p><br/>
    <input type="text" v-model="content">
    <button @click="showContent()">点击显示输入框内容</button><br/>
</div>
<script src="vue.js"></script>
<script>
    let vm = new Vue({
        el: "#app",
        data: {
            message: "演示Vue事件绑定操作",
            count: 0,
            content: "请在此输入信息",
        },//data和method在同一个Vue实例中，可以通过this获取
        methods: {
            addOne() {
                this.count += 1;
            },
            showContent() {
                alert(this.content);
            }
        }
    })
</script>
```

