/*
1）注解（Annotation）也被称为元数据（Metadata），用于修饰解释包类、方法、属性、构造器、局部变量等数据信息。
2）和注释一样，注解不影响程序逻辑，但注解可以被编译或运行，相当于嵌入在代码中的补充信息。
3）在JavaSE中，注解的使用目的比较简单，例如标记过时的功能，忽略警告等。在JavaEE中注解占据了更重要的角色，
例如用来配置应用程序的任何切面，代替JavaEE旧版中所遗留的繁冗代码和XML配置等

基本的 Annotation介绍
使用 Annotation 时要在其前面增加 @ 符号，并把该 Annotation 当成一个修饰符使用。用于修饰它支持的程序元素
> 三个基本的 Annotation:
1）@Override：限定某个方法，是重写父类方法，该注解只能用于方法
2）@Deprecated：用于表示某个程序元素（类，方法等）已过时
3）@SuppressWarnings:抑制编译器警告

补充说明：
@interface不是interface，是在jdk5.0之后加入的注解类
@Target是修饰注解的注解，称为元注解
 */

@Deprecated
class Superclass{
    @Deprecated
    public void fly(){
        System.out.println("Superclass fly");
    }
}
@SuppressWarnings({"unused"})
class Subclass extends Superclass{
    //注解
    //1. @Override 注解在fly方法上，表示子类重写
    //2. 主要是用来编译检查的，不影响程序运行
    //3.写了注解后，编译器会检查是否真得重写，构成重写则编译通过
    // 如果没有构成重写则提示编译错误
    @Override
    public void fly() {
        System.out.println("Subclass fly");
    }
}
/*
@Override使用说明
1.@Override表示指定重写父类的方法（从编译层面验证），如果父类没有fly方法，则会报错
2.如果不写 @Override 注解，而父类仍有public void fly()，仍然构成重写
3.@Override只能修饰方法，不能修饰其它类，包，属性等等
4.查看@Override注解源码为 @Target(ElementType.METHOD) 说明只能修饰方法


@Deprecated
1.用于表示某个程序元素（类，方法等）已过时
2.可以修饰方法，类，字段，包，参数等等
3. @Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
                     构造器   字段(属性)    局部变量      方法      包       参数     类型
4.@Deprecated的作用可以做到新旧版本的兼容和过渡。表示不再推荐使用，但仍然可以使用

@SuppressWarnings
1.当我们不希望看到警告的时候，可以使用@Suppresswarnings注解来抑制警告信息
2.在{""}中，可以写入你希望抑制（不显示）的警告信息，生成时直接点击左侧的黄色提示就可以选择
3.可以指定的警告类型有：
            all，抑制所有警告
            boxing，抑制与封装/拆装作业相关的警告
            cast，抑制与强制转型作业相关的警告
            dep-ann，抑制与淘汰注释相关的警告
            deprecation，抑制与淘汰的相关警告
            fallthrough，抑制与switch陈述式中遗漏break相关的警告
            finally，抑制与未传回finally区块相关的警告
            hiding，抑制与隐藏变数的区域变数相关的警告
            incomplete-switch，抑制与switch陈述式(enum case)中遗漏项目相关的警告
            javadoc，抑制与javadoc相关的警告
            nls，抑制与非nls字串文字相关的警告
            null，抑制与空值分析相关的警告
      rawtypes，忽略没有指定泛型的警告（传参时没有指定泛型）
            resource，抑制与使用Closeable类型的资源相关的警告
            restriction，抑制与使用不建议或禁止参照相关的警告
            serial，抑制与可序列化的类别遗漏serialVersionUID栏位相关的警告
            static-access，抑制与静态存取不正确相关的警告
            static-method，抑制与可能宣告为static的方法相关的警告
            super，抑制与置换方法相关但不含super呼叫的警告
            synthetic-access，抑制与内部类别的存取未最佳化相关的警告
            sync-override，抑制因为置换同步方法而遗漏同步化的警告
      unchecked，忽略没有检查的警告
            unqualified-field-access，抑制与栏位存取不合格相关的警告
      unused，忽略没有使用某个变量的警告
4. 关于SuppressWarnings 作用范围，和放置的位置相关
       比如 @SuppressWarnings放置在 main方法，那么抑制警告的范围就是 main
       通常我们可以放置具体的语句, 方法, 类.
    5.  看看 @SuppressWarnings 源码
    (1) 放置的位置就是 TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE
    (2) 该注解类有数组 String[] values() 设置一个数组，比如 {"rawtypes", "unchecked", "unused"}

 */

/*
元注解
JDK的元Annotation用于修饰其他Annotation
元注解：本身作用不大，讲这个原因希望同学们，看源码时，可以知道他是于什么

●元注解的种类（使用不多，了解，不用深入研究）
1）Retention     //指定注解的作用范围，三种（SOURCE，CLASS,RUNTIME）
2）Target        //指定注解可以在哪些地方使用
3）Documented    //指定该注解是否会在javadoc体现
4）Inherited     //子类会继承父类注解

@Retention注解
>说明
只能用于修饰一个 Annotation 定义，用于指定该 Annotation 可以保留多长时间，@Rentention包含
一个 RetentionPolicy 类型的成员变量，使用 @Rentention 时必须为该 value 成员变量指定值
>@Retention的三种值
1）RetentionPolicy.SOURCE（只作用于源码）：编译器使用后，直接丢弃这种策略的注释
2）RetentionPolicy.CLASS：编译器将把注解记录在class文件中.当运行Java程序时，JVM不会保留注解。这是默认值
3）RetentionPolicy.RUNTiME：编译器将把注解记录在class文件中.当运行Java程序时，JVM会保留注释.程序可以通过反射获取该注释

>举例：@Override 的源码为
@Retention(RetentionPolicy.SOURCE)
说明：Override的作用域在SOURcE，当编译器编译时生效，不会写入
到.class文件，也不会再runtime（运行时）生效

@Target
>基本说明
用于修饰Annotation定义，用于指定被修饰的Annotation能用于修饰哪些程序元素.@Target也包含一个名为value的成员变量

@Documented
>基本说明
@Documented:用于指定被该元 Annotation 修饰的 Annotation 类将被 javadoc 工具提取成文档，即在生成文档时，可以看到该注释。
说明：定义为 Documented 的注解必须设置 Retention 值为 RUNTIME

@Inherited注解
被它修饰的Annotation将具有继承性.如果某个类使用了被 @Inherited 修饰的 Annotation ，则其子类将自动具有该注解
说明：实际应用中，使用较少，了解即可。
元注解：本身作用不大

 */