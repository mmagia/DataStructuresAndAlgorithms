#include <iostream>
#include <vector>

using namespace std;

template<typename T>
class DenisNurmuhametovDAryHeap {
public:
    vector<T> heap;
    int amountOfChildrens;

    DenisNurmuhametovDAryHeap(int amountOfChildrens) {
        this->amountOfChildrens = amountOfChildrens;
    }

    void fixHeap() {
        for (int i = heap.size() / 2; i >= 0; i--) {
            maxHeapify(heap, i);
        }
    }

private:
    void maxHeapify(vector<T>& heap, int index) {
        int largestIndex = index;
        T largestValue = heap[index];
        for (int i = 1; i < amountOfChildrens+1; i++) {
            int currentIndex = amountOfChildrens * index + i;
            if (currentIndex < heap.size() && heap[currentIndex] > largestValue) {
                largestValue = heap[currentIndex];
                largestIndex = currentIndex;
            }
        }

        if (largestIndex != index) {
            T tmp = heap[index];
            heap[index] = largestValue;
            heap[largestIndex] = tmp;
            maxHeapify(heap, largestIndex);
        }
    }
};


template<typename T>
void listHeap(DenisNurmuhametovDAryHeap<T> heap) {
    for (int i = 0; i < heap.heap.size(); i++) {
        cout << heap.heap[i] << " ";
    }
}


int main() {
    int N, d;
    cin >> N >> d;
    DenisNurmuhametovDAryHeap<int> test(d);
    for (int i = 0; i < N; i++) {
        int value;
        cin >> value;
        test.heap.push_back(value);
    }
    test.fixHeap();
    listHeap(test);

    return 0;
}
