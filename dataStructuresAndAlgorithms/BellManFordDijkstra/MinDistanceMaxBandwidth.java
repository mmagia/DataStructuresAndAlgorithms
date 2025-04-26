package dataStructuresAndAlgorithms.BellManFordDijkstra;
// The author is Denis Nurmuhametov

import java.util.ArrayList;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class MinDistanceMaxBandwidth {
    public static void main(String[] args) {
        DenisNurmuhametovGraph<Long> myGraph = new DenisNurmuhametovGraph<>();
        DenisNurmuhametovMap<Long, DenisNurmuhametovVertex<Long>> accessVertices = new DenisNurmuhametovMap<>();

        Scanner sc = new Scanner(System.in);
        int numberOfVertices = sc.nextInt();
        int numberOfEdges = sc.nextInt();

        for (int i = 0; i < numberOfEdges; i++) {
            long source = sc.nextLong();
            long destination = sc.nextLong();
            long weight = sc.nextLong();
            long bandwidth = sc.nextLong();
            DenisNurmuhametovVertex<Long> from;
            if (accessVertices.get(source) == null) {
                from = new DenisNurmuhametovVertex<>(null, source);
                accessVertices.put(source, from);
                myGraph.insertVertex(from);
            } else {
                from = accessVertices.get(source);
            }

            DenisNurmuhametovVertex<Long> to;
            if (accessVertices.get(destination) == null) {
                to = new DenisNurmuhametovVertex<>(null, destination);
                accessVertices.put(destination, to);
                myGraph.insertVertex(to);
            } else {
                to = accessVertices.get(destination);
            }

            myGraph.insertEdge(new DenisNurmuhametovEdge<>(from, to, weight, bandwidth));
            myGraph.insertEdge(new DenisNurmuhametovEdge<>(to, from, weight, bandwidth));
        }

        long source = sc.nextLong();
        long sink = sc.nextLong();

        myGraph.DenisNurmuhametov_dijkstra(accessVertices.get(source), accessVertices.get(sink));
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

class DenisNurmuhametovVertex<T> {
    public T label;
    public long index;
    public long weight;
    public long bandwidth;
    public DenisNurmuhametovVertex<T> parent;
    public LinkedList<DenisNurmuhametovEdge<T>> incidentEdges;
    public LinkedList<DenisNurmuhametovVertex<T>> adjacentVertices;

    public DenisNurmuhametovVertex(T label, long index) {
        this.label = label;
        this.incidentEdges = new LinkedList<>();
        this.adjacentVertices = new LinkedList<>();
        this.weight = Long.MAX_VALUE;
        this.parent = null;
        this.bandwidth = 0L;
        this.index = index;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public void setBandwidth(long bandwidth) {
        this.bandwidth = bandwidth;
    }
}

class DenisNurmuhametovEdge<T> {
    public DenisNurmuhametovVertex from, to;
    public T weight;
    public T bandwidth;

    public DenisNurmuhametovEdge(DenisNurmuhametovVertex<T> from, DenisNurmuhametovVertex<T> to, T weight, T bandwidth) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.bandwidth = bandwidth;
    }
}

class DenisNurmuhametovGraph<T> {
    LinkedList<DenisNurmuhametovVertex<T>> vertices;
    LinkedList<DenisNurmuhametovEdge<T>> edges;
    ArrayList<Long> verticesInCycle;

    public DenisNurmuhametovGraph() {
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
        verticesInCycle = new ArrayList<>();
    }

    public void insertVertex(DenisNurmuhametovVertex<T> vertex) {
        vertices.addLast(vertex);
    }

    public void insertEdge(DenisNurmuhametovEdge<T> newEdge) {
        edges.addLast(newEdge);
        newEdge.from.adjacentVertices.add(newEdge.to);
        newEdge.from.incidentEdges.add(newEdge);
    }

    public void DenisNurmuhametov_dijkstra(DenisNurmuhametovVertex<T> source, DenisNurmuhametovVertex<T> sink) {
        PriorityQueue<DenisNurmuhametovVertex<T>> pq = new PriorityQueue<>(
                (firstVert, secondVert) -> {
                    if (firstVert.weight != secondVert.weight) {
                        return Long.compare(firstVert.weight, secondVert.weight);
                    }
                    return Long.compare(secondVert.bandwidth, firstVert.bandwidth);
                }
        );

        source.weight = 0L;
        source.bandwidth = Long.MAX_VALUE;
        pq.offer(source);

        while (!pq.isEmpty()) {
            DenisNurmuhametovVertex<T> currentVert = pq.poll();

            if (currentVert.equals(sink)) break;

            for (DenisNurmuhametovEdge<T> edge : currentVert.incidentEdges) {
                DenisNurmuhametovVertex<T> v = edge.to;
                long weight = (Long) edge.weight;
                long edgeBandwidth = (Long) edge.bandwidth;

                if (currentVert.weight + weight < v.weight ||
                        (currentVert.weight + weight == v.weight && Math.min(currentVert.bandwidth, edgeBandwidth) > v.bandwidth)) {
                    v.weight = currentVert.weight + weight;
                    v.bandwidth = Math.min(currentVert.bandwidth, edgeBandwidth);
                    pq.offer(v);
                }
            }
        }

        if (sink.weight == Long.MAX_VALUE) {
            System.out.println("-1 -1");
        } else {
            System.out.println(sink.weight + " " + sink.bandwidth);
        }
    }

    public void printGraph() {
        for (DenisNurmuhametovEdge<T> edge : edges) {
            System.out.println(edge.from.index + " " + edge.to.index + " " + edge.weight);
        }
    }
}


/*
1) I took my code from the exercise A and modified it
2) I took my graph data structure from Week 10 Coding Exercise and modified it
3) I took the idea for dijkstra algorithm from CLRS page 642 and modified it
4) I took my HashMap from Coding Exercise Week 3
 */