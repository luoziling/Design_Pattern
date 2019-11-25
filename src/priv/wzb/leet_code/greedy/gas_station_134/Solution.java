package priv.wzb.leet_code.greedy.gas_station_134;

/**
 * @author Satsuki
 * @time 2019/11/16 23:28
 * @description:
 */
public class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;

        // 环行过程中邮箱里剩下的油
        int totalTank = 0;
        // 记录当前油箱里剩余的总油量
        int currTank = 0;
        int startStation = 0;
        for (int i = 0; i < n; i++) {
            totalTank += gas[i]-cost[i];
            // 在运行过程中当前邮箱内的油量在不停变化
            currTank += gas[i] - cost[i];
            // 如果无法到达
            if (currTank<0){
                /// 选下一个加油站开始
                startStation = i+1;
                // 邮箱置空
                currTank = 0;
            }
        }
        // total的作用其实就是一个计数总和的作用
        // 如果总共可以获得的油不足以支撑整个循环就返回-1
        return totalTank>0?startStation:-1;

    }
}
