package priv.wzb.design_pattern.createdpattern.builderpattern.chapter2.pizza;

import java.util.Objects;

/**
 * @author Satsuki
 * @time 2019/6/25 15:16
 * @description:
 */
public class NyPizza extends Pizza {
    public enum Size{
        SMALL, MEDIUM, LARGE
    }

    private final Size size;

    public static class Builder extends Pizza.Builder<Builder>{
        private final Size size;
        public Builder(Size size){
            this.size = Objects.requireNonNull(size);
        }
        @Override
        Pizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }
}
