package priv.wzb.javabase.annotation;

/**
 * @author Satsuki
 * @time 2019/9/12 14:50
 * @description:
 */
@WzbTable("tb_student")
public class WzbStudent {

    @WzbField(columnName = "id",type = "int",length = 10)
    private int id;
    @WzbField(columnName = "studentName",type = "varchar",length = 10)
    private String studentName;
    @WzbField(columnName = "age",type = "int",length = 3)
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent() {
        return studentName;
    }

    public void setStudent(String student) {
        this.studentName = student;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
