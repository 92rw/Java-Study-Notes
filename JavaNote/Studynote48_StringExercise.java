//编程技巧：写出正确的情况后取反

//将字符串中指定部分进行反转。比如将"abcdf"反转为"aedcbf"
public class Studynote48_StringExercise {

    public static void main(String[] args) {
        String s1 = "abcdef";
        String s2 = reverse(s1,1,5);
        System.out.println(s2);
    }

    public static String reverse(String str,int start, int end){
        //StringBuffer的reverse() 逆序，再替换
        StringBuffer sbf = new StringBuffer(str);
        StringBuffer temp = new StringBuffer(str);
        temp.reverse();
        char[] arrays = new char[end-start];
        temp.getChars(start,end,arrays,0);
        sbf.delete(start, end);
        sbf.insert(start,arrays);
        return sbf.toString();
    }
}

//方法2：利用String类的方法实现
class StringExercise1{
    public static void main(String[] args) {
        String str = "abcdef";
        System.out.println("====交换前====");
        try {
            str = reverse(str, 1, 6);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;//如果不return，将继续执行下面的代码
        }
        System.out.println("====交换后====");
        System.out.println(str);
    }
    public static String reverse(String str,int start, int end){
        //增强程序健壮性
        if(!(str != null && start >= 0 && end >start && end< str.length())) {
            throw new RuntimeException("参数异常");
        }
        //将 String 转成char[]，因为 char[]元素可以交换
        char[] chars = str.toCharArray();
        char temp = ' ';//交换辅助变量
        //双指针问题：逆序索引大于顺序索引
        for (int i = start, j = end; i < j; i++) {
            temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        //chars 重建一个 String 并返回
        return new String(chars);
    }
}
/*
输入用户名、密码、邮箱，如果信息录入正确，则提示注册成功，否则生成异常对象
要求：
（1）用户名长度为2或3或4
（2）密码的长度为6，要求全是数字
(3）邮箱中包含@和并且@在.的前面
 */
class StringExercise2{
    public static void main(String[] args) {
        String name = "浪迹天涯1";
        String pwd = "012306";
        String email = "a@b.com";

        try {
            userRegister(name,pwd,email);
            System.out.println("恭喜，注册成功");
        } catch (Exception e){
            System.out.print(e.getMessage());
            System.out.println(" 注册失败");
        }
    }
    public static void userRegister(String username, String password, String email){
        if (!(username != null && password != null && email != null)) {
            throw new RuntimeException("输入值不能为空");
        }

        //判断用户名、密码、邮箱的条件
        int userLength = username.length();
        if (!(userLength > 1 && userLength < 5)) {
            throw new RuntimeException("用户名长度应在2-4位");
        }

        if(!(password.length() == 6 && isDigital(password))) {
            throw new RuntimeException("密码应为6位数字");
        }

        int i = email.indexOf('@');
        int j = email.lastIndexOf('.');//防止用户名中出现标点
        if (!(1 > 0 && j > i + 1)) {
            throw new RuntimeException("邮箱中包含@和并且@在.的前面");
        }
    }
    public static boolean isDigital(String str){
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(chars[i] <'0' || chars[i] > '9') {
                return false;
            }
        }
        return true;
    }
}

/*
（1）编写java程序，输入形式为：Han Shun Ping的人名，以Ping, Han. S的形式打印出来。其中.S是中间单词的首字母。
(2）例如输入"Willian Jefferson Clinton"，输出形式为：Clinton, Willian. J
 */
class StringExercise3{
    public static void main(String[] args) {
        String name = "Willian Jefferson Clinton";
        nameConvert(name);
    }
    public static void nameConvert(String str){
        if (str == null) {
            System.out.println("姓名不能为空");
            return;
        }

        String[] split = str.split(" ");
        if (split.length != 3){
            System.out.println("输入字符串格式应为 XXX XXX XXX");
        }
        char middle = split[1].toUpperCase().charAt(0);//不能先charAt再转大写，因为转大写方法限定String，但是charAt以后就是char字符了
        String printName = String.format("%s, %s. %s", split[split.length - 1], split[0], middle);
        System.out.println(printName);
    }
}
//输入字符串，判断里面有多少个大写字母，多少个小写字母，多少个数字
class StringExercise4{
    public static void main(String[] args) {
        markCount("AZaz129");
    }
    public static void markCount(String str){
        if (str == null) {
            System.out.println("字符串不能为空");
            return;
        }
        //此处也可以调用Character的isDigit、isUpperCase、isLowerCase
        int Upper = 0;
        int Lower = 0;
        int Nums = 0;
        int Others = 0;
        //方法1：str.length()获取字符串长度，用i遍历str.charAt(i)。例如Character.isUpperCase(str.charAt(i))
        //方法2：str.toCharArray()转成字符数组再遍历。
        char[] strs = str.toCharArray();
        for (int i = 0; i < strs.length; i++) {
            if(strs[i] >= '0' && strs[i] <= '9'){
                Nums++;
            }else if(strs[i] >= 'A' && strs[i] <= 'Z'){
                Upper++;
            }else if(strs[i] >= 'a' && strs[i] <= 'z'){
                Lower++;
            } else{
                Others++;
            }
        }
        /*方法3
        int capital = str.length() - str.replaceAll("^*[A-Z]*", "").length();
        int number = str.length() - str.replaceAll("^*[0-9]*", "").length();
         */
        String count = String.format("大写字母 %d 个，小写字母 %d 个，数字 %d 个，其他 %d 个", Upper, Lower, Nums,Others);
        System.out.println(count);
    }
}