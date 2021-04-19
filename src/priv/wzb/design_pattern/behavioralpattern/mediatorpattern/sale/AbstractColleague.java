package priv.wzb.design_pattern.behavioralpattern.mediatorpattern.sale;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-04-19 09:41
 * @description:
 **/

public abstract class AbstractColleague {
	protected AbstractMediator mediator;

	public AbstractColleague(AbstractMediator mediator) {
		this.mediator = mediator;
	}
}
