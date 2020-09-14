package priv.wzb.javabook.rtti.pets;

import java.util.LinkedHashMap;


/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-10 18:29
 * @description:
 **/

public class PerCount3 {
    static class Counter extends
            LinkedHashMap<Class<? extends Pet>,Integer>{
        Counter(){
//            super(LiteralPetCreator.ALL_TYPES.stream()
//            .map(lpc-> Pair.make));
        }
    }
}
