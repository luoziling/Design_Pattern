package priv.wzb.interview.aboutthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Satsuki
 * @time 2019/7/3 16:21
 * @description:
 */
public class Car implements Runnable {
    private int carNum;
    private CarCompetion competion = null;
    public Car(int carNum, CarCompetion competion) {
        this.carNum = carNum;
        this.competion = competion;
    }

    @Override
    public void run() {
        synchronized (competion){
            competion.nowCarNum++;
            while (competion.nowCarNum < competion.totalCarNum){
                try {
                    competion.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            competion.notifyAll();
        }
        startCar();
    }

    private void startCar() {
        System.out.println("Car num " + this.carNum + " start to run.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Car num " + this.carNum + " get to the finish line.");
    }

    public static void main(String[] args) {
        CarCompetion carCompetion = new CarCompetion();
        final ExecutorService carPool =
                Executors.newFixedThreadPool(carCompetion.totalCarNum);
        for (int i = 0; i < carCompetion.totalCarNum; i++) {
            carPool.execute(new Car(i,carCompetion));
        }
    }
}
