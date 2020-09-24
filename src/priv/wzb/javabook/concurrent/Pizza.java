package priv.wzb.javabook.concurrent;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-23 17:21
 * @description:
 **/

public class Pizza {
	public enum Step{
		DOUGH(4),ROLLED(1),SAUCED(1),CHEESED(2),
		TOPPED(5),BAKED(2),SLICED(1),BOXED(0);
		int effort;

		Step(int effort) {
			this.effort = effort;
		}

		Step forward(){
			if (equals(BOXED)) return BOXED;
			new Nap(effort * 0.1);
			return values()[ordinal() + 1];
		}
	}

	private Step step = Step.DOUGH;
	private final int id;

	public Pizza(int id) {
		this.id = id;
	}

	public Pizza next(){
		step = step.forward();
		System.out.println("Pizza " + id + ": " + step);
		return this;
	}

	public Pizza next(Step previousStep){
		if (!step.equals(previousStep)){
			throw new IllegalStateException("Excepted" +
					previousStep + " but found " + step);
		}
		return next();
	}
	public Pizza roll(){
		return next(Step.DOUGH);
	}

	public Pizza sauce(){
		return next(Step.ROLLED);
	}

	public Pizza cheese(){
		return next(Step.SAUCED);
	}

	public Pizza toppings(){
		return next(Step.CHEESED);
	}

	public Pizza bake(){
		return next(Step.TOPPED);
	}

	public Pizza slice(){
		return next(Step.BAKED);
	}

	public Pizza box(){
		return next(Step.SLICED);
	}

	public boolean complete(){
		return step.equals(Step.BOXED);
	}
	@Override
	public String toString(){
		return "Pizza" + id + ": " + (step.equals(Step.BOXED) ? "complete" : step);
	}

}
