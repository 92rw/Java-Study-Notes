import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式的底层实现机制
 */
public class Studynote91_RegExp {
    public static void main(String[] args) {

        String content = "1998年12月8日，第二代Java平台的企业版J2EE发布。1999年6月，Sun公司发布了" +
                "第二代Java平台（简称为Java2）的3个版本：J2ME（Java2 Micro Edition，Java2平台的微型" +
                "版），应用于移动、无线及有限资源的环境；J2SE（Java 2 Standard Edition，Java 2平台的" +
                "标准版），应用于桌面环境；J2EE（Java 2Enterprise Edition，Java 2平台的企业版），应" +
                "用3443于基于Java的应用服务器。Java 2平台的发布，是Java发展过程中最重要的一个" +
                "里程碑，标志着Java的应用开始普及9889 ";
        //目标：匹配所有四个数字
        //说明
        //1. \\d 表示一个任意的数字
        String regStr = "(\\d\\d)(\\d\\d)";
        //2. 创建模式对象[即正则表达式对象]
        Pattern pattern = Pattern.compile(regStr);
        //3. 创建匹配器
        //说明：创建匹配器matcher， 按照 正则表达式的规则 去匹配 content字符串
        Matcher matcher = pattern.matcher(content);

        //4.开始匹配
        /**
         *
         * matcher.find() 完成的任务 （考虑分组）
         * 什么是分组，比如  (\d\d)(\d\d) ,正则表达式中有() 表示分组,第1个()表示第1组,第2个()表示第2组...
         * 1. 根据指定的规则 ,定位满足规则的子字符串(比如(19)(98))
         * 2. 找到后，将 子字符串的开始的索引记录到 matcher对象的属性 int[] groups;
         *    2.1 groups[0] = 0 , 把该子字符串的结束的索引+1的值记录到 groups[1] = 4
         *    2.2 记录1组()匹配到的字符串 groups[2] = 0  groups[3] = 2
         *    2.3 记录2组()匹配到的字符串 groups[4] = 2  groups[5] = 4
         *    2.4.如果有更多的分组，以此类推.....
         * 3. 同时记录oldLast 的值为 子字符串的结束的 索引+1的值即4, 即下次执行find时，就从4开始匹配
         *
         * matcher.group(0) 分析
         *
         * 源码:
         * public String group(int group) {
         *         if (first < 0)
         *             throw new IllegalStateException("No match found");
         *         if (group < 0 || group > groupCount())
         *             throw new IndexOutOfBoundsException("No group " + group);
         *         if ((groups[group*2] == -1) || (groups[group*2+1] == -1))
         *             return null;
         *         return getSubSequence(groups[group * 2], groups[group * 2 + 1]).toString();
         *     }
         *  1. 底层使用subString截取字串，需要提供字串在文本中的索引。根据 groups[0]=0 和 groups[1]=4
         *   记录的位置，从content开始截取子字符串返回，就是 [0,4) 包含 0 但是不包含索引为 4的位置
         *
         *  2.执行完一个之后，指针停留在上一次的索引+1的位置，执行下一次的时候，指针根据groups[0]对应的值
         *  移动指针。如果再次执行 find方法.仍然按上面的规则来执行
         */
        while (matcher.find()) {
            //小结
            //1. 如果正则表达式有() 即分组
            //2. 取出匹配的字符串规则如下
            //3. group(0) 表示匹配到的子字符串整体
            //4. group(1) 表示匹配到的子字符串的第1组字串
            //5. group(2) 表示匹配到的子字符串的第2组字串
            //6. ... 但是分组的数不能越界.
            System.out.println("找到: " + matcher.group(0));
            System.out.println("第1组()匹配到的值=" + matcher.group(1));
            System.out.println("第2组()匹配到的值=" + matcher.group(2));


        }
    }
}

class testRegExp {
    public static void main(String[] args) {
        String content = "刘一\t陈二\r张三\n李四王五赵六孙七周八吴九郑十";
        String regStr = "\\s";
//        String regStr = ".";
//        String content = "nyaameowmeownyaaa\tnyaaaa\rnyaaaaa\nnyaaaaaa";
//        String regStr = "\\bnya+";
//        String content = "三井物产 三井化学 三井金属";
//        String regStr = "三井(物产|化学|金属)";


        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()){
            System.out.println("找到" + matcher.group(0));
            //System.out.println("找到" + matcher.group(1));
        }
    }
}

