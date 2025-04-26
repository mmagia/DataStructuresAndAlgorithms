package dataStructuresAndAlgorithms.HashMaps;
/*
The author is Denis Nurmuhametov
 */
import java.util.Scanner;
import java.util.ArrayList;

public class SpellChecker {
    private static DenisNurmuhametovMap<String, Integer> secondStringElements = new DenisNurmuhametovMap<>();
    private static ArrayList<String> answer = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n1 = Integer.parseInt(scanner.nextLine());
        String[] firstLine = scanner.nextLine().split(" ");
        int n2 = Integer.parseInt(scanner.nextLine());
        String[] secondLine = scanner.nextLine().split(" ");
        for (int i = 0; i < n2; i++) {
            if (secondStringElements.get(secondLine[i]) == null) {
                secondStringElements.put(secondLine[i], 1);
            }
        }

        for (int i = 0; i < n1; i++) {
            Integer check = secondStringElements.get(firstLine[i]);
            if (check == null) {
                answer.add(firstLine[i]);
                secondStringElements.put(firstLine[i], 1);
            }
        }

        int size = answer.size();
        System.out.println(size);

        for (int i = 0; i < size; i++) {
            System.out.println(answer.get(i));
        }

    }
}


