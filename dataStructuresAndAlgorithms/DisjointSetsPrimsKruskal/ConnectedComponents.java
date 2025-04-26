//The author is Denis Nurmuhametov CSE-06

import java.util.LinkedList;
import java.util.Scanner;

public class ConnectedComponents {
    public static void main(String[] args) {
        DenisNurmuhametovGraph myGraph = new DenisNurmuhametovGraph();
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        DenisNurmuhametovVertex<Integer> directAccessVertices[] = new DenisNurmuhametovVertex[N + 1];
        int M = sc.nextInt();
        for (int i = 1; i < N + 1; i++) {
            DenisNurmuhametovVertex newVertex = new DenisNurmuhametovVertex(null);
            directAccessVertices[i] = newVertex;
            myGraph.insertVertex(newVertex);
        }

        for (int i = 0; i < M; i++) {
            int source = sc.nextInt();
            int destination = sc.nextInt();
            myGraph.insertEdge(new DenisNurmuhametovEdge(directAccessVertices[source], directAccessVertices[destination], 1));
        }

        myGraph.DenisNurmuhametov_mst_kruskal();
    }
}


interface NurmuhametovDenisSetsADT {
    int find(int x);

    void union(int x, int y);
}

class NurmuhametovDenisSets implements NurmuhametovDenisSetsADT {
    private int[] reprElem;
    private int[] ranks;

    public NurmuhametovDenisSets(int size) {
        reprElem = new int[size + 1];
        ranks = new int[size + 1];
        for (int i = 1; i <= size; i++) {
            reprElem[i] = i;
            ranks[i] = 1;
        }
    }

    public int countDisjointComponents() {
        int count = 0;
        for (int i = 1; i < reprElem.length; i++) {
            if (reprElem[i] == i) count++;
        }
        return count;
    }


    @Override
    public int find(int x) {
        if (reprElem[x] != x) {
            reprElem[x] = find(reprElem[x]);
        }
        return reprElem[x];
    }

    @Override
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            if (ranks[rootX] > ranks[rootY]) {
                reprElem[rootY] = rootX;
            } else if (ranks[rootX] < ranks[rootY]) {
                reprElem[rootX] = rootY;
            } else {
                reprElem[rootY] = rootX;
                ranks[rootX]++;
            }
        }
    }
}


class DenisNurmuhametovVertex<T> {
    public T label;
    public int index;
    private static int incrementCounter = 1;
    public int weight;
    public DenisNurmuhametovVertex<T> parent;
    public LinkedList<DenisNurmuhametovEdge<T>> incidentEdges;
    public LinkedList<DenisNurmuhametovVertex<T>> adjacentVertices;

    public DenisNurmuhametovVertex(T label) {
        this.label = label;
        this.incidentEdges = new LinkedList<>();
        this.adjacentVertices = new LinkedList<>();
        this.weight = Integer.MAX_VALUE;
        this.parent = null;
        this.index = incrementCounter;
        incrementCounter++;
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

    public DenisNurmuhametovGraph() {
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
    }

    public void insertVertex(DenisNurmuhametovVertex<T> vertex) {
        vertices.addLast(vertex);
    }

    public void insertEdge(DenisNurmuhametovEdge<T> newEdge) {
        edges.addLast(newEdge);
        newEdge.from.adjacentVertices.add(newEdge.to);
        newEdge.from.incidentEdges.add(newEdge);
    }

    public void DenisNurmuhametov_mst_kruskal() {
        DenisNurmuhametovEdge<T>[] directAccessEdges = edges.toArray(new DenisNurmuhametovEdge[0]);
        NurmuhametovDenisSets disjointSets = new NurmuhametovDenisSets(vertices.size());
//        QuickSortEdges(0, edges.size() - 1);
        for (int i = 0; i < directAccessEdges.length; i++) {
            int sourceIdx = directAccessEdges[i].from.index;
            int destinationIdx = directAccessEdges[i].to.index;
            if (disjointSets.find(sourceIdx) != disjointSets.find(destinationIdx)) {
                disjointSets.union(sourceIdx, destinationIdx);
            }
        }

        System.out.println(disjointSets.countDisjointComponents());
    }


    private void QuickSortEdges(int start, int end) {
        if (end <= start) {
            return;
        }
        int sortingMarker = helper(start, end);
        QuickSortEdges(start, sortingMarker - 1);
        QuickSortEdges(sortingMarker + 1, end);
    }

    private int helper(int start, int end) {
        int sortingMarker = (Integer) edges.get(end).weight;
        int i = start - 1;
        int j = start;
        while (j <= end - 1) {
            if ((Integer) edges.get(j).weight <= sortingMarker) {
                i++;
                swap(i, j);
            }
            j++;
        }
        i++;
        swap(i, end);
        return i;
    }

    private void swap(int i, int j) {
        DenisNurmuhametovEdge edge1 = edges.get(i);
        DenisNurmuhametovEdge edge2 = edges.get(j);

        DenisNurmuhametovVertex tmpFrom = edge1.from;
        DenisNurmuhametovVertex tmpTo = edge1.to;
        int tmpWeight = (Integer) edge1.weight;

        edge1.from = edge2.from;
        edge1.to = edge2.to;
        edge1.weight = edge2.weight;

        edge2.from = tmpFrom;
        edge2.to = tmpTo;
        edge2.weight = tmpWeight;
    }


    public void printGraph() {
        for (DenisNurmuhametovEdge<T> edge : edges) {
            System.out.println(edge.from.index + " " + edge.to.index + " " + edge.weight);
        }
    }

}


/*
References:
1) I took the DisjointSets implementation from Ali Jnadi Lab 11 and modified it
2) I took my QuickSort algorithm from the Coding Exercise Week 5 and modified it to work with the edges
3) I took my Graph data structure from Coding Exercise Week 12
4) Kruskal's Algorithm Idea was taken from Tutorial 11
 */