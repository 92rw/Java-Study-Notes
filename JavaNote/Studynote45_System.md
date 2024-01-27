# System类

System类常见方法和案例

1. 退出当前程序 `exit(状态码)`

   ```java
       public static void exit(int status) {
           Runtime.getRuntime().exit(status);
       }
   ```

   使用案例

   ```java
   public class Studynote45_System {
   	public static void main(String[] args) {
            //1)exit退出当前程序
            System.out.println("ok1");
            System.exit(0);
            //此处0表示正常状态退出
            //return是函数的退出，exit是进程的退出
            System.out.println("ok2");//无法输出这段文本
        }
   }
   ```



2. 复制数组元素 `arraycopy(源数组, 起始索引, 目标数组, 新数组起始索引, 拷贝元素数)`

   比较适合底层调用，一般使用Arrays.copyOf完成复制数组

   ```java
       public static native void arraycopy(Object src,  int  srcPos,
                                           Object dest, int destPos,
                                           int length);
   ```

   注意事项：

   * 用原数组下标中的值替换目标数组中的值，因此不能超过边界
   * 所有参数不能为负数

   ```java
   int[] arr ={0,1,2,3,4,5,6};
   System.arraycopy(arr,0,arr,3,3);
   
   //这个方法本质是替换，所以arr数组变成{0,1,2,0,1,2,6}
   ```



3. 获取当前系统时间，以毫秒为单位 `currentTimeMillis()`

   获取当前时间的`new Date()` 方法，底层调用了这个方法

   ```java
   public static native long currentTimeMillis();
   ```

   java起源于UNIX系统，而UNIX认为1970年1月1日0点是时间纪元

   ```java
       //可以直接把这个方法强制转换成date类型
       Date nowTime = new Date(System.currentTimeMillis());
       //设定显示格式
       SimpleDateFormat sdFormatter = new simpleDateFormat("yyyy-MM-dd");
       //按指定格式转换
       String now= sdFormatter.format(nowTime);
       System.out.println(now);
   ```

   

4. 运行垃圾回收机制 

   垃圾回收是 Java 运行时系统的一部分，负责自动释放不再被程序使用的内存资源，以便回收内存。

   ```java
   System.gc();
   ```

   此方法的调用并不是强制垃圾回收的方法，而是向垃圾回收器发出建议进行垃圾回收。实际上，Java 的垃圾回收是由 Java 虚拟机自动管理的，而不需要手动干预。因此，程序员一般不需要主动调用 `System.gc()`。

   这个方法的调用会启动垃圾回收器，但是垃圾回收器是否真正执行垃圾回收，以及何时执行，是由虚拟机的具体实现决定的。一些虚拟机可能会忽略 `System.gc()` 的调用，而一些可能会响应这个调用。

   需要注意的是，过度使用 `System.gc()` 可能会导致性能问题，因为频繁的垃圾回收可能会中断程序的正常执行。因此，通常建议让垃圾回收器自动管理内存，只在极端情况下或特殊需求下才考虑手动调用 `System.gc()`。

   总的来说，一般情况下，不建议依赖手动调用 `System.gc()` 来管理内存，而是让垃圾回收器根据需要自动运行。
