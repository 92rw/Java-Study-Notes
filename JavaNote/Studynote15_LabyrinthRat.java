/*
老鼠出迷宫，迷宫形状如下
◼◼◼◼◼◼◼
◼◉◻◻◻◻◼
◼◻◻◻◻◻◼
◼◼◼◻◻◻◼
◼◻◻◻◻◻◼
◼◻◻◻◻◻◼
◼◻◻◻◻◻◼
◼◼◼◼◼◼◼

思路
1,先创建迷宫，用二维数组表示int[][] map = new int[8][7];
2.先规定map数组的元素值：0表示可以走，1表示障碍物
3.

*/

public class Studynote15_LabyrinthRat {
	public static void main(String[] args){
		int[][] map = new int[8][7];
		//将最上面一行和最下面一行，全部设置为1
		for(int i = 0; i < 7; i++){
			map[0][i] = 1;
			map[7][i] = 1;
		}

		//将最左面一列和最下面一列，全部设置为1
		for(int i = 0; i < 8; i++){
			map[i][0] = 1;
			map[i][6] = 1;
		}

		//无法用函数设置的点位
		map[3][1] = 1;
		map[3][2] = 1;


		//输出当前的迷宫
		System.out.println("======当前地图情况=====");
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}

		//使用findWay1给老鼠找路
		SolveMethod Rat = new SolveMethod();
		//使用findWay1，起始点设置为(1,1)
		//Rat.findWay1(map,1,1);
		//使用findWay2，起始点设置为(1,1)
		Rat.findWay2(map,1,1);
		
		
		System.out.println("\n====找路的情况如下====");
		
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}
}


class SolveMethod{
	//使用递归回溯的思想解决老鼠出迷宫

	//使用findWay1方法，找到返回true，否则返回false
	//i,j表示老鼠位置初始位置(1,1)，到达(6,5)算作离开迷宫
	//规定map数组各个值的含义：0表示没探测过，1表示障碍物，2表示可以走，3表示不能走
	//当map[6][5] =2就说明找到通路，程序可以结束，否则就继续找

	//找路策略1：下->右->上->左
	public boolean findWay1(int[][] map, int i, int j) {

		if(map[6][5] == 2){//说明找到通路
			return true;
		} else {
			if(map[i][j] == 0){//说明没探测过
				//假定可以走通
				map[i][j] =2;
				//使用找路策略，判断该位置是否真得可以走通
				if(findWay1(map, i + 1, j)){//下
					return true;
				} else if(findWay1(map, i, j + 1)){//右
					return true;
				} else if(findWay1(map, i - 1, j)){//上
					return true;
				} else if(findWay1(map, i, j - 1)){//左
					return true;
				} else{ //说明“可以走通”的假定是错的
					map[i][j] = 3;
					return false;
				}
			} else{ //对应map[i][j]不是0的情况
				return false;
			}
		}
	}
	
	//找路策略2：上->右->下->左
	public boolean findWay2(int[][] map, int i, int j) {

		if(map[6][5] == 2){//说明找到通路
			return true;
		} else {
			if(map[i][j] == 0){//说明没探测过
				//假定可以走通
				map[i][j] =2;
				//使用找路策略，判断该位置是否真得可以走通
				if(findWay2(map, i - 1, j)){//上
					return true;
				} else if(findWay2(map, i, j + 1)){//右
					return true;
				} else if(findWay2(map, i + 1, j)){//下
					return true;
				} else if(findWay2(map, i, j - 1)){//左
					return true;
				} else{ //说明“可以走通”的假定是错的
					map[i][j] = 3;
					return false;
				}
			} else{ //对应map[i][j]不是0的情况
				return false;
			}
		}
	}
}

//扩展思考：如何求出最短路径->1）穷举	2)图
