package priv.wzb.interview.nowcoder;

import java.util.Scanner;

/**
 * @author Satsuki
 * @time 2019/10/26 23:34
 * @description:
 */

public class CombinateCoins {
    int[] tempMost = null;
    int count;

    public CombinateCoins(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("");
        }
        if (n > 10) {
            tempMost = new int[n + 1];
            count = n + 1;
        } else {
            tempMost = new int[11];
            count = 11;
        }
        tempMost[0] = 0;
        tempMost[1] = 1;
        tempMost[2] = 2;
        tempMost[3] = 2;
        tempMost[4] = 3;
        tempMost[5] = 4;
        tempMost[6] = 5;
        tempMost[7] = 6;
        tempMost[8] = 7;
        tempMost[9] = 8;
        tempMost[10] = 11;

    }

    //蛮力法
    void combinateCoinsByBtute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入钱数");
        int n = scanner.nextInt();
        int sum = 0;
        int count = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= (n / 2); j++) {
                for (int k = 0; k <= (n / 5); k++) {
                    for (int l = 0; l <= (n / 10); l++) {
                        if ((i + j * 2 + k * 5 + l * 10) == n) {
                            sum++;
                        }
                    }
                }
            }
        }
        System.out.println(sum);
    }

    //动态规划
    int combinateCoinsByDp(int n) {
        CombinateCoins combinateCoins = new CombinateCoins(n);
        if (n <= 10) {
            return combinateCoins.tempMost[n];
        }

        for (int i = 11; i <= n; i++) {
            int most = 0;
            for (int j = 1; j <= i / 2; j++) {
                int tempCount = tempMost[j] + tempMost[i - j];
                if (most < tempCount) {
                    most = tempCount;
                }
                tempMost[i] = most;
            }
        }
        return tempMost[n];
    }

    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入钱数");
            int n = scanner.nextInt();
            CombinateCoins combinateCoins = new CombinateCoins(n);
            System.out.println(combinateCoins.combinateCoinsByDp(n));
        }
    }
}
