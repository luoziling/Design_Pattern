package priv.wzb.bankTest;

/**
 * @author Satsuki
 * @time 2019/5/18 15:44
 * @description:
 */

import java.util.Scanner;

public class BankerClass {

    int[] Available;//初始资源数(可分配资源数
    int[][] Max ;//进程最大需求资源数
    int[][] Alloction ;//已分配Allocation
    int[][] Need ;//进程还需资源数=Max-Allocation
    int[][] Request;
    int[] Work ;//工作向量
    boolean[] Finish ;//进程是否完成
    int processNum;
    int resourceNum ;
    int availableNum;
    int num = 0;// 进程编号
    Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        // TODO code application logic here
        boolean Choose = true;
        String C;
        Scanner in = new Scanner(System.in);
        BankerClass T = new BankerClass();
        System.out.println("Hello!");


        T.setSystemVariable();
        while (Choose == true) {
            T.setRequest();
            System.out.println("您是否还要进行请求：y/n?");
            C = in.nextLine();
            if (C.endsWith("n")) {
                Choose = false;
                System.out.println("程序结束！");
            }
        }
    }

    public BankerClass() {
        // Max={{6,3,2},{5,6,1},{2,3,2}};

    }

    public void setSystemVariable() {// 设置各初始系统变量，并判断是否处于安全状态。
        init();
        setMax();
        setAlloction();
        printSystemVariable();
        SecurityAlgorithm();
    }

    private void init() {//初始化变量
        // TODO Auto-generated method stub
        System.out.println("请设置进程数量：");
        processNum = in.nextInt();

        System.out.println("请设置资源数量：");
        resourceNum = in.nextInt();
        Max = new int[processNum][resourceNum];
        Alloction = new int[processNum][resourceNum];
        Need = new int[processNum][resourceNum];
        Request = new int[processNum][resourceNum];
        Work = new int[resourceNum];
        Available=new int[resourceNum];
        Finish=new boolean[processNum];

        System.out.println("请设置初始资源数:");
        for (int i = 0; i < resourceNum; i++) {
            Available[i]=in.nextInt();
        }

    }

    public void setMax() {// 设置Max矩阵
        System.out.println("请设置各进程的最大需求矩阵Max：");
        for (int i = 0; i < processNum; i++) {
            System.out.println("请输入进程P" + i + "的最大资源需求量：");
            for (int j = 0; j < resourceNum; j++) {
                Max[i][j] = in.nextInt();
            }
        }
    }

    public void setAlloction() {// 设置已分配矩阵Alloction
        System.out.println("请设置请各进程分配矩阵Alloction：");
        for (int i = 0; i < processNum; i++) {
            System.out.println("请输入进程P" + i + "的分配资源量：");
            for (int j = 0; j < resourceNum; j++) {
                Alloction[i][j] = in.nextInt();
            }
        }
        System.out.println("Available=Available-Alloction.");
        System.out.println("Need=Max-Alloction.");
        for (int i = 0; i < resourceNum; i++) {// 设置Available矩阵
            for (int j = 0; j < processNum; j++) {
                Available[i] = Available[i] - Alloction[j][i];
            }
        }

        for (int i = 0; i < processNum; i++) {// 设置Need矩阵
            for (int j = 0; j < resourceNum; j++) {
                Need[i][j] = Max[i][j] - Alloction[i][j];
            }
        }
    }

    public void printSystemVariable() {
        System.out.println("此时资源分配量如下：");
        System.out.println("进程  " + "   Max   " + "   Alloction "
                + "    Need  " + "     Available ");
        for (int i = 0; i < processNum; i++) {
            System.out.print("P" + i + "  ");
            for (int j = 0; j < resourceNum; j++) {
                System.out.print(Max[i][j] + "  ");
            }
            System.out.print("|  ");
            for (int j = 0; j < resourceNum; j++) {
                System.out.print(Alloction[i][j] + "  ");
            }
            System.out.print("|  ");
            for (int j = 0; j < resourceNum; j++) {
                System.out.print(Need[i][j] + "  ");
            }
            System.out.print("|  ");
            if (i == 0) {
                for (int j = 0; j < resourceNum; j++) {
                    System.out.print(Available[j] + "  ");
                }
            }
            System.out.println();
        }
    }

    public void setRequest() {// 设置请求资源量Request

        System.out.println("请输入请求资源的进程编号：");
        num = in.nextInt();// 设置全局变量进程编号num
        System.out.println("请输入请求各资源的数量：");
        for (int j = 0; j < resourceNum; j++) {
            Request[num][j] = in.nextInt();
        }
        //System.out.println("即进程P" + num + "对各资源请求Request：(" + Request[num][0]
        //		+ "," + Request[num][1] + "," + Request[num][2] + ").");

        BankerAlgorithm();
    }

    public void BankerAlgorithm() {
        System.out.println("银行家算法");// 银行家算法
        boolean T = true;
        boolean ReqNeed=true;
        boolean ReqAva=true;
        for (int i = 0; i < resourceNum; i++) {
            if (!(Request[num][i]<=Need[num][i] )) {
                ReqNeed=false;
            }
        }
        for (int i = 0; i < resourceNum; i++) {
            if (!(Request[num][i]<=Available[i])) {
                ReqAva=false;
            }

        }

        if (ReqNeed) {// 判断Request是否小于Need
            if (ReqAva) {// 判断Request是否小于Available
                for (int i = 0; i < resourceNum; i++) {
                    Available[i] -= Request[num][i];
                    Alloction[num][i] += Request[num][i];
                    Need[num][i] -= Request[num][i];
                }

            } else {
                System.out.println("当前没有足够的资源可分配，进程P" + num + "需等待。");
                T = false;
            }
        } else {
            System.out.println("进程P" + num + "请求已经超出最大需求量Need.");
            T = false;
        }

        if (T == true) {
            printSystemVariable();
            System.out.println("现在进入安全算法：");
            boolean flag = SecurityAlgorithm();
            if (!flag) {
                System.out.println("因为不安全，所以退回资源！");
                for (int i = 0; i < resourceNum; i++) {
                    Available[i] += Request[num][i];
                    Alloction[num][i] -= Request[num][i];
                    Need[num][i] += Request[num][i];
                }
            }
        }
    }

    public boolean SecurityAlgorithm() {// 安全算法
        System.out.println("安全算法");
        int count = 0;// 完成进程数
        int circle = 0;// 循环圈数
        int[] S = new int[processNum];// 安全序列
        for (int i = 0; i < resourceNum; i++) {// 设置工作向量
            Work[i] = Available[i];
        }
        for (int i = 0; i < processNum; i++) {//设置finish初始值
            Finish[i]=false;
        }
        boolean flag = true;
        boolean NeedWork=true;
        while (count < processNum) {
            if (flag) {
                System.out.println("进程  " + "   Work  " + "   Alloction "
                        + "    Need  " + "     Work+Alloction ");
                flag = false;
            }
            for (int i = 0; i < processNum; i++) {
                NeedWork=true;
                for (int j = 0; j < resourceNum; j++) {
                    if(!(Finish[i] == false && Need[i][j] <= Work[j])){
                        NeedWork=false;
                    }
                }
                if (NeedWork) {// 判断条件
                    System.out.print("P" + i + "  ");
                    for (int k = 0; k < resourceNum; k++) {
                        System.out.print(Work[k] + "  ");
                    }
                    System.out.print("|  ");
                    for (int j = 0; j < resourceNum; j++) {
                        Work[j] += Alloction[i][j];
                    }
                    Finish[i] = true;// 当当前进程能满足时
                    S[count] = i;// 设置当前序列排号

                    count++;// 满足进程数加1
                    for (int j = 0; j < resourceNum; j++) {
                        System.out.print(Alloction[i][j] + "  ");
                    }
                    System.out.print("|  ");
                    for (int j = 0; j < resourceNum; j++) {
                        System.out.print(Need[i][j] + "  ");
                    }
                    System.out.print("|  ");
                    for (int j = 0; j < resourceNum; j++) {
                        System.out.print(Work[j] + "  ");
                    }
                    System.out.println();
                }

            }
            circle++;// 循环圈数加1
            System.out.println("count:" + count);
            if (count == processNum) {// 判断是否满足所有进程需要
                System.out.print("此时存在一个安全序列：");
                for (int i = 0; i < processNum; i++) {// 输出安全序列
                    System.out.print("P" + S[i] + " ");
                }

                System.out.println("情况C：所申请的资源大于其所需资源，亦大于系统此时可利用资源，预分配并进行安全性检查！");
                System.out.println("故当前可分配！");
                return true;
            }
            if(count == 0){
                System.out.println("情况A：所申请的资源大于其所需资源，提示分配不合理不与分配并返回。");
                System.out.println("当前系统处于不安全状态，故不存在安全序列。");
                return false;
            }
            if (count < processNum) {// 判断完成进程数是否小于循环圈数
//				count = processNum+1;
                System.out.println("情况B：所申请的资源未其所需资源，但大于系统此时的可利用资源，提示分配不合理不与分配并返回。");
                System.out.println("当前系统处于不安全状态，故不存在安全序列。");
                return false;
            }
        }

        return false;
    }

}

