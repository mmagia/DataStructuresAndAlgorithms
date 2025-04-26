package dataStructuresAndAlgorithms.StackQueue;

public class denisNode<T> {
    T data;
    denisNode<T> next;
    public denisNode(T data) {
        this.data = data;
        this.next = null;
    }

    public denisNode(T data, denisNode<T> next) {
        this.data = data;
        this.next = next;
    }
}
