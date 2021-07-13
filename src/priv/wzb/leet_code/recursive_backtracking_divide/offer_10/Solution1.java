package priv.wzb.leet_code.recursive_backtracking_divide.offer_10;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-19 21:42
 * @description:
 **/

public class Solution1 {
	int f0 = 0,f1 = 1,sum = 0;
	public int fib(int n) {
		if (n == 0){
			return 0;
		}
		if (n == 1){
			return f1;
		}
		// 因为一共计算n-1次然而实际上要从第3次开始算，因此多算了一次
		for (int i = 0; i < n; i++) {
			sum = (f0+f1) % 1000000007;
			f0 = f1;
			f1 = sum;
		}
		return f0;
	}
}
