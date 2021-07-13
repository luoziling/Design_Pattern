package priv.wzb.design_pattern.lsp;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2021-01-12 15:46
 * @description:
 **/
public interface Father {
	default HashMap doSth(Map<Integer,String> map){
		System.out.println("333");
		return null;
	}
}
