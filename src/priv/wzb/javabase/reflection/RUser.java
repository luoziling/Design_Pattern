package priv.wzb.javabase.reflection;

/**
 * @author Satsuki
 * @time 2019/9/12 15:18
 * @description:
 */
public class RUser {
    private int id;
    private int age;
    private String uname;

    public RUser() {
    }

    public RUser(int id, int age, String uname) {
        this.id = id;
        this.age = age;
        this.uname = uname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Override
    public String toString() {
        return "RUser{" +
                "id=" + id +
                ", age=" + age +
                ", uname='" + uname + '\'' +
                '}';
    }
}
