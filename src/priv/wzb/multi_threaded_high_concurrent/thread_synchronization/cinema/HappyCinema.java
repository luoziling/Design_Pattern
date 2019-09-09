package priv.wzb.multi_threaded_high_concurrent.thread_synchronization.cinema;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Satsuki
 * @time 2019/9/6 17:34
 * @description:
 * 快乐影院
 */
public class HappyCinema {
    public static void main(String[] args) {
        //可用位置
        List<Integer> available = new ArrayList<>();

        available.add(1);
        available.add(2);
        available.add(3);
        available.add(6);
        available.add(7);

        //顾客需要的位置
        List<Integer> seats1 = new ArrayList<>();
        seats1.add(1);
        seats1.add(2);

        List<Integer> seats2 = new ArrayList<>();
        seats2.add(4);
        seats2.add(5);
        seats2.add(6);


        Cinema c = new Cinema(available,"happy c");

        new Thread(new Customer(c,seats1),"a").start();
        new Thread(new Customer(c,seats2),"b").start();
    }
}


class Customer implements Runnable{

    Cinema cinema;
    List<Integer> seats;

    public Customer(Cinema cinema, List<Integer> seats) {
        this.cinema = cinema;
        this.seats = seats;
    }

    @Override
    public void run() {
        synchronized (cinema){
            boolean flag = cinema.bookTickets(seats);
            if(flag){
                System.out.println("出票成功" + Thread.currentThread().getName() + " 位置：" + seats);
            }else {
                System.out.println("出票失败位置不够");
            }
        }

    }
}

class Cinema{
    List<Integer> available;//可用位置
    String name;//名称

    public Cinema(List<Integer> available, String name) {
        this.available = available;
        this.name = name;
    }

    //购票
    public boolean bookTickets(List<Integer> seats){
        System.out.println("new available seats:" + available);
        List<Integer> copy = new ArrayList<>();
        copy.addAll(available);
        //相减
        copy.removeAll(seats);

        //判断大小
        if((available.size()-copy.size())!=seats.size()){
            return false;
        }

        available = copy;
        return true;
    }
}

