package priv.wzb.multi_threaded_high_concurrent.thread_synchronization;

/**
 * @author Satsuki
 * @time 2019/9/6 17:34
 * @description:
 * 快乐影院
 */
public class HappyCinema {
    public static void main(String[] args) {
        Cinema c = new Cinema(20,"happy c");

        new Thread(new Customer(c,2),"a").start();
        new Thread(new Customer(c,1),"b").start();
    }
}


class Customer implements Runnable{

    Cinema cinema;
    int seats;

    public Customer(Cinema cinema, int seats) {
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
    int available;//可用位置
    String name;//名称

    public Cinema(int available, String name) {
        this.available = available;
        this.name = name;
    }

    //购票
    public boolean bookTickets(int seats){
        System.out.println("new available seats:" + available);
        if (seats>available){
            return false;
        }
        available-=seats;
        return true;
    }
}

