package dataStructuresAndAlgorithms.DivideAndConquer;
/*
The author is Denis Nurmuhametov
 */
import java.util.ArrayList;
import java.util.Scanner;

public class kArySearch {
    public static void main(String[] args) {
        ArrayList<Integer> inputedNumbers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            inputedNumbers.add(scanner.nextInt());
        }

        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            int x = scanner.nextInt();
            int index = denisSearch(inputedNumbers, x, k, 0, n - 1);
            System.out.println(index);
        }


    }


    public static int denisSearch(ArrayList<Integer> inputedNumbers, int toBeFound, int parts, int start, int end) {
        if (inputedNumbers.isEmpty()) {
            return -1;
        }

        if (end >= start) {
            int listSize = end - start + 1;
            if (listSize == 1 && inputedNumbers.get(end) == toBeFound) {
                return end;
            }

            if (listSize == 1 && inputedNumbers.get(end) != toBeFound) {
                return -1;
            }


            int[] midPoints = new int[parts + 2];
            midPoints[0] = 0;
            midPoints[midPoints.length - 1] = start + listSize - 1;

            int coef = (listSize - 1) / parts;
            for (int i = 1; i <= parts; i++) {
                midPoints[i] = i * coef;
            }

            for (int i = 0; i < midPoints.length - 1; i++) {
                if (inputedNumbers.get(midPoints[i]) == toBeFound) {
                    return midPoints[i];
                }
                if (inputedNumbers.get(midPoints[i + 1]) == toBeFound) {
                    return midPoints[i + 1];
                }

                if (inputedNumbers.get(midPoints[i]) < toBeFound && inputedNumbers.get(midPoints[i + 1]) > toBeFound) {
                    return denisSearch(inputedNumbers, toBeFound, parts, midPoints[i]+1, midPoints[i+1]-1);
                }
            }

        }
        return -1;
    }

}
