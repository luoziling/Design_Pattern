package priv.wzb.design_pattern.createdpattern.singletonpattern.sxt;

/**
 * @author Satsuki
 * @time 2019/8/18 16:22
 * @description:
 */
public class SingleTon {

    //静态方法只能调用静态变量
    //无法被外围访问同时只有一个
    private static SingleTon singleTon;

    //私有化构造方法使得其他类无法实例化
    private SingleTon() {
    }

    //对外提供访问入口
    public static SingleTon getInstance(){
        if(singleTon==null){
            //多线程可以同时出现if成立，添加锁
            synchronized (SingleTon.class){

                //双重验证
                if(singleTon==null){
                    singleTon = new SingleTon();
                }

            }

        }
        return singleTon;
    }
}
