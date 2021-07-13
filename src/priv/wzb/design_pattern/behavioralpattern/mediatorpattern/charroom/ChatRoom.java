package priv.wzb.design_pattern.behavioralpattern.mediatorpattern.charroom;

import java.util.Date;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-19 09:29
 * @description: 中介者模式-聊天室
 **/

public class ChatRoom {
	public static void showMessage(User user,String message){
		System.out.println(new Date().toString()
				+ " [" + user.getName() +"] : " + message);
	}
}
