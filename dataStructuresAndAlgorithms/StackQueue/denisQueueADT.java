package dataStructuresAndAlgorithms.StackQueue;

public interface denisQueueADT<T> {
    void offer(T element);
    T poll();
    T peek();
    boolean isEmpty();
    int size();
}
