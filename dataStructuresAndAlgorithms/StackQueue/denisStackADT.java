package dataStructuresAndAlgorithms.StackQueue;

public interface denisStackADT<T> {
    void push(T element);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
}
