package priv.wzb.design_pattern.behavioralpattern.mediatorpattern.sale;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-19 20:59
 * @description:
 **/

public class Stock extends AbstractColleague {
	public Stock(AbstractMediator mediator) {
		super(mediator);
	}
	private static int COMPUTER_NUMBER = 100;
	public void increase(int number){
		COMPUTER_NUMBER += number;
		System.out.println("increase COMPUTER_NUMBER = " + COMPUTER_NUMBER);
	}
	public void decrease(int number){
		COMPUTER_NUMBER -= number;
		System.out.println("decrease COMPUTER_NUMBER = " + COMPUTER_NUMBER);
	}
	public int getStockNumber(){
		return COMPUTER_NUMBER;
	}

	/**
	 * 库存压力大 清库存
	 */
	public void clearStock(){
		System.out.println("clear COMPUTER_NUMBER = " + COMPUTER_NUMBER);
		super.mediator.execute("stock.clear");
	}
}
