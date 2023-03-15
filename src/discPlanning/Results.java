package discPlanning;

import java.util.*;

public class Results {
    private final int cycles;
    private final int tasks;
    private final int priority;

    public Results(int cycles, int tasks, int priority) {
        this.priority = priority;
        this.cycles = cycles;
        this.tasks = tasks;
    }

    public ArrayList<Result> averageResults() {

        ArrayList<Result> resultList = new ArrayList<>();
        ArrayList<Task> randomTasks = new ArrayList<>();
        ArrayList<Task> randomPriorityTasks = new ArrayList<>();
        int sum_FCFS_EDF = 0;
        int sum_FCFS_FD = 0;
        int sum_SSTF_EDF = 0;
        int sum_SSTF_FD = 0;
        int sum_SCAN_EDF = 0;
        int sum_SCAN_FD = 0;
        int sum_C_SCAN_EDF = 0;
        int sum_C_SCAN_FD = 0;
        for (int j = 0; j < cycles; j++) {

            for (int i = 0; i < tasks * (100 - priority) / 100; i++) {
                Random r = new Random();
                int d = 1 + r.nextInt(1000);
                int m = 1 + r.nextInt(199);
                randomTasks.add(new Task(d, m));
            }

            for (int i = 0; i < tasks * priority / 100; i++) {
                Random r = new Random();
                int d = 1 + r.nextInt(1000);
                int m = 1 + r.nextInt(199);
                int k = 300 + r.nextInt(100);
                randomPriorityTasks.add(new Task(d, m, k));
            }
            DiscPlanningAlgorithms pr = new DiscPlanningAlgorithms(randomTasks, randomPriorityTasks);
            sum_FCFS_EDF += pr.fcfsEdf();
            sum_FCFS_FD += pr.fcfsFdScan();
            sum_SSTF_EDF += pr.sstfEdf();
            sum_SSTF_FD += pr.sstfFdScan();
            sum_SCAN_EDF += pr.scanEdf();
            sum_SCAN_FD += pr.scanFdScan();
            sum_C_SCAN_EDF += pr.cScanEdf();
            sum_C_SCAN_FD += pr.cScanFdScan();
            randomTasks.clear();
            randomPriorityTasks.clear();

        }

        resultList.add(new Result("FCFS EDF", sum_FCFS_EDF));
        resultList.add(new Result("FCFS FD-SCAN", sum_FCFS_FD));
        resultList.add(new Result("SSTF EDF", sum_SSTF_EDF));
        resultList.add(new Result("SSTF FD-SCAN", sum_SSTF_FD));
        resultList.add(new Result("SCAN EDF", sum_SCAN_EDF));
        resultList.add(new Result("SCAN FD-SCAN", sum_SCAN_FD));
        resultList.add(new Result("C SCAN EDF", sum_C_SCAN_EDF));
        resultList.add(new Result("C SCAN FD-SCAN", sum_C_SCAN_FD));

        return resultList;
    }

    public void showData() {
        List<Result> helpList = new ArrayList<>(averageResults());
        helpList.sort(Comparator.comparing(Result::discMoves));

        for (Result result : helpList) {
            System.out.println(result);
        }
    }
}
