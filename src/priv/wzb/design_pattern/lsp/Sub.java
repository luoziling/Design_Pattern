package priv.wzb.design_pattern.lsp;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2021-01-12 15:47
 * @description:
 **/

public class Sub implements Father {
//	@Override
//	public void doSth(Double wqaeq) {
//	}

//	@Override

	@Override
	public HashMap doSth(Map<Integer, String> map) {
		System.out.println("111");
		return null;
	}

	public Map doSth(HashMap<Integer, String> map) {
		System.out.println("222");
		return null;
	}

	public static void main(String[] args) {
		Father f = new Sub();
		Map<Integer,String> map = new HashMap<>(1);
		f.doSth(map);
	}
}
