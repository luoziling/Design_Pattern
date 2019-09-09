package priv.wzb.javabase.treemap;

/**
 * @author Satsuki
 * @time 2019/9/4 13:44
 * @description:
 */
public class Employee implements Comparable<Employee> {
    int id;
    String name;
    double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public int compareTo(Employee o) {
        if(this.salary>o.salary){
            return 1;
        }else if(this.salary<o.salary){
            return -1;
        }else{
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
