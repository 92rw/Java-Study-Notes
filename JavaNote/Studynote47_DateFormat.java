import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
//注意不是 java.sql.Date

public class Studynote47_DateFormat {
}
/*
第一代日期类（很多方法已过时）
1）Date：精确到毫秒，代表特定的瞬间
2）SimpleDateFormat：格式和解析日期的类
SimpleDateFormat格式化和解析日期的具体类。它允许进行格式化（日期->文本）、解析（文本->日期）和规范化
字母	日期或时间元素	    表示	    示例
G	Era 标志符	            Text	    AD
y	年	                    Year	    1996; 96
M	年中的月份	            Month	    July; Jul; 07
w	年中的周数	            Number	    27
W	月份中的周数          	Number	    2
D	年中的天数	            Number	    189
d	月份中的天数          	Number  	10
F	月份中的星期          	Number	    2
E	星期中的天数          	Text	    Tuesday; Tue
a	Am/pm 标记	            Text    	PM
H	一天中的小时数（0-23）	Number	    0
k	一天中的小时数（1-24）	Number	    24
K	am/pm 中的小时数（0-11）	Number  	0
h	am/pm 中的小时数（1-12）	Number	    12
m	小时中的分钟数         	Number  	30
s	分钟中的秒数          	Number	    55
S	毫秒数	                Number	    978
z	时区	        General time zone	Pacific Standard Time; PST; GMT-08:00
Z	时区	        RFC 822 time zone	-800
 */
class Date_ {
    public static void main(String[] args) throws ParseException {

        //Date(): 获取当前系统时间
        //默认输出的日期格式是国外的方式, 因此通常需要对格式进行转换
        Date d1 = new Date(); //获取当前系统时间
        System.out.println(d1);
        System.out.println("当前日期=" + d1);
        Date d2 = new Date(9234567); //通过指定毫秒数得到时间
        System.out.println("d2=" + d2); //获取某个时间对应的毫秒数
//

        //SimpleDateFormat()：可以指定相应的格式
        //这里的格式使用的字母是规定好，不能乱写

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss E");
        String format = sdf.format(d1); // format:将日期转换成指定格式的字符串
        System.out.println("当前日期=" + format);

        //SimpleDateFormat().parse()：把一个格式化的String 转成对应的 Date
        //得到Date 仍然在输出时，还是按照国外的形式，如果希望指定格式输出，需要转换
        //parse方法显式地声明了可能会抛出的异常(比如 2月30日，或是时间格式不一致)，调用者必须进行编译异常处理

        String s = "1996年01月01日 10:20:30 Mon";
        Date parse = sdf.parse(s);
        System.out.println("parse=" + sdf.format(parse));

    }
}
/*
第二代日期类
1）第二代日期类，主要就是Calendar类（protected构造器）
实现接口： Serializable, Cloneable, Comparable
2)Calendar类是一个抽象类，它为特定瞬间与一组诸如YEAR、MONTH、DAY_OF_MONTH、HOUR 等
日历字段之间的转换提供了一些方法，并为操作日历字段（例如获得下星期的日期）提供了一些方法。
 */

