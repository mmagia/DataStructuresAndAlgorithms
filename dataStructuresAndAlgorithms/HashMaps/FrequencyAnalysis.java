package dataStructuresAndAlgorithms.HashMaps;/*
The author is Denis Nurmuhametov CSE-06
 */
import java.util.Scanner;

public class FrequencyAnalysis {
    private static DenisNurmuhametovMap<String, Integer> precedenceMap = new DenisNurmuhametovMap<>();
    public static void main(String[] args) {
        DenisNurmuhametovMap<String, Integer> inputMap = new DenisNurmuhametovMap<>();
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int k = Integer.parseInt(scanner.nextLine());
        String[] inputData = scanner.nextLine().split(" ");
        for (int i = 0; i < inputData.length; i++) {
            if (inputMap.get(inputData[i]) == null) {
                inputMap.put(inputData[i], 1);
                precedenceMap.put(inputData[i], i);
            } else {
                inputMap.put(inputData[i], inputMap.get(inputData[i]) + 1);
            }
        }

        String[] keys = inputMap.keys();
        int[] values = inputMap.values();
        denisNurmuhametovVeryQuickSort(values, keys, 0, keys.length - 1);
        System.out.println(keys[k - 1]);
    }

    public static void denisNurmuhametovVeryQuickSort(int[] values, String[] keys, int start, int end) {
        if (end <= start) {
            return;
        }
        int sortingMarker = helper(values, keys, start, end);
        denisNurmuhametovVeryQuickSort(values, keys, start, sortingMarker - 1);
        denisNurmuhametovVeryQuickSort(values, keys, sortingMarker + 1, end);
    }

    public static int helper(int[] values, String[] keys, int start, int end) {
        int sortingMarker = values[end];
        int i = start - 1;
        int j = start;
        while (j <= end - 1) {
            if (values[j] > sortingMarker) {
                i++;
                swap(values, i, j);
                swap(keys, i, j);
            } else if (values[j] == sortingMarker && precedenceMap.get(keys[j]) < precedenceMap.get(keys[end])) {
                i++;
                swap(values, i, j);
                swap(keys, i, j);
            }
            j++;
        }
        i++;
        swap(values, i, end);
        swap(keys, i, end);
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}


