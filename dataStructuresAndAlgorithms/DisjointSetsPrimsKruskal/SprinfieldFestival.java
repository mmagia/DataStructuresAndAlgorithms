//The author is Denis Nurmuhametov CSE-06
package dataStructuresAndAlgorithms.DisjointSetsPrimsKruskal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class SprinfieldFestival {
    public static void main(String[] args) {
        DenisNurmuhametovGraph<String, Double> myGraph = new DenisNurmuhametovGraph<>();
        Scanner sc = new Scanner(System.in);
        DenisNurmuhametovMap<String, DenisNurmuhametovVertex<String, Double>> directAccessVerticesMap = new DenisNurmuhametovMap<>();

        int N = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < N; i++) {
            String[] command = sc.nextLine().split(" ");
            if (command[0].equals("ADD")) {
                double tax = Double.parseDouble(command[2]);
                DenisNurmuhametovVertex<String, Double> newVert = new DenisNurmuhametovVertex<>(command[1], tax);
                directAccessVerticesMap.put(command[1], newVert);
                myGraph.insertVertex(newVert);
            }
            if (command[0].equals("CONNECT")) {
                DenisNurmuhametovVertex<String, Double> from = directAccessVerticesMap.get(command[1]);
                DenisNurmuhametovVertex<String, Double> to = directAccessVerticesMap.get(command[2]);
                double distance = Double.parseDouble(command[3]);
                double cost = distance / (from.tax + to.tax);
                myGraph.insertEdge(new DenisNurmuhametovEdge<>(from, to, cost));
                myGraph.insertEdge(new DenisNurmuhametovEdge<>(to, from, cost));
            }
            if (command[0].equals("PRINT_MIN")) {
                myGraph.DenisNurmuhametov_mst_prim();
            }
        }
    }
}


class DenisNurmuhametovBinaryHeap<V, E> {
    public ArrayList<DenisNurmuhametovVertex<V, E>> buffer;
    int amountOfChildren;

    DenisNurmuhametovBinaryHeap() {
        this.buffer = new ArrayList<>();
        this.amountOfChildren = 2;
    }

    void buildHeap() {
        for (int i = 0; i < buffer.size(); i++) {
            buffer.get(i).heapIndex = i;
        }
        for (int i = (buffer.size() / 2) - 1; i >= 0; i--) {
            minHeapify(i);
        }
    }

    private void minHeapify(int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int smallest = index;

        if (left < buffer.size() && buffer.get(left).weight < buffer.get(smallest).weight) {
            smallest = left;
        }
        if (right < buffer.size() && buffer.get(right).weight < buffer.get(smallest).weight) {
            smallest = right;
        }
        if (smallest != index) {
            swap(index, smallest);
            minHeapify(smallest);
        }
    }

    private void swap(int i, int j) {
        DenisNurmuhametovVertex<V, E> temp = buffer.get(i);
        buffer.set(i, buffer.get(j));
        buffer.set(j, temp);
        buffer.get(i).heapIndex = i;
        buffer.get(j).heapIndex = j;
    }

