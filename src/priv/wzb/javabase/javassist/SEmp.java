package priv.wzb.javabase.javassist;

/**
 * @author Satsuki
 * @time 2019/9/12 17:22
 * @description:
 */
public class SEmp {
    private int empno;
    private String ename;

    public SEmp() {
    }

    public SEmp(int empno, String ename) {
        this.empno = empno;
        this.ename = ename;
    }

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    @Override
    public String toString() {
        return "SEmp{" +
                "empno=" + empno +
                ", ename='" + ename + '\'' +
                '}';
    }
}
