package priv.wzb.javabook.rtti;

import java.util.Optional;

/**
 * @program: Design_Pattern
 * @author: yuzuki
 * @create: 2020-09-11 16:30
 * @description:
 **/
class EmptyTitleException extends RuntimeException{

}
public class Position {
    private String title;
    private Person person;
    Position(String jobTitle, Person employee) {
        setTitle(jobTitle);
        setPerson(employee);
    }

    Position(String jobTitle) {
        this(jobTitle, null);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        // Throws EmptyTitleException if newTitle is null:
        // 为空抛出自定义异常
        title = Optional.ofNullable(newTitle)
                .orElseThrow(EmptyTitleException::new);
    }
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person newPerson) {
        // Uses empty Person if newPerson is null:
        person = Optional.ofNullable(newPerson)
                .orElse(new Person());
    }
    @Override
    public String toString() {
        return "Position: " + title +
                ", Employee: " + person;
    }

    public static void main(String[] args) {
        System.out.println(new Position("CEO"));
        System.out.println(new Position("Programmer",
                new Person("Arthur", "Fonzarelli")));
        try {
            new Position(null);
        } catch (Exception e) {
            System.out.println("caught " + e);
        }
    }
}
