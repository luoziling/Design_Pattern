package priv.wzb.leet_code.recursive_backtracking_divide.offer_10;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-19 21:42
 * @description:
 **/

public class Solution {
	int f0 = 0,f1 = 1;
	public int fib(int n) {
		if (n == 0){
			return 0;
		}
		if (n == 1){
			return f1;
		}
		return fib(n-1) + fib(n-2);
	}
}
