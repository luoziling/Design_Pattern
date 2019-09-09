package priv.wzb.design_pattern.createdpattern.builderpattern.chapter2;

/**
 * @author Satsuki
 * @time 2019/6/25 14:57
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8).calories(100).sodium(35).carbohydrate(27).build();
    }
}
