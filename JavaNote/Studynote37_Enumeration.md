# 枚举

1. 枚举对应英文(enumeration, 简写enum)
2. 枚举是一组常量的集合。
3. 可以这里理解：枚举属于一种特殊的类，里面只包含一组有限的特定的对象。

## 枚举的二种实现方式

1. 自定义类实现枚举
2. 使用enum 关键字实现枚举

### 自定义类实现枚举

1. 不需要提供setXxx方法，防止属性被修改
2. 对枚举对象/属性使用 final + static共同修饰，实现底层优化.（final 和 static 搭配使用可以不导致类加载，效率更高）
3. 枚举对象名通常使用全部大写，常量的命名规范
4. 枚举对象根据需要,也可以有多个属性
5. 构造器和属性私有化，防止直接新建对象实例

### 使用enum 关键字实现枚举

```java
//使用 enum 实现枚举
enum Season2 {
    // 1.使用关键字 enum 替代 class
    // 2.常量名(实参列表)
    // 3.如果有多个常量，使用 , (逗号)间隔
    // 4.如果使用enum来实现枚举，要求将定义常量对象，写在最前面

    SPRING("春天","温暖"), SUMMER("夏天","炎热"), AUTUMN("秋天","凉爽"), WINTER("冬天","寒冷");

    private String name;
    private String desc;//描述
    private Season2(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Season{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
```

enum 关键字注意事项

1. 当我们使用 enum 关键字开发一个枚举类时，默认会继承 Enum 类，对象都使用 final + static 共同修饰（使用反编译工具 javap ，可查询到这个类是 java.lang.Enum 的子类）
2. 传统的public static final Season2 SPRING = new Season2("春天","温暖"); 简化成 SPRING("春天","温暖"), 这里必须知道，它调用的是哪个构造器
3. 如果使用无参构造器创建枚举对象，则实参列表和小括号都可以省略
4. 当有多个枚举对象时，使用逗号间隔，最后有一个分号结尾
5. 枚举对象必须放在枚举类的行首
6. 若枚举类显式的定义了带参数的构造器, 则在列出枚举值时也必须对应的传入参数



使用细节

* 使用 enum 关键字后，就不能再继承其它类了，因为 enum 会隐式继承 Enum，而 Java 是单继承机制
* 枚举类和普通类一样，可以实现接口，如下形式。
   enum 类名 implements 接口1，接口2{}





enum 常用方法一览表

| 方法名            | 详细描述                                                     |
| ----------------- | ------------------------------------------------------------ |
| values            | 返回当前枚举类中所有的常量                                   |
| valueOf           | 传递枚举类型的 Class 对象和枚举常量名称给静态方法 valueOf，会得到与参数匹配的枚举常量。 |
| toString          | 得到当前枚举常量的名称。可以通过重写这个方法来使得到的结果更易读。 |
| equals            | 在枚举类型中可以直接使用“==来比较两个枚举常量是否相等。 Enum 提供的这个 equals()  方法，也是直接使用“=="实现的。它的存在是为了在Set、List和Map中使用。注意，equals是不可变的。 |
| hashCode          | Enum实现了 hashCode() 来和 equals() 保持一致。它也是不可变的。 |
| getDeclaringClass | 得到枚举常量所属枚举类型的 Class 对象。可以用它来判断两个枚举常量是否属于同一个枚举类型。 |
| name              | 得到当前枚举常量的名称，在子类中不能重写。建议优先使用 toString()。 |
| ordinal           | 得到当前枚举常量的位置号，默认从0开始。                      |
| compareTo         | 枚举类型实现了 Comparable 接口，比较位置号大小               |
| clone             | 枚举类型不能被Clone。为了防止子类实现克隆方法，Enum 实现了一个仅抛出 CloneNotSupportedException 异常的不变  Clone() |