class Calendar_ {
    public static void main(String[] args) {
        //说明
        //1）Calendar 是一个抽象类，并且构造器是protected，只能通过 getInstance() 来获取实例
        //  Calendar是一个抽象类不可以被实例化，看源码可以发现
        //2）Calendar没有提供对应的格式化的类，因此需要程序员自己组合来输出(灵活)
        //3）如果我们需要按照 24小时进制来获取时间， Calendar.HOUR ==改成=> Calendar.HOUR_OF_DAY

        //1.getInstance() 获取到的实例包含大量字段信息，可用对应名称获取
        Calendar c = Calendar.getInstance();
        System.out.println("c=" + c);

        //2.获取日历对象的某个日历字段
        System.out.println("年：" + c.get(Calendar.YEAR));
        //月的下标从零开始，因此需要 + 1
        System.out.println("月：" + (c.get(Calendar.MONTH) + 1));//
        System.out.println("日：" + c.get(Calendar.DAY_OF_MONTH));
        System.out.println("12时制小时：" + c.get(Calendar.HOUR));
        System.out.println("24时制小时：" + c.get(Calendar.HOUR_OF_DAY));
        System.out.println("分钟：" + c.get(Calendar.MINUTE));
        System.out.println("秒：" + c.get(Calendar.SECOND));
        //Calender 没有专门的格式化方法，所以需要程序员自己来组合显示
        System.out.println(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) +
                " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) );

        String dateformat = String.format("%d年%d月%d日", c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        System.out.println(dateformat);
    }
}
/*
第三代日期类
●前面两代日期类的不足分析
JDK1.0中包含了一个 java.util.Date 类，但是它的大多数方法已经在 JDK1.1 引入Calendar类之后被弃用了。
而Calendar也存在问题是：
1）可变性：像日期和时间这样的类应该是不可变的
2）偏移性：Date中的年份是从1900开始的，而月份都从0开始
3）格式化：格式化只对Date有用，Calendar则不行
4)此外，它们也不是线程安全的；不能处理闰秒等（每隔2天，多出1s）

●第三代日期常见类，JDK8 加入
1）LocalDateTime（日期时间）
LocalDate只包含年月日，不包含时分秒
LocalTime只包含时分秒，不包含年月日
LocalDateTime 包含日期+时间，可以获取日期和时间字段
2）DateTimeFormatter 类：修改日期时间格式（类似SimpleDateFormat）
3）Instant时间戳：类似于Date，提供了一系列和Date类转换的方式

//LocalDateTime是系统所在时区的时间点，它是替代旧版Calendar的，另外还有带时区的ZonedDateTime
//Instant是不带时区的时间点，默认是UTC时间点；Instant是 精确到纳秒级别的，可以跟Date相互转换
4）MonthDay、Year、YearMonth 类：检香重复时间

 */
class LocalDate_ {
    public static void main(String[] args) {
        //第三代日期
        //1. 使用now() 返回表示当前日期时间的 对象
        LocalDateTime ldt = LocalDateTime.now();

        //可参考 jdk1.8 手册
        System.out.println("年=" + ldt.getYear());
        System.out.println("月（英文）=" + ldt.getMonth());
        System.out.println("月（数值）=" + ldt.getMonthValue());
        System.out.println("日=" + ldt.getDayOfMonth());
        System.out.println("时=" + ldt.getHour());
        System.out.println("分=" + ldt.getMinute());
        System.out.println("秒=" + ldt.getSecond());

        LocalDate now1 = LocalDate.now(); //只能获取年月日
        LocalTime now2 = LocalTime.now(); //只能获取时分秒
        //日期格式化前
        System.out.println(ldt);

        //2. 使用DateTimeFormatter 对象来进行格式化
        // 创建 DateTimeFormatter对象
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateTimeFormatter.format(ldt);
        System.out.println("格式化的日期=" + format);



        //提供 plus 和 minus方法可以对当前时间进行加或者减
        //看看890天后，是什么时候 把 年月日-时分秒
        LocalDateTime localDateTime = ldt.plusDays(890);
        System.out.println("890天后=" + dateTimeFormatter.format(localDateTime));

        //看看在 3456分钟前是什么时候，把 年月日-时分秒输出
        LocalDateTime localDateTime2 = ldt.minusMinutes(3456);
        System.out.println("3456分钟前 日期=" + dateTimeFormatter.format(localDateTime2));

    }
}

class Instant_ {
    public static void main(String[] args) {

        //1.通过 静态方法 now() 获取表示当前时间戳的对象
        Instant now = Instant.now();
        System.out.println(now);
        //2. 通过 from 可以把 Instant转成 Date
        Date date = Date.from(now);
        System.out.println(date);
        //3. 通过 date的toInstant() 可以把 date 转成Instant对象
        Instant instant = date.toInstant();
        System.out.println(instant);
    }
}