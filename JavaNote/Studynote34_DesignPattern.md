# 设计模式综述

根据文章 [设计模式也可以这么简单_Javadoop](https://github.com/yifanzheng/java-notes/blob/master/docs/java/设计模式也可以这么简单.md)，[永不磨灭的设计模式 - ShuSheng007](https://shusheng007.top/2021/09/07/design-pattern/)，[Design Pattern - Overview (tutorialspoint.com)](https://www.tutorialspoint.com/design_pattern/design_pattern_overview.htm) ，[快速记忆23种设计模式 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/128145128)，[23 种设计模式详解（全23种） - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/575645658)，[分类: Java 设计模式 | Star's Tech Blog (yifanstar.top)](http://yifanstar.top/categories/Java-设计模式/) 提炼总结

![设计模式间的关系](https://www.runoob.com/wp-content/uploads/2014/08/the-relationship-between-design-patterns.jpg)

## 六大原则

总原则 开闭原则（Open Close Principle）：开发完成的类尽量不修改，而是通过继承增加新的类

1. 单一职责（Single Responsibility Principle）：一个类应该只负责一个职责
2. 里氏替换原则（Liskov Substitution Principle）：将调用某个类的地方修改为调用其子类，系统仍然正常工作
3. 依赖倒置原则（Dependence Inversion Principle）：面向接口编程，只和上层抽象接口交互，不和具体类交互（详见文章：[依赖倒置原则 | 蝉沐风 (chanmufeng.com)](https://www.chanmufeng.com/posts/basic/design-principle/依赖倒置原则.html)）
4. 接口隔离原则（Interface Segregation Principle）：只提供都子类用得到的接口方法，子类特有的方法专门另外创建接口
5. 迪米特原则（Law of Demeter 又名Least Knowledge Principle）：调用者不需要知道依赖类的内部实现，只需要调用方法
6. 合成复用原则（Composite Reuse Principle）：将已有对象纳入新对象中，而不是通过继承调用

## 创建型：返回实例对象

简单工厂模式：工厂类 XxxFactory提供静态方法，根据传入参数返回实现接口的对象实例

①工厂模式Factory：需要新建 XXXFactory 接口的实现类实例，再调用接口中的方法

②抽象工厂模式Abstract Factory：工厂接口下面的子方法生成的实例，作为最终目标对象的组件，需要被组装后共同使用

③单例模式Singleton：保证一个类仅有一个实例，并提供一个访问它的全局访问点。饿汉式在第一次调用时创建实例，懒汉式在调用时才新建实例

④建造者模式Builder：链式调用将所有的属性都设置给 XXXBuilder，执行 build() 方法的时候，将这些属性**复制**给实际产生的对象。

⑤原型模式Prototype：通过复制现有实例来创建新的实例，无需知道相应类的信息。浅复制：引用类型指向原来的；深复制：实现 Cloneable 接口，使用时通过Object 类中的 clone() 方法返回新实例



## 结构型：组合类与对象

①代理模式Proxy：内部有注入 Impl 类实例。在调用父类接口的方法时，前后都可以加入Proxy类的业务逻辑，实现方法包装（方法增强）

②适配器模式Adapter

* 默认适配器模式：当父接口存在大量抽象方法，而实际只需要实现其中一部分时，通过Adaptor类做缓冲，继承该类并实现需要的方法
* 对象适配器模式：Adaptor类实现目标接口，构造方法中注入要代理的对象，实现的接口方法中实际是代理对象的方法（使用较多）
* 类适配器模式：Adaptor实现目标接口，继承提供方法的类。（静态实现，和对象适配器相比，不需要传入实例化对象）

③桥接模式Bridge：将抽象部分（抽象类）与它的实现部分（接口）分离，实现部分不通过抽象部分实现，使它们都可以独立地变化。整个类图看起来像一座桥，所以称为桥接模式

④装饰器模式Decorator：在构造器中注入了所在接口的实例对象，在实现方法中对于内部的实例对象进行操作，实现在不改变结构的情况下向现有对象添加功能（IO流的类调用）

⑤外观模式Facade：在Maker类构造方法中新建对应接口的不同实例，外部类可以直接调用Maker类的不同方法实现不同实例的功能（不需要关心实例化过程）

⑥组合模式Composite：创建了一个包含自己对象组的类，提供了修改相同对象组的方式。根据树形结构组合对象（算术表达式包括操作数、操作符和另一个操作数，其中，另一个操作数也可以是操作数、操作符和另一个操作数。）

⑦享元模式Flyweight：复用已经生成的对象，例如需要对象实例时，先检查内存中是否存在，避免重新创建



## 行为型：完成类或对象间的通信

### 父类与子类

①策略模式Strategy：相当于简化版的桥梁模式，在构造方法注入接口实现类对象，包装具体的算法和行为

②模板方法模式Template Method：抽象基类的模板总方法中，定义类方法的执行顺序，由子类完成每个步骤的具体实现（类似生命周期函数）

### 两个类之间

③观察者模式Observer：Subject类构造器新建观察者对象的List集合，Observer类的构造器传入Subject类的对象、调用Subject类对象的添加集合方法，当属性变化时Subject类实例会通过通知方法遍历集合，调用Observer类的观察方法

④迭代器模式Iterator：Iterator接口定义遍历元素的方法：取得下一个元素的方法next()，判断是否遍历结束的方法hasNext()），移出当前对象的方法remove()；容器提供方法返回接口实例。实现`java.lang.Iterable`接口，在重写的方法中实现`Iterator`接口

⑤责任链Chain of Responsibility：在抽象基类中有一个本类的内部变量，提供本类实例的getter、setter方法，让子类实现抽象方法，以链表的形式传递调用关系

⑥命令模式Command：Command接口供所有类调用，接口实现类在构造方法中注入Receiver对象，各自只实现一个请求。Invoker类维护了List\<Command> 集合

### 类的状态

⑦备忘录模式Memento：Memento类是POJO类记录数据，CareTaker类处理Memento完成数据保存和恢复，Originator类负责调用

⑧状态模式State：在State接口的doAction方法传入Context类实例，Content类有State类型的内部变量，并提供其getter、setter方法

### 通过中间类

⑨访问者模式Visitor：Element接口和实现类定义了需要完成的内容，在accept方法传入Visitor接口实例；ObjectStructure类维护了List\<Element> 集合，可以遍选择性调用集合实例的accept方法；Visitor接口和实现类定义了需要完成的操作

⑩中介者模式Mediator：使用中介类解耦不同类之间的调用关系，降低复杂度

⑪解释器模式Interpreter：根据传入内容的关键字，提供返回值（可用于制作问答器）





## J2EE模式

MVC 模式

业务代表模式Business Delegate

组合实体模式Composite Entity

数据访问对象模式Data Access Object

前端控制器模式Front Controller

拦截过滤器模式Intercepting Filter

服务定位器模式Service Locator

传输对象模式Transfer Object






过滤器模式Filter（结构型）：新建Criteria接口，提供meetCriteria方法，传入集合，在方法中遍历集合并根据条件过滤，返回过滤后的集合

空对象模式Null Object （行为型）：接口中新增isNull方法，且专门新建一个空对象类，避免客户端出现空指针异常
