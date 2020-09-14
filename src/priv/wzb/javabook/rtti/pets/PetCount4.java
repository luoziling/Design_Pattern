package priv.wzb.javabook.rtti.pets;

import priv.wzb.javabook.rtti.TypeCounter;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-10 18:45
 * @description:
 **/

public class PetCount4 {
    public static void main(String[] args) {
        TypeCounter counter = new TypeCounter(Pet.class);
        Pets.stream()
                .limit(20)
                .peek(counter::count)
                .forEach(pet -> System.out.println(
                        pet.getClass().getSimpleName() +" "
                ));
        System.out.println("n" + counter);
    }
}
