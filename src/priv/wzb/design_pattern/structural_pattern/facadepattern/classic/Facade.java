package priv.wzb.design_pattern.structural_pattern.facadepattern.classic;

/**
 * @author Satsuki
 * @time 2019/4/6 15:47
 * @description:
 */
public class Facade {
    private SubSystemA obj1 = new SubSystemA();
    private SubSystemB obj2 = new SubSystemB();
    private SubSystemC obj3 = new SubSystemC();

    public void Method()
    {
        obj1.MethodA();
        obj2.MethodB();
        obj3.MethodC();
    }
}
