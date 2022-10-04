class IntConsumer {
    public void accept(int x) {
        System.out.println(x);
    }
}

public class test {
    public static void main(String args[]) {
        Runnable x = () -> {
            System.out.println("Bhagya");
        };
        x.run();
        (new IntConsumer()).accept(699);
    }
}
