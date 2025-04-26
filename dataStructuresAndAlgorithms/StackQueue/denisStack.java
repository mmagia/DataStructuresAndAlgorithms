package dataStructuresAndAlgorithms.StackQueue;

import java.util.EmptyStackException;

public class denisStack <T> implements denisStackADT<T> {
    private T[] buffer;
    private int size = 0;

    private static final int DEFAUT_CAPACITY = 16;
    private static final int CAPACITY_MULT = 2;

    public denisStack() {
        buffer = (T[]) new Object[DEFAUT_CAPACITY];
    }

    public denisStack(int capacity) {
        buffer = (T[]) new Object[capacity];
    }

    public void listStack() {
        for (int i = 0; i < size; i++) {
            System.out.print(buffer[i] + " ");
        }
    }

    private void enlargeBuffer() {
        T[] tmpArray = (T[]) new Object[buffer.length * CAPACITY_MULT];
        for (int i = 0; i < size; i++) {
            tmpArray[i] = buffer[i];
        }
        buffer = tmpArray;
    }



    @Override
    public void push(T element) {
        if (size == buffer.length) {
            enlargeBuffer();
        }
        buffer[size] = element;
        size++;
    }

    @Override
    public T pop() throws EmptyStackException {
        if (size == 0) {
            throw new EmptyStackException();
        }
        size--;
        return buffer[size];
    }

    @Override
    public T peek() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return buffer[size - 1];
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
