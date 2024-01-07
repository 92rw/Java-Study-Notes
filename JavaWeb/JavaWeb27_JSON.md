# JSON

1. JSON指的是JavaScript对象表示法（JavaScript Object Notation）
2. JSON是轻量级的文本数据交换格式
3. JSON独立于语言［即java、php、asp.net、go等都可以使用JSON]
4. JSON具有自我描述性，更易理解

```json
var 变量名={
"k1":value,//Number类型
"k2":"value",//字符串类型
"k3":[],//数组类型
"k4":{}，//json对象类型
"k5":[{}},{}] // json数组
};
```

JSON规则

* 映射（元素/属性）用冒号:表示，"名称":值，注意名称是字符串，因此要用双引号引起来
* 并列的数据之间用逗号，分隔。"名称1":值,"名称2":值
* 映射的集合（对象）用大括号{}表示。{"名称1":值,"名称2":值}
* 并列数据的集合（数组）用方括号[]表示。[{"名称1":值,"名称2":值},{"名称1":值,"名称2":值}]
* 元素值类型：string，number，obiect，array，true，false，nul

## json和字符串互转

JSON.stringify(json)功能：将一个json对象转换成为json字符串

JSON.parse(jsonString)功能：将一个json字符串转换成为json对象

案例演示：json_string.html

注意事项：

1、JSON.stringify(json对象) 会返回对应string，并不会影响原来json对象

2、JSON.parse(string)函数会返回对应的json对象，并不会影响原来string

3、在定义Json对象时，可以使用单引号表示字符串；定义key时可以不用引号

```json
var json_person = {"name": "jack", "age": 100};
//也可以写成
var json_person = {'name': 'jack', 'age': 100};
//或者
var json_person = {name: 'jack', age: 100};
```

4、但是在把原生字符串转成ison对象时，必须使用""，否则会报错

```json
var str_dog = "{'name':'小黄狗','age':4}";//转JSON会报错
```

5、JSON.stringify(json对象) 返回的字符串，都是""表示的字符串，所以在语法格式正确的情况下，是可以重新转成json对象的



## JSON 在 Java 中使用

### 引入外部包

1. Java中使用json，需要引入到第三方的包，对json字符串和Java对象相互转换
2. [fastjson：阿里巴巴开源的JSON解析库 | 二哥的Java进阶之路 (javabetter.cn)](https://javabetter.cn/gongju/fastjson.html)
3. [Jackson：GitHub上star数最多的JSON解析库 | 二哥的Java进阶之路 (javabetter.cn)](https://javabetter.cn/gongju/jackson.html)
4. [Gson：Google开源的JSON解析库 | 二哥的Java进阶之路 (javabetter.cn)](https://javabetter.cn/gongju/gson.html)

### 应用场景

JavaBean对象和json字符串的转换，List对象和json字符串的转换，Map对象和json字符串的转换

过程：新建Gson类对象，调用其toJson和fromJson方法

说明：将json字符串转成List对象和Map对象，借助TypeToken类，涉及到泛型和匿名内部类的使用

案例演示：JavaJson.java

