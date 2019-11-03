package priv.wzb.javabase.reflection;

/**
 * @author Satsuki
 * @time 2019/9/12 15:17
 * @description:
 * 测试java.lang.Class对象的获取方式
 * 各种类型(class,interface,enum,annotation,primitive type,void等)的获取方式
 */
@SuppressWarnings("all")
public class Demo1 {
    public static void main(String[] args) {
        String path = "priv.wzb.javabase.reflection.RUser";
        try {
            Class clazz = Class.forName(path);
            //对象表示一些数据
            System.out.println(clazz.hashCode());

            Class clazz2 = Class.forName(path);
            //一个类被加载后JVM回创建一个对应该类的Class对象
            //类的整个结构信息会放到Class对象中。
            //这个Class对象就像一面镜子一样，
            // 通过这面镜子我可以看到对应类的全部信息
            System.out.println(clazz2.hashCode());

            Class<String> strClass = String.class;
            Class aClass = path.getClass();
            System.out.println(strClass==aClass);

            //Every array also
            // * belongs to a class that is reflected as a {@code Class} object
            // * that is shared by all arrays with the same element type and number
            // * of dimensions.
            int[] arr01 = new int[10];
            int[] arr02 = new int[30];
            System.out.println(arr01.getClass().hashCode()==arr02.getClass().hashCode());
            int[][] arr03 = new int[10][10];
            //primitive数据类型和数组对象也只会获得一个但是数组对象按照维度的不同会得到不同的对象
            System.out.println(arr01.getClass().hashCode()==arr03.getClass().hashCode());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
