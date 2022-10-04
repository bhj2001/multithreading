class sem  {
    private int cnt;

    sem() {
        this.cnt = 0;
    }

    public synchronized void acquire() throws InterruptedException {
        while(this.cnt != 0) {
            wait();
        }
        this.cnt++;
    }

    public synchronized void release() {
        this.cnt = 0;
        notifyAll();
    }
}

public class deadlock {
    
    static int data1, data2;

    static sem lock1, lock2;

    public static void func1() throws InterruptedException {
        lock1.acquire();
        System.out.println("Sem 1 acquired by func1");
        Thread.sleep(1000);
        data1 = 1;
        lock2.acquire();
        System.out.println("Sem 2 acquired by func1");
        Thread.sleep(1000);
        data2 = 1;
        lock1.release();
        lock2.release();
    }

    
    public static void func2() throws InterruptedException {
        lock2.acquire();
        System.out.println("Sem 2 acquired by func2");
        Thread.sleep(1000);
        data2 = 2;
        lock1.acquire();
        System.out.println("Sem 1 acquired by func2");
        Thread.sleep(1000);
        data1 = 2;
        lock2.release();
        lock1.release();
    }

    public static void main(String args[]) {
        data1 = 0;
        data2 = 0;
        lock1 = new sem();
        lock2 = new sem();
        Thread t1 = new Thread(() -> {
            try {
                func1();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            try {
                func2();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        });
        t2.start();
        try {
            t1.join();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        try {
            t2.join();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