/**
 * 演示Matcher类的相关方法
 */
class MatcherMethod {
    public static void main(String[] args) {
        String content = "nyaa Nya jerry nyapa hello smith hello nYa nyA";
        String regStr = "hello";

        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("=================");
            System.out.println(matcher.start());
            //end方法得到的是子字符串末尾索引+1
            System.out.println(matcher.end());//可直接将数据传入substring方法截取字符串
            System.out.println("找到: " + content.substring(matcher.start(), matcher.end()));
        }

        //整体匹配方法，常用于，去校验某个字符串是否满足某个规则
        pattern = Pattern.compile("nya.*");//.可以匹配除换行外所有的字符
        matcher = pattern.matcher(content);
        System.out.println("整体匹配=" + matcher.matches());

        //完成如果content 有 nya 替换成 喵
        regStr = "nya";
        pattern = Pattern.compile(regStr, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(content);
        //注意：返回的字符串才是替换后的字符串 原来的 content 不变化
        String newContent = matcher.replaceAll("喵");
        //String类的字符串不能修改，因此replaceAll方法得到的是一个新字符串
        System.out.println("newContent=" + newContent);
        System.out.println("content=" + content);

    }
}

/**
 * 演示反向引用的使用
 */
class BackwardReference{
    public static void main(String[] args) {
        //要匹配个位与千位相同，十位与百位相同的数 5225 , 1551  (\\d)(\\d)\\2\\1
        //String regStr = "(\\d)(\\d)\\2\\1";
        String brString = "上海自来水来自海上12306-777888999黄山落叶松叶落山黄95306-777333444山西悬空寺空悬西山" +
                "わたしまけましたわ一块五花肉花五块一redivider前门停车场车停门前reconocer海南护卫舰卫护南海よのなかばかなのよ";
        String palindrome = "(.)(.)(.)(.).\\4\\3\\2\\1";
        Pattern pattern = Pattern.compile(palindrome);
        Matcher matcher = pattern.matcher(brString);
        System.out.println("=====匹配到的回文=====");
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
        //商品标签：5个随机数字+三组3个相同数字
        //思路1：三个相同数字=1个括号内+2个括号外(\\d)\\1{2}(\\d)\\2{2}(\\d)\\3{2}
        //思路2：（（数字）*2））*3
        String numberMark = "\\d{5}-((\\d)\\2{2}){3}";
        pattern = Pattern.compile(numberMark);
        matcher = pattern.matcher(brString);
        System.out.println("=====匹配到的商品标签=====");
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }
}

/**
 * 处理连续重复的字符
 */
class SolveStutter{
    public static void main(String[] args) {
        String content = "我....我要....学学学学....编程java!";

        //1. 去掉所有的.

        Pattern pattern = Pattern.compile("\\.");
        Matcher matcher = pattern.matcher(content);
        content = matcher.replaceAll("");

        System.out.println("去掉所有的“.”后\t" + content);

        //2. 去掉重复的字  我我要学学学学编程java!
        // 思路
        //(1) 使用 (.)\\1+
        //(2) 使用 反向引用$1 来替换匹配到的内容
        // 注意：因为正则表达式变化，所以需要重置 matcher
//        pattern = Pattern.compile("(.)\\1+");//分组的捕获内容记录到$1
//        matcher = pattern.matcher(content);
//        while (matcher.find()) {
//            System.out.println("找到=" + matcher.group(0));
//        }
//
//        //使用 反向引用$1 来替换匹配到的内容
//        content = matcher.replaceAll("$1");
//        System.out.println("content=" + content);

        //3. 使用一条语句 去掉重复的字  我我要学学学学编程java!
        String method1 = Pattern.compile("(.)\\1+").matcher(content).replaceAll("$1");
        //直接写+的话，就全文都匹配到了，这里是匹配有重复字的，所以要反向引用

        System.out.println("使用Matcher类的replaceAll方法\t" + method1);
        //此处"$1"也可以换成空格，然后用trim()方法去掉空格
        content.replaceAll("(.)\\1*","$1");
        System.out.println("使用String类的replaceAll方法\t" + method1);


    }
}

