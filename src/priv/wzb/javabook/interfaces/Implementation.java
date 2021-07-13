package priv.wzb.javabook.interfaces;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-27 15:21
 **/

/**
 * Class 'Implementation' must either be declared abstract
 * or implement abstract method 'idea1()' in 'Concept'
 */
public class Implementation implements Concept {
    /**
     * 'idea1()' in 'priv.wzb.javabook.interfaces.Implementation' clashes with 'idea1()'
     * in 'priv.wzb.javabook.interfaces.Concept';
     * attempting to assign weaker access privileges ('package-private'); was 'public'
     */
    @Override
    public void idea1() {

    }

    @Override
    public void idea2() {

    }
}
