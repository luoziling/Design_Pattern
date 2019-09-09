package priv.wzb.design_pattern.behavioralpattern.visitorpattern.refactoring;

/**
 * @author Satsuki
 * @time 2019/6/9 19:47
 * @description:
 */
public class ParttimeEmployee implements Employee {
    private String name;
    private double hourWage;
    private int workTime;

    public ParttimeEmployee(String name,double hourWage,int workTime)
    {
        this.name = name;
        this.hourWage = hourWage;
        this.workTime = workTime;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setHourWage(double hourWage)
    {
        this.hourWage = hourWage;
    }

    public void setWorkTime(int workTime)
    {
        this.workTime = workTime;
    }

    public String getName()
    {
        return (this.name);
    }

    public double getHourWage()
    {
        return (this.hourWage);
    }

    public int getWorkTime()
    {
        return (this.workTime);
    }

    public void accept(Department handler)
    {
        handler.visit(this); //调用访问者的访问方法
    }
}
