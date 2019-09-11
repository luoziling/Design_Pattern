package priv.wzb.javabase.server.servlet;

/**
 * @author Satsuki
 * @time 2019/9/10 20:46
 * @description:
 */
public class Entity {
    private String name;
    private String clz;

    public Entity() {
    }

    public Entity(String name, String clz) {
        this.name = name;
        this.clz = clz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", clz='" + clz + '\'' +
                '}';
    }
}
