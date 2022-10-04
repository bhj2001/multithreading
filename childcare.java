// At a child care center, state regulations require that there is always
// one adult present for every three children.
import java.util.*;

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

public class childcare {

    static Semaphore mutex;
    
    static int waiting_children = 0;
    static int waiting_adults = 0;

    static Semaphore allow_child;
    static Semaphore allow_alult;

    public static void child(int id) throws InterruptedException {
        mutex.acquire();
        waiting_children++;
        System.out.println(Integer.toString(waiting_children) + " " + Integer.toString(waiting_adults));
        if(waiting_children >= 3 && waiting_adults >= 1) {
            waiting_adults -= 1;
            waiting_children -= 3;
            allow_alult.release(1);
            allow_child.release(3);
        }
        mutex.release();

        allow_child.acquire();
        System.out.println("Child " + Integer.toString(id) + " enters");
    }

    public static void adult(int id) throws InterruptedException {
        mutex.acquire();
        waiting_adults++;
        if(waiting_children >= 3 && waiting_adults >= 1) {
            waiting_adults -= 1;
            waiting_children -= 3;
            allow_alult.release(1);
            allow_child.release(3);
        }
        mutex.release();

        allow_alult.acquire();
        System.out.println("Adult " + Integer.toString(id) + " enters");
    }

    public static void main(String args[]) {
        mutex = new Semaphore(1);
        allow_alult = new Semaphore(0);
        allow_child = new Semaphore(0);
        int n = 30, m = 10;
        List<Thread> v = new ArrayList<Thread>();
        for(int i = 0; i < n; i++) {
            final int id = i;
            Thread t = new Thread(() -> {
                try {
                    child(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            v.add(t);
        }
        for(int i = 0; i < m; i++) {
            final int id = i;
            Thread t = new Thread(() -> {
                try {
                    adult(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            v.add(t);
        }
        for(int i = 0; i < v.size(); i++) {
            v.get(i).start();
        }
        for(int i = 0; i < v.size(); i++) {
            try {
                v.get(i).join();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
