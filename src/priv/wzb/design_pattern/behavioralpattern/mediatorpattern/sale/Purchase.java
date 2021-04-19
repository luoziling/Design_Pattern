package priv.wzb.design_pattern.behavioralpattern.mediatorpattern.sale;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-19 09:43
 * @description:
 **/

public class Purchase extends AbstractColleague {
	// 产生循环引用
	public Purchase(AbstractMediator mediator) {
		super(mediator);
	}

	public void buyIBMcomputer(int number){
		super.mediator.execute("purchase.buy",number);
	}

	public void refuseBuyIBM(){
		System.out.println("不采购IBM电脑");
	}

}
