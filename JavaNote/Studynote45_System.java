import java.util.Arrays;

public class Studynote45_System {
    public static void main(String[] args) {
        //1)exit退出当前程序
        System.out.println("ok1");
        System.exit(0);
        //此处0表示正常状态退出
        //return是函数的退出，exit是进程的退出
        System.out.println("ok2");
    }
}
/*
System类
System类常见方法和案例

1)exit退出当前程序
2)arraycopy：复制数组元素，比较适合底层调用，一般使用Arrays.copyOf完成复制数组

3)currentTimeMillis:返回当前时间距离1970-1-1的毫秒数，java起源于UNIX系统，而UNIX认为1970年1月1日0点是时间纪元.
4)gc:运行垃圾回收机制System.gc();
 */

class SystemMethod{
    public static void main(String[] args) {
        //2)arraycopy：复制数组元素，比较适合底层调用，一般使用Arrays.copyOf完成复制数组
        int[] src = {1,2,3};
        int[] dest = new int[3];//当前状态{0,0,0}
        //System.arraycopy(源数组, 起始索引, 目标数组, 新数组起始索引, 拷贝元素数}
        System.arraycopy(src, 0, dest, 0, src.length);
        System.out.println("src = " + Arrays.toString(src));
        System.out.println("dest= " + Arrays.toString(dest));

        //3)currentTimeMillis：返回当前时间距离1970-1-1的毫秒数，可以计算执行一个程序所用的时间
        System.out.println(System.currentTimeMillis());
    }
}