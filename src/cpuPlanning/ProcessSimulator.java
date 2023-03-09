package cpuPlanning;

import cpuPlanning.Process;

import java.util.*;

public class ProcessSimulator {
    private List<Process> processList1;
    private List<Process> processList;

    public ProcessSimulator(int amount) {
        this.processList1 = new ArrayList<>();
        Random r = new Random();

        for (int i = 0; i < amount; i++) {
            //processList1.add(new Process(r.nextInt(100), r.nextInt(200), (i + 1)));
        }
    }

    public ProcessSimulator(List<Process> processList) {
        this.processList = processList;
    }

    public List<Process> getProcesses() {
        return processList;
    }

    public void showData() {
        for (Process process : processList) {
            System.out.println(process);
        }
        System.out.println();
    }

    public void fcfsSimulation() {
        processList.sort(Comparator.comparing(Process::getArrivalTime).thenComparing(Process::getNr));
        int currentTime = 0;
        double totalWaitingTime = 0;

        for (int i = 0; i < processList.size() - 1; i++) {
            currentTime += processList.get(i).getExecuteTime();
            totalWaitingTime += currentTime;
        }
        System.out.printf("%s %.2f %s\n", "Total waiting time: ", (totalWaitingTime - processList.get(0).getArrivalTime()), " ms");
        System.out.printf("%s %.2f %s\n", "Average waiting time for FCFS: ", (totalWaitingTime - processList.get(0).getArrivalTime()) / getProcesses().size(), "ms");
    }


    public void sjfSimulation() {
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(Process::getExecuteTime));
        int time = 0;
        double totalWaitingTime = 0;
        int i = 0;

        while (!queue.isEmpty() || i < processList.size()) {
            if (!queue.isEmpty()) {
                Process currentProcess = queue.poll();
                totalWaitingTime += time - currentProcess.getArrivalTime();
                time += currentProcess.getExecuteTime();
                while (i < processList.size() && processList.get(i).getArrivalTime() <= time) {
                    queue.add(processList.get(i));
                    i++;
                }
            } else {
                queue.add(processList.get(i));
                time = processList.get(i).getArrivalTime();
                i++;
            }
        }

        System.out.printf("%s %.2f %s\n", "Total waiting time: ", (totalWaitingTime), " ms");
        System.out.printf("%s %.2f %s\n", "Average waiting time for SJF: ", (totalWaitingTime) / getProcesses().size(), "ms");
    }

    public void rrSimulation(int quantum) {
        int sum = 0;
        int count = 0;
        int delete = processList.size();

        while (delete != 0) {
            for (Process process : processList) {
                int temp = process.getRemainingTime();

                if (temp <= quantum && temp > 0) {
                    sum += temp;
                    process.setCompletionTime(sum);
                    process.setRemainingTime(0);
                    count = 1;
                } else if (temp > 0) {
                    process.setRemainingTime(temp - quantum);
                    sum += quantum;
                }
                if (process.getRemainingTime() == 0 && count == 1) {
                    delete--;
                    process.setWaitingTime(sum - process.getExecuteTime() - process.getArrivalTime());
                    count = 0;
                }
            }
        }

        double totalWaitingTime = 0;
        for (Process process : processList) {
            totalWaitingTime += process.getWaitingTime();
        }

        System.out.printf("%s %.2f %s\n", "Total waiting time for RR: ", (totalWaitingTime), " ms");
        System.out.printf("%s %.2f %s\n", "Average waiting time for RR: ", (totalWaitingTime) / processList.size(), " ms");

    }

    public void srtfSimulation() {
        int currentTime = 0;
        int completedProcesses = 0;
        double totalWaitingTime = 0;

        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime).thenComparingInt(Process::getNr));

        while (completedProcesses < processList.size()) {
            for (Process p : processList) {
                if (p.getArrivalTime() == currentTime) {
                    readyQueue.offer(p);
                }
            }

            if (!readyQueue.isEmpty()) {
                Process p = readyQueue.poll();
                p.setRemainingTime(p.getRemainingTime() - 1);
                if (p.getRemainingTime() == 0) {
                    // process completed
                    p.setCompletionTime(currentTime + 1);
                    p.setWaitingTime(p.getCompletionTime() - p.getExecuteTime() - p.getArrivalTime());
                    totalWaitingTime += p.getWaitingTime();
                    completedProcesses++;
                } else {
                    readyQueue.offer(p);
                }
            }

            currentTime++;
        }

        System.out.printf("%s %.2f %s\n", "Total waiting time for SRTF: ", (totalWaitingTime), " ms");
        System.out.printf("%s %.2f %s\n", "Average waiting time for SRTF: ", (totalWaitingTime) / processList.size(), " ms");
    }


}
