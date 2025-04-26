package dataStructuresAndAlgorithms.DynamicProgramming;
/*
The author is Denis Nurmuhametov
 */


import java.util.ArrayList;
import java.util.Scanner;

public class JobScheduling {
    private static int[] profitSaver;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        profitSaver = new int[n];
        ArrayList<DenisNurmuhametovJob> jobs = new ArrayList<>();
        String[] starts = scanner.nextLine().split(" ");
        String[] finishes = scanner.nextLine().split(" ");
        String[] profits = scanner.nextLine().split(" ");

        for (int i = 0; i < n; i++) {
            jobs.add(new DenisNurmuhametovJob(Integer.parseInt(starts[i]), Integer.parseInt(finishes[i]), Integer.parseInt(profits[i])));
        }


        denisNurmuhametovVeryQuickSort(jobs, 0, n - 1);

        profitSaver[0] = jobs.get(0).getProfit();
        for (int i = 1; i < n; i++) {
            profitSaver[i] = Math.max(profitSaver[i - 1], jobs.get(i).getProfit());
            for (int j = i - 1; j >= 0; j--) {
                if (jobs.get(j).getFinish() <= jobs.get(i).getStart()) {
                    profitSaver[i] = Math.max(profitSaver[i], profitSaver[j] + jobs.get(i).getProfit());
                    break;
                }
            }
        }
        System.out.println(profitSaver[n - 1]);
    }


    public static void denisNurmuhametovVeryQuickSort(ArrayList<DenisNurmuhametovJob> jobs, int start, int end) {
        if (end <= start) {
            return;
        }
        int sortingMarker = helper(jobs, start, end);
        denisNurmuhametovVeryQuickSort(jobs, start, sortingMarker - 1);
        denisNurmuhametovVeryQuickSort(jobs, sortingMarker + 1, end);
    }

    public static int helper(ArrayList<DenisNurmuhametovJob> jobs, int start, int end) {
        int sortingMarker = jobs.get(end).getFinish();
        int i = start - 1;
        int j = start;
        while (j <= end - 1) {
            if (jobs.get(j).getFinish() <= sortingMarker) {
                i++;
                swap(jobs, i, j);
            }
            j++;
        }
        i++;
        swap(jobs, i, end);
        return i;
    }

    private static void swap(ArrayList<DenisNurmuhametovJob> jobs, int i, int j) {
        DenisNurmuhametovJob job1 = jobs.get(i);
        DenisNurmuhametovJob job2 = jobs.get(j);
        int tmpStart = job1.getStart();
        int tmpFinish = job1.getFinish();
        int tmpProfit = job1.getProfit();
        job1.setStart(job2.getStart());
        job1.setFinish(job2.getFinish());
        job1.setProfit(job2.getProfit());
        job2.setStart(tmpStart);
        job2.setFinish(tmpFinish);
        job2.setProfit(tmpProfit);
    }
}


class DenisNurmuhametovJob {
    public int start;
    public int finish;
    public int profit;


    public DenisNurmuhametovJob() {
        this.start = 0;
        this.finish = 0;
        this.profit = 0;
    }

    public DenisNurmuhametovJob(int start, int end, int profit) {
        this.start = start;
        this.finish = end;
        this.profit = profit;
    }


    public void setProfit(int profit) {
        this.profit = profit;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getProfit() {
        return this.profit;
    }

    public int getStart() {
        return this.start;
    }

    public int getFinish() {
        return this.finish;
    }

    public void listJob() {
        System.out.println("Job start: " + this.start + " ends " + this.finish + " profit: " + this.profit);
    }

}
/*
References:
1) I used my implementation of quickSort algorithm from my previous code exercises and modified it
2) I took the idea from tutorial 5 and implemented bottom-up approach
 */