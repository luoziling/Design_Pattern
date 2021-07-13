package priv.wzb.design_pattern.behavioralpattern.mediatorpattern.charroom;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-19 09:30
 * @description:
 **/

public class User {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User(String name){
		this.name  = name;
	}

	public void sendMessage(String message){
		ChatRoom.showMessage(this,message);
	}
}
