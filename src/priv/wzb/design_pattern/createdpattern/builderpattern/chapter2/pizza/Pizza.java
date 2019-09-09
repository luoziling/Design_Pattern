package priv.wzb.design_pattern.createdpattern.builderpattern.chapter2.pizza;



import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Satsuki
 * @time 2019/6/25 15:03
 * @description:
 */
public abstract class Pizza {
    public enum Topping{
        HAM, MUSHROOM, ONION, PEPPER, SAUSAGE
    }
    final Set<Topping> toppings;
    abstract static class Builder<T extends Builder<T>>{
        EnumSet<Topping> toppings =
                EnumSet.noneOf(Topping.class);
        public T addTopping(Topping topping){
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }
        abstract Pizza build();
        //Subclasses must override this method to return "this"
        protected abstract T self();
    }
    Pizza(Builder<?> builder){
        toppings = builder.toppings.clone();
    }
}
