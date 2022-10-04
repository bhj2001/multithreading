
class IntConsumer {
    public void accept(int x) {
        System.out.println(x);
    }
}


public class FizzBuzz {
    private int n;
    private int cnt;

    public FizzBuzz(int n) {
        this.n = n;
        this.cnt = 1;
    }

    // printFizz.run() outputs "fizz".
    public synchronized void fizz(Runnable printFizz) throws InterruptedException {
        while(cnt <= n) {
            if(cnt % 3 == 0 && cnt % 5 != 0) {
                printFizz.run();
                cnt++;
                notifyAll();
            }
            else {
                wait();
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public synchronized void buzz(Runnable printBuzz) throws InterruptedException {
        while(cnt <= n) {
            if(cnt % 3 != 0 && cnt % 5 == 0) {
                printBuzz.run();
                cnt++;
                notifyAll();
            }
            else {
                wait();
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public synchronized void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while(cnt <= n) {
            if(cnt % 3 == 0 && cnt % 5 == 0) {
                printFizzBuzz.run();
                cnt++;
                notifyAll();
            }
            else {
                wait();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public synchronized void number(IntConsumer printNumber) throws InterruptedException {
        while(cnt <= n) {
            if(cnt % 3 != 0 && cnt % 5 != 0) {
                printNumber.accept(cnt);
                cnt++;
                notifyAll();
            }
            else {
                wait();
            }
        }
    }
    
    public static void main(String args[]) {
        System.err.println(23);
        FizzBuzz fb = new FizzBuzz(69);
        Thread a = new Thread(() -> {
            try {
                fb.fizz(() -> {
                    System.out.println("fizz");
                });
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        });
        Thread b = new Thread(() -> {
            try {
                fb.buzz(() -> {
                    System.out.println("buzz");
                });
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        });
        Thread c = new Thread(() -> {
            try {
                fb.fizzbuzz(() -> {
                    System.out.println("fizzbuzz");
                });
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        });
        Thread d = new Thread(() -> {
            try {
                fb.number(new IntConsumer());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        });
        a.start();
        b.start();
        c.start();
        d.start();
        try{
            a.join();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        try{
            b.join();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        try{
            c.join();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        try{
            d.join();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}

