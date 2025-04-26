package dataStructuresAndAlgorithms.BellManFordDijkstra;// The author is Denis Nurmuhametov CSE-06

import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.EmptyStackException;


public class BellmanFordRevenge {
    public static void main(String[] args) {
        DenisNurmuhametovGraph<Integer> myGraph = new DenisNurmuhametovGraph<>();
        Scanner sc = new Scanner(System.in);

        int N = Integer.parseInt(sc.nextLine());
        DenisNurmuhametovVertex<Integer>[] directAccessVertices = new DenisNurmuhametovVertex[N];
        for (int i = 0; i < N; i++) {
            DenisNurmuhametovVertex<Integer> newVertex = new DenisNurmuhametovVertex<>(null);
            directAccessVertices[i] = newVertex;
            myGraph.insertVertex(newVertex);
        }


        for (int i = 0; i < N; i++) {
            DenisNurmuhametovVertex fromVertex = directAccessVertices[i];
            for (int j = 0; j < N; j++) {
                int weight = sc.nextInt();
                if (weight != 0) {
                    DenisNurmuhametovVertex<Integer> toVertex = directAccessVertices[j];
                    myGraph.insertEdge(new DenisNurmuhametovEdge<>(fromVertex, toVertex, weight));
                }
            }
        }

        myGraph.DenisNurmuhametov_bellman_ford(directAccessVertices[0]);
    }
}


interface denisStackADT<T> {
    void push(T element);

    T pop();

    T peek();

    boolean isEmpty();

    int size();
}


class denisStack<T> implements denisStackADT<T> {
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


class DenisNurmuhametovVertex<T> {
    public T label;
    public int index = 0;
    public static int increment = 1;
    public int weight;
    public DenisNurmuhametovVertex<T> parent;
    public LinkedList<DenisNurmuhametovEdge<T>> incidentEdges;
    public LinkedList<DenisNurmuhametovVertex<T>> adjacentVertices;