    DenisNurmuhametovVertex<V, E> extractMinVertex() {
        if (buffer.isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        DenisNurmuhametovVertex<V, E> minVertex = buffer.get(0);
        buffer.set(0, buffer.get(buffer.size() - 1));
        buffer.get(0).heapIndex = 0;
        buffer.remove(buffer.size() - 1);
        if (!buffer.isEmpty()) {
            minHeapify(0);
        }
        minVertex.heapIndex = -1;
        return minVertex;
    }

    public void decreaseKey(DenisNurmuhametovVertex<V, E> vertex, double newWeight) {
        int i = vertex.heapIndex;
        if (i == -1 || i >= buffer.size()) return;
        if (newWeight >= buffer.get(i).weight) return;
        buffer.get(i).weight = newWeight;
        fixViolations(i);
    }

    private void fixViolations(int index) {
        while (index > 0) {
            int parent = (index - 1) / amountOfChildren;
            if (buffer.get(index).weight < buffer.get(parent).weight) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    public boolean inQueue(DenisNurmuhametovVertex<V, E> vertex) {
        int idx = vertex.heapIndex;
        return idx >= 0 && idx < buffer.size() && buffer.get(idx).equals(vertex);
    }
}


class DenisNurmuhametovMap<K, V> implements DenisNurmuhametovMapADT<K, V> {
    ArrayList<DenisNurmuhametovPair<K, V>>[] denisTable;
    static final int DEFAULT_INITIAL_CAPACITY = 977;
    int tableSize;

    public DenisNurmuhametovMap() {
        this.tableSize = 0;
        this.denisTable = new ArrayList[DEFAULT_INITIAL_CAPACITY];
        for (int i = 0; i < DEFAULT_INITIAL_CAPACITY; i++) {
            denisTable[i] = new ArrayList<>();
        }
    }

    private int hashCode(K key) {
        return Math.abs(key.hashCode()) % DEFAULT_INITIAL_CAPACITY;
    }

    public void set(K key, V newValue) {
        int currentHash = hashCode(key);
        for (DenisNurmuhametovPair<K, V> pair : denisTable[currentHash]) {
            if (pair.key.equals(key)) {
                pair.value = newValue;
            }
        }
    }

    @Override
    public V get(K key) {
        int currentHash = hashCode(key);
        for (DenisNurmuhametovPair<K, V> pair : denisTable[currentHash]) {
            if (pair.key.equals(key)) {
                return pair.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.tableSize;
    }

    @Override
    public void put(K key, V value) {
        int currentHash = Math.abs(hashCode(key)) % DEFAULT_INITIAL_CAPACITY;
        this.denisTable[currentHash].add(new DenisNurmuhametovPair<K, V>(key, value));
        this.tableSize++;
    }

    @Override
    public boolean isEmpty() {
        return this.tableSize == 0;
    }

    public void printMap() {
        for (int i = 0; i < tableSize; i++) {
            for (DenisNurmuhametovPair<K, V> pair : denisTable[i]) {
                System.out.println(pair.key + " " + pair.value);
            }
        }
    }
}

interface DenisNurmuhametovMapADT<K, V> {
    V get(K key);

    int size();

    void put(K key, V value);

    boolean isEmpty();
}

class DenisNurmuhametovPair<K, V> {
    K key;
    V value;

    public DenisNurmuhametovPair(K key, V value) {
        this.value = value;
        this.key = key;
    }

    public DenisNurmuhametovPair() {
        this.value = null;
        this.key = null;
    }
}

class DenisNurmuhametovVertex<V, E> {
    public V label;
    public int index;
    public int heapIndex = -1;
    private static int incrementCounter = 1;
    public double weight;
    public double tax;
    public DenisNurmuhametovVertex<V, E> parent;
    public LinkedList<DenisNurmuhametovEdge<V, E>> incidentEdges;
    public LinkedList<DenisNurmuhametovVertex<V, E>> adjacentVertices;

    public DenisNurmuhametovVertex(V label, double tax) {
        this.label = label;
        this.tax = tax;
        this.incidentEdges = new LinkedList<>();
        this.adjacentVertices = new LinkedList<>();
        this.weight = Double.MAX_VALUE;
        this.parent = null;
        this.index = incrementCounter;
        incrementCounter++;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}


class DenisNurmuhametovEdge<V, E> {
    public DenisNurmuhametovVertex<V, E> from, to;
    public E weight;

    public DenisNurmuhametovEdge(DenisNurmuhametovVertex<V, E> from, DenisNurmuhametovVertex<V, E> to, E weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

class DenisNurmuhametovGraph<V, E> {
    LinkedList<DenisNurmuhametovVertex<V, E>> vertices;
    LinkedList<DenisNurmuhametovEdge<V, E>> edges;

    public DenisNurmuhametovGraph() {
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
    }

    public void insertVertex(DenisNurmuhametovVertex<V, E> vertex) {
        vertices.addLast(vertex);
    }

    public void insertEdge(DenisNurmuhametovEdge<V, E> newEdge) {
        edges.addLast(newEdge);
        newEdge.from.adjacentVertices.add(newEdge.to);
        newEdge.from.incidentEdges.add(newEdge);
    }

    public void DenisNurmuhametov_mst_prim() {
        for (DenisNurmuhametovVertex<V, E> vertex : vertices) {
            vertex.weight = Double.MAX_VALUE;
            vertex.parent = null;
        }
        if (!vertices.isEmpty()) {
            vertices.get(0).weight = 0;
        }

        DenisNurmuhametovBinaryHeap<V, E> priorityQueue = new DenisNurmuhametovBinaryHeap<>();
        for (DenisNurmuhametovVertex<V, E> vertex : vertices) {
            vertex.heapIndex = priorityQueue.buffer.size();
            priorityQueue.buffer.add(vertex);
        }
        priorityQueue.buildHeap();

        LinkedList<DenisNurmuhametovEdge<V, E>> mstEdges = new LinkedList<>();

        while (!priorityQueue.isEmpty()) {
            DenisNurmuhametovVertex<V, E> currentVert = priorityQueue.extractMinVertex();
            if (currentVert.parent != null) {
                DenisNurmuhametovEdge<V, E> mstEdge = new DenisNurmuhametovEdge<>(currentVert.parent, currentVert, (E) (Double) currentVert.weight);
                mstEdges.add(mstEdge);
            }

            for (DenisNurmuhametovEdge<V, E> edge : currentVert.incidentEdges) {
                DenisNurmuhametovVertex<V, E> adjacent = edge.to;
                double cost = (Double) edge.weight;
                if (priorityQueue.inQueue(adjacent) && cost < adjacent.weight) {
                    adjacent.parent = currentVert;
                    priorityQueue.decreaseKey(adjacent, cost);
                }
            }
        }

        for (DenisNurmuhametovEdge<V, E> edge : mstEdges) {
            System.out.print(edge.from.label + ":" + edge.to.label + " ");
        }
        System.out.println();
    }

    public void printGraph() {
        for (DenisNurmuhametovEdge<V, E> edge : edges) {
            System.out.println(edge.from.index + " " + edge.to.index + " " + edge.weight);
        }
    }
}


/*
References:
1) I took my Graph data structure from Coding Exercise Week 12
2) Prims's Algorithm Idea was taken from Lecture 11
3) I took my Min Heap implementation from Coding Exercise Week 8
4) I took my Hash Map implementation from Coding Exercise Week 3
5) I took the code from the previous Exercise and modified it
 */