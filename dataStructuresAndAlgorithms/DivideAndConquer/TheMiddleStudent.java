package dataStructuresAndAlgorithms.DivideAndConquer;
/*
The author is Denis Nurmuhametov
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TheMiddleStudent {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<DenisNurmuhametovPair<Integer, String>> inputs = new ArrayList<>();
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            String[] inputedData = scanner.nextLine().split(" ");
            int benchmark = Integer.parseInt(inputedData[0]);
            String cpuModel = inputedData[1];
            DenisNurmuhametovPair<Integer, String> pair = new DenisNurmuhametovPair<>(benchmark, cpuModel);
            inputs.add(pair);
        }

        System.out.println(denisNurmuhametovMedianAlgoritm(inputs, inputs.size() / 2));

    }

    public static String denisNurmuhametovMedianAlgoritm(ArrayList<DenisNurmuhametovPair<Integer, String>> list, int k) {
        if (list.size() == 1) {
            if (k != 0) {
                System.exit(-1);
            }
            return (String) list.get(0).getValue();
        }

        Random random = new Random();
        int leadingEL = list.get(random.nextInt(list.size())).getKey();

        ArrayList<DenisNurmuhametovPair<Integer, String>> belowLeading = new ArrayList<>();
        ArrayList<DenisNurmuhametovPair<Integer, String>> upLeading = new ArrayList<>();
        ArrayList<DenisNurmuhametovPair<Integer, String>> sameLeading = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {
            DenisNurmuhametovPair<Integer, String> currentPair = list.get(i);
            if (currentPair.getKey() < leadingEL) {
                belowLeading.add(currentPair);

            } else if (currentPair.getKey() > leadingEL) {
                upLeading.add(currentPair);
            } else {
                sameLeading.add(currentPair);
            }
        }

        if (k < belowLeading.size()) {
            return denisNurmuhametovMedianAlgoritm(belowLeading, k);
        } else if (k < belowLeading.size() + sameLeading.size()) {
            return sameLeading.get(0).getValue();
        } else {
            return denisNurmuhametovMedianAlgoritm(upLeading, k - belowLeading.size() - sameLeading.size());
        }
    }
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

    public V getValue() {
        return this.value;
    }

    public K getKey() {
        return this.key;
    }
}
/*
References:
1)https://habr.com/ru/articles/346930/ took the idea for the algorithm and improved it to find median cpuModel
2)also I took my pair class from previous assignments (especially 3rd coding exercise task 1)
 */