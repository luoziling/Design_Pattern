package priv.wzb.javabook.patterns.state;

import priv.wzb.javabook.concurrent.Nap;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-24 11:36
 * @description:
 * 状态机 模板方法模式
 **/
interface State{
	void run();
}
abstract class StateMachine{
	protected State currentState;
	protected abstract boolean changeState();

	protected final void runAll(){
		while (changeState()){
			currentState.run();
		}
	}
}

/**
 * A different subclass for each state
 */
class Wash implements State{
	@Override
	public void run() {
		System.out.println("Washing");
		new Nap(0.5);
	}
}

class Spin implements State {
	@Override
	public void run() {
		System.out.println("Spinning");
		new Nap(0.5);
	}
}

class Rinse implements State {
	@Override
	public void run() {
		System.out.println("Rinsing");
		new Nap(0.5);
	}
}

class Washer extends StateMachine{
	private int i = 0;
	// The state table;
	private State[] states = {
			new Wash(),new Spin(),
			new Rinse(),new Spin()
	};

	Washer() {
		runAll();
	}

	@Override
	protected boolean changeState() {
		if (i<states.length){
			// change the state by setting the
			// surrogate reference to a new object
			currentState = states[i++];
			return true;
		}else {
			return false;
		}
	}
}
public class StateMachineDemo {
	public static void main(String[] args) {
		new Washer();
	}
}
