package priv.wzb.design_pattern.behavioralpattern.mediatorpattern.sale;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-19 21:09
 * @description:
 **/

public class Mediator extends AbstractMediator {
	public Mediator(Purchase purchase, Sale sale, Stock stock) {
		super(purchase, sale, stock);
	}

	@Override
	public void execute(String str, Object... objects) {
		if (str.equals("purchase.buy")){
			this.buyComputer((Integer) objects[0]);
		}else if (str.equals("sale.sell")){
			this.sellComputer((Integer) objects[0]);
		}else if (str.equals("sale.offSell")){
			this.offSell();
		}else if (str.equals("stock.clear")){
			this.clearStock();
		}
	}

	public void buyComputer(int number){
		int saleStatus = super.sale.getSaleStatus();
		if (saleStatus > 80){
			// 销售情况好
			System.out.println("purchase number = " + number);
			super.stock.increase(saleStatus);
		}else {
			int buyNumber = saleStatus/2;
			// 折半
			super.stock.increase(buyNumber);
			System.out.println("buyNumber = " + buyNumber);
		}
	}
	private void sellComputer(int number){
		if (super.stock.getStockNumber()<number){
			super.purchase.buyIBMcomputer(number);
		}
		super.stock.decrease(number);
	}
	private void offSell(){
		System.out.println("offSell super.stock.getStockNumber() = " + super.stock.getStockNumber());
	}
	private void clearStock(){
		super.sale.offSale();
		super.purchase.refuseBuyIBM();
	}
}
