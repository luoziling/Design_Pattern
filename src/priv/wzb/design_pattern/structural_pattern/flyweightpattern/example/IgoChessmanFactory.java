package priv.wzb.design_pattern.structural_pattern.flyweightpattern.example;

import java.util.Hashtable;

/**
 * @author Satsuki
 * @time 2019/6/18 17:27
 * @description:
 */
public class IgoChessmanFactory {
    private static IgoChessmanFactory instance = new IgoChessmanFactory();
    private static Hashtable ht;

    public IgoChessmanFactory() {
        ht = new Hashtable();
        IgoChessman black,white;
        black = new BlackIgoChessman();
        ht.put("b",black);
        white = new WhiteIgoChessman();
        ht.put("w",white);
    }

    //返回唯一实例
    public static IgoChessmanFactory getInstance(){
        return instance;
    }

    //通过key获取存储在Hashtable中的享元对象
    public static IgoChessman getIgoChessman(String color){
        return (IgoChessman) ht.get(color);
    }
}
