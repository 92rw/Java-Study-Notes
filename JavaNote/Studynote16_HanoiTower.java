
/*
汉诺塔传说
汉诺塔：汉诺塔（又称河内塔）问题是源于印度一个古老传说的益智玩具。
大梵天创造世界的时候做了三根金刚石柱子，在一根柱子上从下往上按照大小顺序摞着64片圆盘，
大梵天命令婆罗门把圆盘从下面·开始按大小顺序重新摆放在另一根柱子上。并且规定，在小圆盘上不能放大圆盘，
在三根柱子之间一次只能移动一个圆盘。
假如每秒钟移动一次，移完这些金片需要5845.54亿年以上，太阳系的预期寿命据说也就是数百亿年。
真的过了5845.54亿年，地球上的一切生命，连同梵塔、庙宇等，都早已经灰飞烟灭
*/
public class Studynote16_HanoiTower {
	public static void main(String[] args){
		Tower Hanoi = new Tower();
		Hanoi.move(3, 'A', 'B', 'C');

		//1.在定义方法时，再多定义一个 int n 形参用来计步，同时前面的void要改成int，要将步数返回

		//3.if 和 else 的两个代码块的末尾添加 return n; 将 n 返回；
		//4.else中，递归的两个 move() 方法中，把 n 传入，左边再用 n 来接收返回的值
		//另外 int 的值最大到2^31-1，再算再多的盘子步数应该要用上 long 型了
	}
}


class Tower{
	//使用递归回溯的思想解决老鼠出迷宫

	//num表示要移动的盘子个数，a,b,c分别表示三座塔
	public void move(int num, char a, char b, char c) {
		if(num == 1) {
			System.out.println(a + "->" + c);
		} else {
			//如果有多个盘，可以看做“最下面的”和“上面所有”
			
			//先移动a上层的盘到中间b柱，借助c
			move(num - 1, a , c, b);
			
			//把最下面的盘，移动到c
			System.out.println(a + "->" + c);
			
			//把b塔所有的盘，移动到c，借助a
			move(num - 1, b, a, c);

		}	
	}
}
