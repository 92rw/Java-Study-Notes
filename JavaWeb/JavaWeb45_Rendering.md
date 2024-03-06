## Vue2 修饰符modifier

修饰符（Modifiers）是以（.）指明的后缀，指出某个指令以特殊方式绑定

案例：表单提交时：

* 不希望将这个表单进行整体提交，而是是Ajax的方式进行提交
* 表单整体提交会导致重载页面，而Ajax方式可以有选择性提交数据，并且局部刷新

演示正则表达式（全局属性）的使用

```html
<div id="app">
    <form action="#" v-on:submit.prevent="checkInput">
        请输入待处理文本：<br/>
        <textarea rows="5" cols="30" v-model.trim="inputText.content"></textarea><br/>
        <!--这个输入框会自动去空格-->
        请输入正则表达式：<br>
        <input type="text" v-model.trim="inputText.regexp">
        <button type="submit">检测</button><br/><br/>
        得到的检测结果为<br/>
        {{returnTest}}
    </form>
</div>
<script src="vue.js"></script>
<script>
    let vm = new Vue({
        el: "#app",
        data: {
            inputText :{},//对象的属性可以通过双向渲染动态生成，不需要提前定义
            returnTest: ""//主页面无法调用方法池新建的对象，需要提前定义
        },
        methods:{
            checkInput() {
                var regExp = new RegExp(this.inputText.regexp,'g');
                var regExpMatchArray = this.inputText.content.match(regExp);
                this.returnTest = regExpMatchArray;
            }
        }
    })
</script>
```



## 条件渲染

[条件渲染 — Vue.js (vuejs.org)](https://v2.cn.vuejs.org/v2/guide/conditional.html)

案例演示：在复选框勾选后显示同意

```html
<div id="app">
    <input type="checkbox" v-model="sel1">是否同意条款[v-if]
    <h5 v-if="sel1">你同意条款</h5>
    <h5 v-else>你不同意条款</h5><br/><br/>
    <input type="checkbox" v-model="sel2">是否同意条款[v-show]
    <h5 v-show="sel2">你同意条款</h5>
    <h5 v-show="!sel2">你不同意条款</h5>
</div>
<script src="./vue.js"></script>
<script>
    const vm = new Vue({
        el: "#app",
        data: {
            sel1: 0,
            sel2: ""
        }
    })
</script>
```

v-if会根据返回的值，来决定是否动态创建对应的子组件。确保在切换过程中，存在条件块内的事件监听器和子组件销毁和重建

v-show机制相对简单，不管初始条件是什么，元素总是会被渲染，并且只是对CSS进行切换，不显示的设置为style="display: none"

使用建议：如果要频繁地切换，建议使用 v-show，如果运行时条件很少改变，使用 v-if 较好

案例演示：成绩判断页面

当用户输入成绩后，判断成绩范围并进行数据修正。

* 90分以上优秀，70分以上良好，60分以上及格，其余不及格
* 大于100的按100处理，小于0的按0处理

```html
<div id="app">
    <h1>演示条件判断</h1>
    输出成绩：<input type="text" v-model="score" @blur="setScore"><br/>
    你当前成绩为：{{score}}，等级为
    <div v-if="score >= 90">优秀</div>
    <div v-else-if="score >= 70">良好</div>
    <div v-else-if="score >= 60">及格</div>
    <div v-else>不及格</div>
</div>
<script src="./vue.js"></script>
<script>
    const vm = new Vue({
        el: "#app",
        data: {
            score: "",
        },
        methods:{
            setScore() {
                if (this.score > 100) {
                    this.score = 100;
                }
                if (this.score < 0) {
                    this.score = 0;
                }

            }
        }
    })
</script>
```



## 列表渲染

[列表渲染 — Vue.js (vuejs.org)](https://v2.cn.vuejs.org/v2/guide/list.html)

```html
<div id="app">
    <ul>
        <li v-for="(index, i) in 5">{{index}} - 数字{{i}}</li>
    </ul>
    <table width="400px" border="1px">
        <tr>
            <td>序号</td>
            <td>站名</td>
            <td>拼音码</td>
        </tr>
        <tr v-for="(station,index) in stations">
            <td>{{index}}</td>
            <td>{{station.name}}</td>
            <td>{{station.code}}</td>
        </tr>
    </table>
</div>
<script src="./vue.js"></script>
<script>
    const vm = new Vue({
        el: "#app",
        data: {
            stations: [
                {id: 18089, name:"青岛", code:'QDK'},
                {id: 18077, name:"四方", code:'SFK'},
                {id: 18074, name:"沙岭庄", code:'SAK'},
                {id: 18088, name:"青岛北", code:'QHK'},
                {id: 17623, name:"红岛", code:'HQK'},
                {id: 17617, name:"青岛机场", code:'QJK'},
            ]
        }
    })
</script>
```

