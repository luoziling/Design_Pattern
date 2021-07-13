package priv.wzb.javabook.annotations;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-16 20:03
 * @description:
 **/

public class UseCaseTracker {
    public static<T> void trackUseCases(List<Integer> useCases,Class<T> cl){
        for (Method m : cl.getDeclaredMethods()) {
            UseCase uc = m.getAnnotation(UseCase.class);
            if (uc != null){
                System.out.println("Found Use Case " +
                        uc.id() + "\n " + uc.description());
                useCases.remove(Integer.valueOf(uc.id()));
            }
        }
        useCases.forEach(i-> System.out.println("Missing use case " + i));
    }

    public static void main(String[] args) {
        List<Integer> useCases = IntStream.range(47,51)
                .boxed().collect(Collectors.toList());
        trackUseCases(useCases,PasswordUtils.class);
    }
}
