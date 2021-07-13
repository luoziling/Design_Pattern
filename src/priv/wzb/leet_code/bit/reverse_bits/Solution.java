package priv.wzb.leet_code.bit.reverse_bits;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-03-29 20:48
 * @description:
 **/

public class Solution {
	public int reverseBits(int n) {
		int rev = 0;
		for (int i = 0; i < 32 && n != 0; i++) {
			// 通过&1取出最后一位，然后座椅31-i次反转到起始位置
			rev |= (n & 1) << (31-i);
			// n循环右移
			n >>>= 1;
		}
		return rev;
	}
}
