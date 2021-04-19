package priv.wzb.design_pattern.behavioralpattern.mediatorpattern.sale;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-19 09:41
 * @description:
 **/

public abstract class AbstractMediator {
	protected Purchase purchase;
	protected Sale sale;
	protected Stock stock;

	public AbstractMediator(Purchase purchase, Sale sale, Stock stock) {
		this.purchase = purchase;
		this.sale = sale;
		this.stock = stock;
	}

	/**
	 * 中介者模式最重要的中间执行 解耦 函数
	 * @param str
	 * @param objects
	 */
	public abstract void execute(String str,Object... objects);
}
