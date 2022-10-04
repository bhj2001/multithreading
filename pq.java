// Node for implementing linked list

class Node {
    public int data;
    public Node nxt;

    Node(int data, Node nxt) {
        this.data = data;
        this.nxt = nxt;
    }
}

// Blocked Queue
class BlockingQueue {

    private int capacity;
    private int size;
    private Node head;
    private Node tail;

    BlockingQueue(int cap) {
        this.capacity = cap;
        this.size = 0;
    }

    synchronized void enqueue(int x) throws InterruptedException {
        while(size == capacity) {
            wait();
        }
        Node tmp = new Node(x, null);
        this.tail.nxt = tmp;
        if(size == 0) {
            this.head = tmp;
        }
        size++;
        notifyAll();
    }

    synchronized int dequeue() throws InterruptedException {
        while(size == 0) {
            wait();
        }
        int ret = this.head.data;
        this.head = this.head.nxt;
        size--;
        if(size == 0) {
            this.head = this.tail = null;
        }
        notifyAll();
        return ret;
    }
}

public class pq {
    
    public static void main(String args[]) {
    }
}
