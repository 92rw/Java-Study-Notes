import java.lang.Math;
/*
Math 类基本介绍
Math 类包含用于执行基本数学运算的方法，如初等指数、对数、平方根和三角函数，均为静态方法
常用方法
1）abs绝对值
2）pow求幂
3）ceil向上取整
4）floor向下取整
5）round四舍五入
6）sqrt求开方
7）random求随机数//思考
请写出获取a-b之间的一个随机整数，a，b均为整数？2-7
8）max求两个数的最大值
9）min求两个数的最小值
 */

class MathMethod {
    public static void main(String[] args) {
        //Math 类常用的方法(静态方法)
        //1.abs 绝对值
        int abs = Math.abs(-9);
        System.out.println(abs);//9

        //2.pow 求幂
        double pow = Math.pow(2, 4);//2的4次方
        System.out.println(pow);//16.0（double型）

        //3.ceil 向上取整,返回>=该参数的最小整数(转成double);
        double ceil = Math.ceil(3.9);
        System.out.println(ceil);//4.0

        //4.floor 向下取整，返回<=该参数的最大整数(转成double)
        double floor = Math.floor(4.001);
        System.out.println(floor);//4.0

        //5.round 四舍五入  Math.floor(该参数+0.5)
        long round = Math.round(5.51);
        System.out.println(round);//6

        //6.sqrt 求开方。负数执行此方法，返回NaN
        double sqrt = Math.sqrt(9.0);
        System.out.println(sqrt);//3.0

        //7.random 求随机数：返回的是 0 <= x < 1 之间的一个随机小数
        // 思考：请写出获取 a-b之间的一个随机整数,a,b均为整数
        // 分析：(int)(a) <= x <= (int)(a + Math.random() * (b-a + 1) )
        // 因为double强转int不管小数点后面的数，直接取整数，所以要 (int) 转换后会丢失小数部分，
        // 如果使用 Math.random() * (b-a) 不加1，返回的范围是 0  <= 数 < b-a，只能取到2到6
        // 这个是由于强制转换都向下取整了，如果自己写向上取整也可以b-a就行

        // 公式就是  (int)(a + Math.random() * (b-a +1) )
        for(int i = 0; i < 100; i++) {
            System.out.print((int)(2 +  Math.random() * (7 - 2 + 1)) + " ");
        }
        System.out.println();

        //max , min 返回最大值和最小值
        int min = Math.min(1, 9);
        int max = Math.max(45, 90);
        System.out.println("min=" + min);
        System.out.println("max=" + max);

    }
}
