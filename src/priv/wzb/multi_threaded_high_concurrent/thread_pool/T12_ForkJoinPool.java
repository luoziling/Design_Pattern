package priv.wzb.multi_threaded_high_concurrent.thread_pool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @author Satsuki
 * @time 2019/6/8 17:30
 * @description:
 */
public class T12_ForkJoinPool {
    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000;
    static Random r = new Random();

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
        System.out.println(Arrays.stream(nums).sum());
    }

    static class AddTask extends RecursiveAction{
        int start,end;
        AddTask(int s,int e){
            this.start=s;
            this.end=e;
        }
        @Override
        protected void compute() {
            if(end-start<=MAX_NUM){
                long sum=0L;
                for (int i = start; i < end; i++) {
                    sum+=nums[i];
                }
                System.out.println("from:" + start + "to:" + end + "=" + sum);
            }else {
                int middle = start + (end-start)/2;
                AddTask subTask1 = new AddTask(start,middle);
                AddTask subTask2 = new AddTask(middle,end);
                subTask1.fork();
                subTask2.fork();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ForkJoinPool fjp = new ForkJoinPool();
        AddTask task = new AddTask(0,nums.length);
        fjp.execute(task);
        System.in.read();
    }
}
