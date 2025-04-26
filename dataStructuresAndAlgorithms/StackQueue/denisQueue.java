package dataStructuresAndAlgorithms.StackQueue;

public class denisQueue<T> implements denisQueueADT<T> {
    denisNode<T> head;
    denisNode<T> tail;
    int size;

    public denisQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    public void listQueue() {
        int index = 0;
        denisNode<T> currentInsance = this.head;
        for (int i = 0; i < this.size; i++) {
            System.out.print(currentInsance.data + " ");
            currentInsance = currentInsance.next;
        }

    }

    public String[] fromQueueToStringArray() {
        denisNode<T> currentInsance = this.head;
        String[] array = new String[this.size];
        for (int i = 0; i < this.size; i++) {
            array[i] = (String) currentInsance.data;
            currentInsance = currentInsance.next;
        }
        return array;
    }


    @Override
    public void offer(T element) {
        if (head == null) {
            head = new denisNode<>(element, null);
            tail = head;
        } else {
            tail.next = new denisNode<>(element, null);
            tail = tail.next;
        }
        this.size++;
    }

    @Override
    public T poll() {
        T item = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        this.size--;
        return item;
    }

    @Override
    public T peek() {
        return this.head.data;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }
}
