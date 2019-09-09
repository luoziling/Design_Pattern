package priv.wzb.effective_java.chapter2.pizza;

/**
 * @author Satsuki
 * @time 2019/6/25 15:22
 * @description:
 */
public class Calzone extends Pizza {
    private final boolean sauceInside;
    public static class Builder extends Pizza.Builder<Builder>{
        private boolean sauceInside = false;//Default
        public Builder sauceInside(){
            sauceInside = true;
            return this;
        }
        @Override
        Pizza build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private Calzone(Builder builder){
        super(builder);
        sauceInside = builder.sauceInside;
    }
}