class SolveURL {
    public static void main(String[] args) {
        String content = "http://www.sohu.com:8080/abc/xxx/yyy/////inde@#$%x.htm";

        //因为正则表达式是根据要求来编写的，所以，如果需求需要的话，可以改进.
        String regStr = "^([a-zA-Z]+)://([a-zA-Z.]+):(\\d+)[\\w-/]*/([\\w.@#$%]+)$";

        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);

        if(matcher.matches()) {//整体匹配, 如果匹配成功，可以通过group(x), 获取对应分组的内容
            //说明：可以给括号命名，会更方便检索
            System.out.println("整体匹配=" + matcher.group(0));
            System.out.println("协议: " + matcher.group(1));
            System.out.println("域名: " + matcher.group(2));
            System.out.println("端口: " + matcher.group(3));
            System.out.println("文件: " + matcher.group(4));
        } else {
            System.out.println("没有匹配成功");

        }

    }
}




/**
 * 在String类中使用正则表达式
 */
class RegString {
    public static void main(String[] args) {
        //演示public String replaceAll(String regex, String replacement) 方法
        String sensitiveWord = "自相残杀 士可杀不可辱 借刀杀人 杀鸡取卵 吾好梦中杀人";
        //由于String类本身的地址不可改变，因此需要重新赋值
        sensitiveWord = sensitiveWord.replaceAll("杀","□");
        System.out.println("去敏感词后："+sensitiveWord);

        //演示 public boolean matches(String regex) 方法
        //验证手机号，要求必须以138 139开头
        //这个方法整体匹配，因此不需要加^和$
        String phoneNum = "13966669999";
        if (phoneNum.matches("13[89]\\d{8}")) {
            System.out.println("手机验证通过");
        } else {
            System.out.println("手机号码有误");
        }

        //演示public String[] split(String regex)方法
        //要求按照 # 或者 - 或者 ~ 或者 数字 来分割
        System.out.println("===================");
        String content = "hello#abc-jack12smith~北京";
        String[] split = content.split("[#\\-~\\d]+");
        for (String s : split) {
            System.out.println(s);
        }
    }
}

class EmailCheck {
    public static void main(String[] args) {
        //规定电子邮件规则为
        //只能有一个@
        //@前面是用户名,可以是a-z A-Z 0-9 _-字符
        //@后面是域名,并且域名只能是英文字母， 比如 sohu.com 或者 tsinghua.org.cn
        //        写出对应的正则表达式, 验证输入的字符串是否为满足规则

        String content = "hsp@tsinghua.org.cn kkk";
        //说明：A-z在ASCII码表中有[\]^_`这些字符，因此不能用A-z表示字母
        String regStr = "^[\\w-]+@([a-zA-Z]+\\.)+[a-zA-Z]+$";

        //老师说明
        //1. String 的 matches 是整体匹配
        //2. 看看这个matches 底层
        /**
         * String 的 matches
         *  public boolean matches(String regex) {
         *         return Pattern.matches(regex, this);
         *     }
         *
         *  Pattern
         *  public static boolean matches(String regex, CharSequence input) {
         *         Pattern p = Pattern.compile(regex);
         *         Matcher m = p.matcher(input);
         *         return m.matches();
         *     }
         *
         *  Mather类 match
         *  Attempts to match the entire region against the pattern
         *  public boolean matches() {
         *         return match(from, ENDANCHOR);
         *     }
         */
        if (content.matches(regStr)) {
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配失败");
        }

    }
}

class CheckNumber {

    public static void main(String[] args) {
        //要求验证是不是整数或者小数
        //提示： 这个题要考虑正数和负数
        //比如： 123 -345 34.89 -87.9 -0.01 0.45 等
        /**
         * 老师的思路
         * 1. 先写出简单的正则表达式
         * 2. 在逐步的完善[根据各种情况来完善]
         */
        String content = "0.00"; //
        String regStr = "^[-+]?([1-9]\\d*|0)(\\.\\d+)?$";

        if(content.matches(regStr)) {
            System.out.println("匹配成功 是整数或者小数");
        } else {
            System.out.println("匹配失败");
        }
    }
}
