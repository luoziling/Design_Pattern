package priv.wzb.javabook.fp;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-12 16:33
 **/

public class Strategize {
    Strategy strategy;
    String msg;

    public Strategize(String msg) {
        // 在 Strategize 中，你可以看到 Soft 作为默认策略，在构造函数中赋值。
        strategy = new Soft(); // [1]
        this.msg = msg;
    }

    void communicate() {
        System.out.println(strategy.approach(msg));
    }

    void changeStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public static void main(String[] args) {
        Strategy[] strategies = {
                new Strategy() { // [2]
                    // 一种较为简洁且更加自然的方法是创建一个匿名内部类。
                    // 即便如此，仍有相当数量的冗余代码。
                    // 你总需要仔细观察后才会发现：“哦，我明白了，原来这里使用了匿名内部类。”
                    @Override
                    public String approach(String msg) {
                        return msg.toUpperCase() + "!";
                    }
                },
                // Java 8 的 Lambda 表达式，其参数和函数体被箭头 -> 分隔开。
                // 箭头右侧是从 Lambda 返回的表达式。
                // 它与单独定义类和采用匿名内部类是等价的，但代码少得多。
                msg -> msg.substring(0, 5), // [3]
                // Java 8 的方法引用，它以 :: 为特征。
                // :: 的左边是类或对象的名称， :: 的右边是方法的名称，但是没有参数列表。
                // 这里甚至将非子类的方法传过去了
                Unrelated::twice // [4]
        };
        Strategize s = new Strategize("Hello there");
        s.communicate();
        for (Strategy newStrategy : strategies) {
            s.changeStrategy(newStrategy); // [5]
            s.communicate(); // [6]
        }
    }
}
