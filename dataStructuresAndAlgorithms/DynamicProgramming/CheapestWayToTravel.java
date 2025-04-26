package dataStructuresAndAlgorithms.DynamicProgramming;
/*
The author is Denis Nurmuhametov
 */

import java.util.Scanner;

public class CheapestWayToTravel {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String path = "";
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] initialRoadCosts = new int[n][m];
        int[][] finalRoadCosts = new int[n][m];
        int[][] chosenPath = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int input = scanner.nextInt();
                finalRoadCosts[i][j] = input;
                initialRoadCosts[i][j] = input;
            }
        }

        for (int i = 1; i < n; i++) {
            finalRoadCosts[i][0] += finalRoadCosts[i - 1][0];
        }

        for (int i = 1; i < m; i++) {
            finalRoadCosts[0][i] += finalRoadCosts[0][i - 1];
            chosenPath[0][i] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (finalRoadCosts[i - 1][j] <= finalRoadCosts[i][j - 1]) {
                    finalRoadCosts[i][j] += finalRoadCosts[i - 1][j];
                    chosenPath[i][j] = 0;
                } else {
                    finalRoadCosts[i][j] += finalRoadCosts[i][j - 1];
                    chosenPath[i][j] = 1;
                }
            }
        }

        System.out.println(finalRoadCosts[n - 1][m - 1]);

        int row = n - 1;
        int col = m - 1;
        while (row >= 0 && col >= 0) {
            path = initialRoadCosts[row][col] + " " + path;
            if (row == 0 && col == 0) break;
            if (row > 0 && chosenPath[row][col] == 0) {
                row--;
            } else if (col > 0 && chosenPath[row][col] == 1) {
                col--;
            } else if (row > 0) {
                row--;
            } else if (col > 0) {
                col--;
            }
        }
        System.out.println(path);
    }

    public static void print2DMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

}