import java.util.Random;

public class MergeSort {
    
    static int[] arr;

    static void sort(int l, int r) {
        if(l == r) {
            return;
        }
        int mid = (l + r) / 2;
        // sort(l, mid);
        // sort(mid + 1, r);
        Thread left_thread = new Thread(() -> {
            sort(l, mid);
        });
        Thread right_thread = new Thread(() -> {
            sort(mid + 1, r);
        });
        left_thread.start();
        right_thread.start();
        try {
            left_thread.join();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        try {
            right_thread.join();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        int[] res = new int[r - l + 1];
        int idx1 = l, idx2 = mid + 1;
        int idx = 0;
        while(idx1 <= mid && idx2 <= r) {
            if(arr[idx1] < arr[idx2]) {
                res[idx++] = arr[idx1++];
            }
            else {
                res[idx++] = arr[idx2++];
            }
        }
        while(idx1 <= mid) {
            res[idx++] = arr[idx1++];
        }
        while(idx2 <= r) {
            res[idx++] = arr[idx2++];
        }
        for(int i = l; i <= r; i++) {
            arr[i] = res[i - l];
        }
    }

    public static void main(String args[]) {
        int n = 10;
        arr = new int[n];
        for(int i = 0; i < n; i++) {
            arr[i] = (new Random()).nextInt(100);
        }
        for(int i = 0; i < n; i++) {
            System.out.print(arr[i]);
            System.out.print(" ");
        }
        System.out.println();
        sort(0, n - 1);
        for(int i = 0; i < n; i++) {
            System.out.print(arr[i]);
            System.out.print(" ");
        }
    }
}
