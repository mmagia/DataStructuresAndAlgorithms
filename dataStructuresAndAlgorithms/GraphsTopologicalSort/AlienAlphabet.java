// The author is Denis Nurmuhametov
package dataStructuresAndAlgorithms.GraphsTopologicalSort;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.EmptyStackException;


public class AlienAlphabet {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();

        String[] scannedWords = new String[n];
        for (int i = 0; i < n; i++) {
            scannedWords[i] = scanner.nextLine();
        }

        DenisNurmuhametovGraph<Character> denisNurmuhametovGraph = new DenisNurmuhametovGraph<>();
        ArrayList<Character> processedLetters = new ArrayList<>();

        for (String word : scannedWords) {
            for (char character : word.toCharArray()) {
                if (!processedLetters.contains(character)) {
                    processedLetters.add(character);
                    denisNurmuhametovGraph.insertVertex(new DenisNurmuhametovVertex<>(character));
                }
            }
        }

        for (int i = 0; i < n - 1; i++) {
            String previousWord = scannedWords[i];
            String currentWord = scannedWords[i + 1];
            int minLen = Math.min(previousWord.length(), currentWord.length());

            if (previousWord.length() > currentWord.length() && previousWord.substring(0, minLen).equals(currentWord.substring(0, minLen))) {
                System.out.print("Doh");
                System.exit(0);
            }

            for (int j = 0; j < minLen; j++) {
                if (previousWord.charAt(j) != currentWord.charAt(j)) {
                    DenisNurmuhametovVertex<Character> from = null;
                    DenisNurmuhametovVertex<Character> to = null;

                    for (DenisNurmuhametovVertex<Character> v : denisNurmuhametovGraph.vertices) {
                        if (v.label == previousWord.charAt(j)) {
                            from = v;
                        }
                        if (v.label == currentWord.charAt(j)) {
                            to = v;
                        }
                    }
                    if (from != null && to != null) {
                        denisNurmuhametovGraph.insertEdge(new DenisNurmuhametovEdge<>(from, to, null));
                    }
                    break;
                }
            }
        }

        String result = denisNurmuhametovGraph.DenisNurmuhametov_topsort();

        if (result.length() == processedLetters.size()) {
            System.out.println(result);
        } else {
            System.out.println("Doh");
        }
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
    public static int increment = 0;
    public LinkedList<DenisNurmuhametovEdge<T>> incidentEdges;
    public LinkedList<DenisNurmuhametovVertex<T>> adjacentVertices;

    public DenisNurmuhametovVertex(T label) {
        this.label = label;
        this.index = increment;
        this.incidentEdges = new LinkedList<>();
        this.adjacentVertices = new LinkedList<>();
        increment++;
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


    public DenisNurmuhametovGraph() {
        vertices = new LinkedList<DenisNurmuhametovVertex<T>>();
        edges = new LinkedList<DenisNurmuhametovEdge<T>>();
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

    public void printGraph() {
        for (DenisNurmuhametovVertex<T> vertex : vertices) {
            System.out.println(vertex.label);
            for (DenisNurmuhametovEdge<T> edge : vertex.incidentEdges) {
                System.out.println(edge.from.label + "->" + edge.to.label);
            }
            for (DenisNurmuhametovVertex<T> vertex1 : vertex.adjacentVertices) {
                System.out.print(vertex1.label + " ");
            }
            System.out.println();
            System.out.println("$$$$$");
        }
    }
}



/*

1) I took the graph idea from the lab, lecture and tutorial
2) I took the DFS and DFS-Visit idea from the tutorial 10 slide and 11 respectively
3) https://www.geeksforgeeks.org/topological-sorting/
4) I took my Stack Implementation from Coding Exercise Week 2
*/