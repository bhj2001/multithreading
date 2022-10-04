import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Semaphore {
    int n;

    Semaphore(int n) {
        this.n = n;
    }

    public synchronized void acquire() throws InterruptedException {
        while(n == 0) {
            wait();
        }
        n--;
    }

    public synchronized void release() {
        n++;
        notifyAll();
    }

    public synchronized void release(int ad) {
        n += ad;
        notifyAll();
    }
}

public class uber {

    static int waiting_rep = 0, waiting_dem = 0;

    static Semaphore mutex = new Semaphore(1);

    static Semaphore rep_queue = new Semaphore(0);
    static Semaphore dem_queue = new Semaphore(0);

    static int cab_count = 0;
    static Semaphore mutex2 = new Semaphore(1);

    static void seated(String name) throws InterruptedException{
        Thread.sleep(Math.abs(new Random().nextInt()) % 1000);
        System.out.println("Seated " + name);
    }
    static void drive(String name) throws InterruptedException {
        Thread.sleep(Math.abs(new Random().nextInt()) % 1000);
        System.out.println("Drive " + name);
    }

    static Semaphore last_cab_left = new Semaphore(1);

    static void democrat(int id) throws InterruptedException {
        mutex.acquire();
        waiting_dem++;
        if(waiting_dem >= 4) {
            last_cab_left.acquire();
            waiting_dem -= 4;
            dem_queue.release(4);
        }
        if(waiting_dem >=2 && waiting_rep >= 2) {
            last_cab_left.acquire();
            waiting_dem -= 2;
            waiting_rep -= 2;
            dem_queue.release(2);
            rep_queue.release(2);
        }
        mutex.release();

        dem_queue.acquire();
        seated("Dem " + Integer.toString(id));

        mutex2.acquire();
        cab_count++;
        if(cab_count == 4) {
            drive("Dem " + Integer.toString(id));
            last_cab_left.release(1);
            cab_count = 0;
        }
        mutex2.release();
    }

    static void republician(int id) throws InterruptedException {
        mutex.acquire();
        waiting_rep++;
        if(waiting_rep >= 4) {
            last_cab_left.acquire();
            waiting_rep -= 4;
            rep_queue.release(4);
        }
        if(waiting_dem >=2 && waiting_rep >= 2) {
            last_cab_left.acquire();
            waiting_dem -= 2;
            waiting_rep -= 2;
            dem_queue.release(2);
            rep_queue.release(2);
        }
        mutex.release();

        rep_queue.acquire();
        seated("Rep " + Integer.toString(id));

        mutex2.acquire();
        cab_count++;
        if(cab_count == 4) {
            drive("Rep " + Integer.toString(id));
            last_cab_left.release(1);
            cab_count = 0;
        }
        mutex2.release();
    }
    
    public static void main(String[] args) {
        Semaphore[] arr = new Semaphore[10];
        System.out.println(arr[0]);
        return;
        // ArrayList<Thread> v = new ArrayList<Thread>();
        // int r = 10, d = 10;
        // for(int i = 0; i < r; i++) {
        //     final int id = i;
        //     Thread t = new Thread(() -> {
        //         try {
        //             republician(id);
        //         }
        //         catch (Exception e) {
        //             e.printStackTrace();
        //         }
        //     });
        //     v.add(t);
        // }
        // for(int i = 0; i < d; i++) {
        //     final int id = i;
        //     Thread t = new Thread(() -> {
        //         try {
        //             democrat(id);
        //         }
        //         catch (Exception e) {
        //             e.printStackTrace();
        //         }
        //     });
        //     v.add(t);
        // }
        // Collections.shuffle(v);
        // for(Thread t : v) {
        //     t.start();
        // }
        // for(Thread t : v) {
        //     try {
        //         t.join();
        //     }
        //     catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // }
    }
}
