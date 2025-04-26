/*
    The author is Denis Nurmuhametov
 */
#include <iostream>
#include <vector>
using namespace std;

template<typename T>
class DenisNurmuhametovPair {
public:
    T key;
    int occurence;

    DenisNurmuhametovPair(T key) : key(key), occurence(1) {}
};

template<typename T>
class DenisNurmuhametovBinaryHeap {
public:
    vector<DenisNurmuhametovPair<T>> buffer;
    int amountOfChildren;

    DenisNurmuhametovBinaryHeap() : amountOfChildren(2) {}

    void buildHeap() {
        for (int i = buffer.size() / 2; i >= 0; i--) {
            minHeapify(buffer, i);
        }
    }

    DenisNurmuhametovPair<T> extractMinDenisNurmuhametovPair() {
        if (buffer.empty()) {
            throw "Heap is empty";
        }
        DenisNurmuhametovPair<T> minPair = buffer[0];
        buffer[0] = buffer.back();
        buffer.pop_back();
        if (!buffer.empty()) {
            minHeapify(buffer, 0);
        }
        return minPair;
    }


private:
    void minHeapify(vector<DenisNurmuhametovPair<T>>& toBeHeapified, int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int smallest = index;

        if (left < toBeHeapified.size() && toBeHeapified[left].occurence < toBeHeapified[smallest].occurence) {
            smallest = left;
        }

        if (right < toBeHeapified.size() && toBeHeapified[right].occurence < toBeHeapified[smallest].occurence) {
            smallest = right;
        }

        if (smallest != index) {
            swap(toBeHeapified[index], toBeHeapified[smallest]);
            minHeapify(toBeHeapified, smallest);
        }
    }
};

int findElement(vector<DenisNurmuhametovPair<int>> buffer, int value) {
    for (int i = 0; i < buffer.size(); i++) {
        if (buffer[i].key == value) {
            return i;
        }
    }
    return -1;
}


int main() {
    int N;
    cin >> N;
    DenisNurmuhametovBinaryHeap<int> heap;

    for (int i = 0; i < N; i++) {
        int value;
        cin>>value;
        int find = findElement(heap.buffer, value);
        if (find == -1) {
            DenisNurmuhametovPair<int> elem(value);
            heap.buffer.push_back(elem);
        } else {
            heap.buffer[find].occurence++;
        }
    }

    heap.buildHeap();

    while (!heap.buffer.empty()) {
        DenisNurmuhametovPair<int> minPair = heap.extractMinDenisNurmuhametovPair();
        cout << minPair.key << endl;
    }

    return 0;
}