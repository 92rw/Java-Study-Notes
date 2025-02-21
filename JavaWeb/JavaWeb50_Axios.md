# Axios

* Axios是一个基于promise的HTTP库
* axios是独立于vue的一个项目，不是Vue的一部分
* x import Vue from 'vue';import ElementUI from 'element-ui';import 'element-ui/lib/theme-chalk/index.css';import App from './App.vue';​Vue.use(ElementUI);//使用插件​new Vue({  el: '#app',  render: h => h(App)});javascript



[axios中文文档|axios中文网 | axios (axios-js.com)](http://axios-js.com/zh-cn/docs/index.html)

在网页中可以直接引入

```html
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
```

## 案例演示

需求：在Vue项目中使用Axios，从服务器获取json数据，显示在页面

需要获取的json数据如下：

```json
{
  "success": true,
  "message": "成功",
  "data": {
    "items": [
      {
        "name": "牛魔王",
        "age": 800
      },
      {
        "name": "红孩儿",
        "age": 500
      },
      {
        "name": "蜈蚣精",
        "age": 200
      }
    ]
  }
}
```



代码实现如下：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>axios的应用实例</title>
</head>
<body>
<!--页面视图-->
<div id="app">
    <h1>{{msg}}</h1>
    <table border="1" width="200">
        <tr>
            <td>名字</td>
            <td>年龄</td>
        </tr>
        <tr v-for="monster in monsterList">
            <td>{{monster.name}}</td>
            <td>{{monster.age}}</td>
        </tr>
    </table>
</div>
<script src="vue.js"></script>
<script src="axios.min.js"></script>
<script>
    new Vue({
        el: "#app",
        data: {
            msg: "妖怪信息列表",
            monsterList: [] //表示妖怪的信息数组
        },
        methods: {//自定义方法
            list() {//发送ajax请求,获取数据 axios
                /*
                    解读
                    1. axios.get() 表示发出ajax请求
                    2. http://localhost:63342/axios/data/response.data.json 表示请求的url
                       要根据实际情况来填写
                    3. axios发出ajax请求的基本语法
                       axios.get(url).then(箭头函数).then(箭头函数)...catch(箭头函数)
                       (1) 如果get请求成功就进入到第一个then()
                       (2) 可以再 第一个then()中继续发出axios的ajax请求
                       (3) 如果有异常, 会进入到 catch(箭头函数)
                    4. list在生命周期函数created() 中调用-自己去回顾vue的生命周期函数
                 */
                axios.get("http://localhost:63342/axios/data/response.data.json")
                    .then(responseData => {
                        console.log("responseData= ", responseData)
                        //使用JSON.stringify(json) 把json对象转成一个字符串,方便观察
                        console.log("responseData= ", JSON.stringify(responseData));
                        // console.log("responseData.data= ", responseData.data)
                        // console.log("responseData.data.data= ", responseData.data.data)
                        console.log("responseData.data.data.item= ", responseData.data.data.items)
                        //将妖怪列表数组信息, 绑定到 data数据池的 monsterList
                        //老师小技巧，一定要学会看返回的数据格式!!!
                        this.monsterList = responseData.data.data.items;
                        //可以再次发出ajax请求, 老师故意发出了第二次ajax请求， 回顾老师讲过的promise发出多次请求
                        // return axios.get("http://localhost:63342/axios/data/response.data.json")
                    })
                    // .then(responseData => {
                    //     console.log("第二次axios发出 ajax请求responseData= ", responseData)
                    // })
                    .catch(err => {
                    console.log("异常=", err)
                    })
            }
        },
        created() {
            this.list();
        }
    })
</script>
</body>
</html>
```

