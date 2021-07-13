package priv.wzb.design_pattern.behavioralpattern.mediatorpattern.sale;

import java.util.Random;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-19 21:04
 * @description:
 **/

public class Sale extends AbstractColleague {
	public Sale(AbstractMediator mediator) {
		super(mediator);
	}
	// sell ibm
	public void sellIBMComputer(int number){
		super.mediator.execute("sale.sell",number);
		System.out.println("sale IBM number = " + number);
	}

	/**
	 * 反馈销售情况
	 */
	public int getSaleStatus(){
		Random rand = new Random(System.currentTimeMillis());
		int saleStatus = rand.nextInt(100);
		System.out.println("saleStatus = " + saleStatus);
		return saleStatus;
	}

	public void offSale(){
		super.mediator.execute("sale.offSell");
	}
}
