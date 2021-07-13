package priv.wzb.javabook.patterns.abstractfactory;

import java.util.function.Supplier;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-24 15:18
 * @description:
 **/
interface Obstacle{
	void action();
}
interface Player{
	void interactWith(Obstacle o);
}
class Kitty implements Player{
	@Override
	public void interactWith(Obstacle ob) {
		System.out.print("Kitty has encountered a ");
		ob.action();
	}
}
class KungFuGuy implements Player {
	@Override
	public void interactWith(Obstacle ob) {
		System.out.print("KungFuGuy now battles a ");
		ob.action();
	}
}
class Puzzle implements Obstacle {
	@Override
	public void action() {
		System.out.println("Puzzle");
	}
}

class NastyWeapon implements Obstacle {
	@Override
	public void action() {
		System.out.println("NastyWeapon");
	}
}

/**
 * The Abstract Factory:
 */
class GameElementFactory{
	Supplier<Player> player;
	Supplier<Obstacle> obstacle;
}

/**
 * Concrete factories:
 */
class KittiesAndPuzzles extends GameElementFactory{
	public KittiesAndPuzzles() {
		player = Kitty::new;
		obstacle = Puzzle::new;
	}
}
class KillAndDismember extends GameElementFactory {
	KillAndDismember() {
		player = KungFuGuy::new;
		obstacle = NastyWeapon::new;
	}
}

public class GameEnvironment {
	private Player p;
	private Obstacle ob;

	public GameEnvironment(GameElementFactory factory) {
		p = factory.player.get();
		ob = factory.obstacle.get();
	}
	public void play(){
		p.interactWith(ob);
	}

	public static void main(String[] args) {
		GameElementFactory kp = new KittiesAndPuzzles(), kd = new KillAndDismember();
		GameEnvironment g1 = new GameEnvironment(kp), g2 = new GameEnvironment(kd);
		g1.play();
		g2.play();
	}
}
