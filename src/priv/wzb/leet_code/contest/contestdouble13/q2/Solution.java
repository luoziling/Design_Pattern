package priv.wzb.leet_code.contest.contestdouble13.q2;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/11/16 22:55
 * @description:
 */
public class Solution {
    public String findSmallestRegion(List<List<String>> regions, String region1, String region2) {
        // 从后向前遍历，找到包含的就返回
//        String res = null;
        List<String> nowRegion;
        int n = regions.size();
        boolean f1 = false,f2= false;
        String r1,r2;
        r1 = null;
        r2 = null;
        for (int i = 0; i < n; i++) {
            // 置空
//            f1 = false;
//            f2 = false;
            // 从后向前
            nowRegion = regions.get(n-i-1);
            // 先找到
            for(String s : nowRegion){
                if (s.equals(region1)){
                    f1 = true;
                    r1 = nowRegion.get(0);
                }
                if (s.equals(region2)){
                    f2 = true;
                    r2 = nowRegion.get(0);
                }
            }
            if (f1&&f2){
                if (r1.equals(r2)){
                    return nowRegion.get(0);
                }else if (nowRegion.contains(r1)&&nowRegion.contains(r2)){
                    return nowRegion.get(0);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        List<List<String>> regions = new LinkedList<>();

        List<String> s1 = new LinkedList<>();
        s1.add("Earth");
        s1.add("North America");
        s1.add("South America");
        List<String> s2 = new LinkedList<>();
        s2.add("North America");
        s2.add("United States");
        s2.add("Canada");
        List<String> s3 = new LinkedList<>();
        s3.add("United States");
        s3.add("New York");
        s3.add("Boston");
        List<String> s4 = new LinkedList<>();
        s4.add("Canada");
        s4.add("Ontario");
        s4.add("Quebec");
        List<String> s5 = new LinkedList<>();
        s5.add("South America");
        s5.add("Brazil");

        regions.add(s1);
        regions.add(s2);
        regions.add(s3);
        regions.add(s4);
        regions.add(s5);
        System.out.println(new Solution().findSmallestRegion(regions,"Quebec","New York"));;
    }
}