    public DenisNurmuhametovVertex(T label) {
        this.label = label;
        this.index = increment;
        this.incidentEdges = new LinkedList<>();
        this.adjacentVertices = new LinkedList<>();
        this.weight = Integer.MAX_VALUE;
        this.parent = null;
        increment++;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

class DenisNurmuhametovEdge<T> {
    public DenisNurmuhametovVertex from, to;
    public T weight;

    public DenisNurmuhametovEdge(DenisNurmuhametovVertex<T> from, DenisNurmuhametovVertex<T> to, T weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}


class DenisNurmuhametovGraph<T> {
    LinkedList<DenisNurmuhametovVertex<T>> vertices;
    LinkedList<DenisNurmuhametovEdge<T>> edges;
    ArrayList<Integer> verticesInCycle;


    public DenisNurmuhametovGraph() {
        vertices = new LinkedList<DenisNurmuhametovVertex<T>>();
        edges = new LinkedList<DenisNurmuhametovEdge<T>>();
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

    public void deleteVertex(DenisNurmuhametovVertex<T> vertex) {
        for (DenisNurmuhametovEdge<T> edge : vertex.incidentEdges) {
            if (!edge.from.equals(vertex)) {
                edge.from.incidentEdges.remove(edge);
                edge.from.adjacentVertices.remove(vertex);
            }
            if (!edge.to.equals(vertex)) {
                edge.to.incidentEdges.remove(edge);
                edge.to.adjacentVertices.remove(vertex);
            }
            edges.remove(edge);
        }
        vertices.remove(vertex);
    }

    public void deleteEdge(DenisNurmuhametovEdge<T> edge) {
        edge.to.incidentEdges.remove(edge);
        edge.from.incidentEdges.remove(edge);
        edges.remove(edge);
    }

    public String DenisNurmuhametov_topsort() {
        int verSize = vertices.size();
        denisStack<DenisNurmuhametovVertex<T>> answer = new denisStack<>();
        int[] color = new int[DenisNurmuhametovVertex.increment];

        for (int i = 0; i < verSize; i++) {
            if (color[vertices.get(i).index] == 0) {
                if (!DenisNurmuhametov_dfs_cycleAccounted(vertices.get(i), answer, color)) {
                    return "Doh";
                }
            }
        }

        StringBuilder sorted = new StringBuilder();
        while (!answer.isEmpty()) {
            sorted.append(answer.pop().label);
        }
        return sorted.toString();
    }


    private boolean DenisNurmuhametov_dfs_cycleAccounted(DenisNurmuhametovVertex<T> vertex, denisStack<DenisNurmuhametovVertex<T>> stack, int[] color) {
        color[vertex.index] = 1;
        for (DenisNurmuhametovVertex<T> adjacent : vertex.adjacentVertices) {
            if (color[adjacent.index] == 1) {
                return false;
            }
            if (color[adjacent.index] == 0) {
                if (!DenisNurmuhametov_dfs_cycleAccounted(adjacent, stack, color)) {
                    return false;
                }
            }
        }
        color[vertex.index] = 2;
        stack.push(vertex);
        return true;
    }

    public void DenisNurmuhametov_dfs(DenisNurmuhametovVertex<T> vertex, denisStack<DenisNurmuhametovVertex<T>> denisStack, boolean visited[], LinkedList<DenisNurmuhametovVertex<T>> adjacentVertices) {
        visited[vertex.index] = true;
        for (DenisNurmuhametovVertex v : adjacentVertices) {
            if (!visited[v.index]) {
                DenisNurmuhametov_dfs(v, denisStack, visited, v.adjacentVertices);
            }
        }
        denisStack.push(vertex);
    }

    public void DenisNurmuhametov_bellman_ford(DenisNurmuhametovVertex<T> source) {
        source.setWeight(0);
        for (int i = 1; i < vertices.size(); i++) {
            for (int j = 0; j < edges.size(); j++) {
                DenisNurmuhametovEdge currentEdge = edges.get(j);
                relax(currentEdge.from, (Integer) currentEdge.weight, currentEdge.to);
            }
        }

        for (int i = 0; i < edges.size(); i++) {
            DenisNurmuhametovEdge currentEdge = edges.get(i);
            if (currentEdge.from.weight + (Integer) currentEdge.weight < currentEdge.to.weight) {
                DenisNurmuhametovVertex cycleStarted = currentEdge.from;
                verticesInCycle.add(cycleStarted.index);
                cycleStarted = cycleStarted.parent;
                if (cycleStarted != null) {
                    while (cycleStarted != currentEdge.from) {
                        verticesInCycle.add(cycleStarted.index);
                        cycleStarted = cycleStarted.parent;
                    }
                }
                break;
            }
        }

        if (!verticesInCycle.isEmpty()) {
            System.out.println("YES");
            System.out.println(verticesInCycle.size());
        } else {
            System.out.println("NO");
        }

        for (int i = verticesInCycle.size()-1; i > -1; i--) {
            if (i != 0) {
                System.out.print(verticesInCycle.get(i) + " ");
            } else {
                System.out.print(verticesInCycle.get(i));
            }
        }

    }


    private void relax(DenisNurmuhametovVertex<T> from, int edgeWeight, DenisNurmuhametovVertex<T> to) {
        if (from.weight != Integer.MAX_VALUE && from.weight + edgeWeight < to.weight) {
            to.weight = from.weight + edgeWeight;
            to.parent = from;
        }
    }


    public void printGraph() {
        for (DenisNurmuhametovVertex<T> vertex : vertices) {
            System.out.println(vertex.index + " " + vertex.weight);
        }
    }
}



/*

1) I took my graph implementation from Week 10 Coding Exercises and modified it
2) I took my Stack Implementation from Coding Exercise Week 2
3) Bellman-Ford algorithm with slight modifications is taken from the CLRS book pages 612, 610, 609
*/