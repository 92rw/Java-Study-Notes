# ES6

1. ECMAScript6.0（以下简称ES6）是  JavaScript  语言的下一代标准，2015年6月发布。
2. ES6设计目标：达到 JavaScript 语言可以用来编写复杂的大型程序，成为企业级开发语言
3. ECMAScript和 JavaScript 的关系：ECMAScript 是 JavaScript 的规范/规则， JavaScript 是E CMAScript 的一种实现



## let声明变量

|          | var变量 | let变量 | 错误提示                                                     |
| -------- | ------- | ------- | ------------------------------------------------------------ |
| 作用域   | 全局    | 局部    | Uncaught ReferenceError: is not defined                      |
| 声明次数 | 多次    | 一次    | Uncaught SyntaxError: redeclaration                          |
| 变量提升 | 存在    | 不存在  | Uncaught ReferenceError: can't access lexical declaration before initialization |



## const声明常量/只读常量

常量在定义时赋值，否则报错 Uncaught SyntaxError: Missing initializer in const declaration

赋值后不能修改，否则报错 Uncaught TypeError: Assignment to constant variable.



## 解构赋值

对赋值运算符的扩展，是一种针对数组或者对象进行模式匹配，然后对其的变量进行赋值

主要有两种形式：数组解构（使用中括号[]）和对象解构（使用花括号{}）

数组解构ArrayResolve

```javascript
let arr = [1, 2, 3];

//传统方式解构
let x = arr[0], y = arr[1], z = arr[2];

//ES6数组解构，也可以用var类型接收（不强制用let类型）
let [a,b,c] = arr;//写法1
let [num1, num2, num3] = [100, 200, 300];//写法2
```

对象解构ObjectResolve

```javascript
let monster = {name: '牛魔王', age: 800}

//传统方式解构：对象名.属性名
console.log(monster.name, " ", monster.age);

//ES6对象解构
let {age, name} = monster;//把monster对象的属性依次赋值给{name,age}
console.log(name, " ", age);

//更加简化的写法
let {name, age} = {name: '牛魔王', age: 800}
```

说明：

* {name, age}顺序可以和对象不一致，取名需要和对象的属性名保持一致，否则得到undefined
* 使用花括号{}包括，不适用中括号[]，中括号表示数组

```javascript
//在方法中自动解构对象，取出对象的参数
//方法参数名需要和对象属性名保持一致
let monster = {name: '牛魔王', age: 800};
function f1({name, age}) {
    console.log("f1-name=>", name, "f1-age=>", age);
}
f1(monster);
```



## 模板字符串

模板字符串使用反引号``将字符串包裹

可作为普通字符串或用来定义多行字符串，即：可以将换行字符串原生输出

```js
//1.将换行字符串原生输出
let str1 = `for (int i = 0; i <10; i++) {
				System.out.println("i = " + i);
			}`
console.log("str1=" + str1);
```

字符串插入变量和表达式，使用${}，类似EL表达式

```javascript
let name = "南京东路";
let str2 = `车站名称=${name}`;
//解析表达式中的name时，会根据就近原则
//如果找不到对应的字符串，会显示为空但不报错
console.log("str2= ", str2);
```

花括号{}中可放入 JavaScript 表达式

```javascript
let str3 = `1 + 2 = ${1 + 2}`;
console.log("str3= ", str3);

let n1 = 80;
let n2 = 20;
let str4 = `n1 + n2 = ${n1 + n2}`
console.log("str4= ", str4);
```

字符串中调用函数

```javascript
function sayHi(name) {
    return "hi," + name;
}

let str5 = `sayHi() 返回的结果是=${sayHi('🍿')}`;
console.log("str5= ", str5)
```



## 对象的新特性

对象声明的简写

```javascript
const age = 800;
const name = "牛魔王";

//传统方式定义对象
let monster = {name: name, age: age};

//ES6声明对象
let monster2 = {name, age};
```

对象方法的简写

```javascript
//传统方式定义
const food1 = {
    item: "🍔",
    price: 88.8,
    buy: function () {
        console.log("item=>", this.item, "price=>", this.price);
    }
};
food1.buy();

//ES6方式定义
let food2 = {
    item: "🍨",
    price: 31.4,
    buy() {
        console.log("item=>", this.item, "price=>", this.price);
    }
};
food2.buy();
```

