package priv.wzb.javabook.enums;

// enums/SecurityCategory.java
// More succinct subcategorization of enums

import java.util.Random;

enum SecurityCategory {
    STOCK(Security.Stock.class),
    BOND(Security.Bond.class);
    Security[] values;
    SecurityCategory(Class<? extends Security> kind) {
        values = kind.getEnumConstants();
    }
    interface Security {
        enum Stock implements Security {
            SHORT, LONG, MARGIN
        }
        enum Bond implements Security {
            MUNICIPAL, JUNK
        }
    }
    public Security randomSelection() {
        return values[new Random().nextInt(values.length)];
    }
    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
//            SecurityCategory category =
//                    Enums.random(SecurityCategory.class);
//            SecurityCategory category =
//                    Security[new Random().nextInt(Security.class)];
//            System.out.println(category + ": " +
//                    category.randomSelection());
        }
    }
}

