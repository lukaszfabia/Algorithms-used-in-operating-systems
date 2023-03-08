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
        double totalWaitingTime = 0;
        List<Process> readyQueue = new LinkedList<>();
        int currentTime = 0;
        int completed = 0;

        while (completed < processList.size()) {
            // add any arriving processes to the ready queue
            for (Process p : processList) {
                if (p.getArrivalTime() == currentTime) {
                    readyQueue.add(p);
                }
            }
            readyQueue.sort(Comparator.comparingInt(Process::getArrivalTime).thenComparingInt(Process::getNr));
            processList.removeAll(readyQueue);

            // run each process in the ready queue for a time quantum
            for (Process p : readyQueue) {
                if (p.getRemainingTime() <= quantum) {
                    p.setRemainingTime(0);
                    p.setCompletionTime(p.getRemainingTime());
                    completed++;
                } else {
                    p.setRemainingTime(p.getRemainingTime() - quantum);
                }
            }
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