对象拓展运算符ObjectOperator

```javascript
let beverage = {item: "🍵", price:20};

//引用指向，会造成同步修改
//let beverage = beverage;

//拷贝对象（深拷贝）：开辟新的内存空间
let beverage2 = {...beverage};
beverage2.item = "🍺";
console.log("beverage=>", beverage, "beverage2=>", beverage2)

//合并对象：下方的属性会替代上方，注意使用小逗号
let train = {locomotive:"🚂", passenger:80};
let station = {type:"🚉", passenger:60};
let merge = {...train,...station}
console.log("merge=>", merge)
```

注意事项：

* 对象拓展运算符是比较新的特性，低版本的浏览器不支持
* 火狐/谷歌浏览器没有问题



## 箭头函数

箭头函数提供更加简洁的函数书写方式。多用于匿名函数的定义

* 基本语法：(形参列表)=> {函数体}
* 箭头函数没有参数或有多个参数，要用 () 括起来；箭头函数只有一个参数，可以省略 ()
* 箭头函数函数体有多行语句，用 {} 包裹起来，表示代码块
* 函数体只有一行语句，并且需要返回结果时，可以省略 {}，结果会自动返回

```javascript
//传统方式定义函数
var f1 = function (n) {
    return n * 2;
}
console.log(f1,"f1(2)= ",f1(2));

//箭头函数标准写法
let f2 = (n) => {
    return n * 2;
}
console.log(f2,"f2(2)= ",f2(2));
//箭头函数简写
let f3 = n => n * 2;
console.log(f3,"f3(2)= ",f3(2));
```

箭头函数作为匿名形参使用

```javascript
function hi(f4) {
    console.log(f4(900));
}
hi(n => n + 100);
```

箭头函数+对象解构：注意参数是 ({属性名})，和对象属性名保持一致

```javascript
const earth = {
    name: "earth",
    gravity: 9.8,
    snapshot: ['🌍','🌏','🌎','🗺']
}

//传统方式
function f1(planet){
    console.log("snapshot=>",planet.snapshot);
}
f1(earth);

//箭头函数
let f2 = ({snapshot}) => console.log("snapshot=>",snapshot);
f2(earth);
//解读：根据ES6的对象解构特性，会把earth对象的snapshot属性赋给f2函数，作为形参是
//前提，花括号{}内的形参名，和传入对象的属性名一致

let f3 = ({name, gravity, snapshot}) => {
    console.log("name：",name, "，gravity：",gravity,"，snapshot：", snapshot)
}
f3(earth);
```

案例：调用f8函数，形参传入arr数组和fun箭头函数，计算arr的积

```javascript
function f8(arr, fun) {
    console.log(fun(arr));
}
f8([1,4,3,5], (arr) => {
    //初始值不能为0
    let res = arr[0];
    for (let i = 1; i < arr.length; i++) {
        res *= arr[i];
    }
    return res;
})
```



函数调用

浏览器控制台（Browser Console）提供了一个 JavaScript 执行环境，允许你在浏览器中直接执行 JavaScript 代码。当你在控制台中输入数学表达式时，实际上是在执行 JavaScript 代码。

JavaScript 是一种脚本语言，最初设计用于在浏览器中添加交互式的动态内容。数学表达式在 JavaScript 中是合法的语法，所以你可以在控制台中直接输入数学表达式并执行。通过 `变量 => 语句` 定义的箭头函数也相当于函数表达式

一般在控制台定义变量时因为没有指定返回值，所以控制台会返回 `undefined`，例如 `let x = 5+6;` ，`const c100 = x => 100;`

函数也可以作为变量传入其他函数，例如`c100(c100)` 会返回 `100` 。在控制台中输入`(变量 => 语句/函数体) (传入变量值)`，会返回计算结果，例如 `(x => x ** 0.5) (2)` 可以计算根号2

定义函数 `var f1 = m => (n => m * n);` 控制台输入`f1(6)` 时只会得到`(n => m * n)`，原因是m的值已作为静态作用域但未显示；如果执行`f1(6)(5)` 可以直接得到两数相乘结果