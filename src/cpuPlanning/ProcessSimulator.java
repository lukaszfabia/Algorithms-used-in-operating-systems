package cpuPlanning;

import cpuPlanning.Process;

import java.util.*;

public class ProcessSimulator {
    private List<Process> processes;
    private List<Process> processList;

    public ProcessSimulator(int amount) {
        this.processes = new ArrayList<>();
        Random r = new Random();

        for (int i = 0; i < amount; i++) {
            processes.add(new Process(r.nextInt(100), r.nextInt(200), (i + 1)));
        }
    }

    public ProcessSimulator(List<Process> processList) {
        this.processList = processList;
    }

    public List<Process> getProcesses() {
        return processList;
    }

    public void showData() {
        System.out.printf("%-20s %-20s %-20s\n","Number", "Arrival time", "Execute time");
        for (Process process : processList) {
            System.out.println(process);
        }
        System.out.println();
    }

    public void fcfsSimulation() {
        List<Process> fcfsList = new ArrayList<>(processList);

        fcfsList.sort(Comparator.comparing(Process::getArrivalTime).thenComparing(Process::getNr));
        int currentTime = 0;
        double totalWaitingTime = 0;

        for (Process process : fcfsList) {
            currentTime += process.getExecuteTime();
            process.setWaitingTime(currentTime-process.getArrivalTime());
            System.out.println("Process has "+process.getNr()+ " waited: "+process.getWaitingTime()+" ms");
            totalWaitingTime+=process.getWaitingTime();
        }


        System.out.printf("%s %.2f %s\n", "Total waiting time for FCFS: ", totalWaitingTime, " ms");
        System.out.printf("%s %.2f %s\n", "Average waiting time for FCFS: ", totalWaitingTime / getProcesses().size(), "ms");
    }


    public void sjfSimulation() {
        List<Process> sjfList = new ArrayList<>(processList);

        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(Process::getExecuteTime));
        int time = 0;
        double totalWaitingTime = 0;
        int i = 0;
        System.out.println("SJF:");
        while (!queue.isEmpty() || i < sjfList.size()) {
            if (!queue.isEmpty()) {
                Process currentProcess = queue.poll();
                totalWaitingTime += time - currentProcess.getArrivalTime();
                time += currentProcess.getExecuteTime();
                while (i < sjfList.size() && sjfList.get(i).getArrivalTime() <= time) {
                    queue.add(sjfList.get(i));
                    i++;
                }
            } else {
                queue.add(sjfList.get(i));
                time = sjfList.get(i).getArrivalTime();
                i++;
            }
        }

        System.out.printf("%s %.2f %s\n", "Total waiting time: ", (totalWaitingTime), " ms");
        System.out.printf("%s %.2f %s\n", "Average waiting time for SJF: ", (totalWaitingTime) / getProcesses().size(), "ms");
    }

    public void rrSimulation(int quantum) {
        List<Process> rrList = new ArrayList<>(processList);

        int totalExecutionTime = 0;
        int remainingProcesses = rrList.size();
        int completedProcess = 0;

        while (remainingProcesses != 0) {
            for (Process process : rrList) {
                int remainingTime = process.getRemainingTime();

                if (remainingTime <= quantum && remainingTime > 0) {
                    totalExecutionTime += remainingTime;
                    process.setCompletionTime(totalExecutionTime);
                    process.setRemainingTime(0);
                    completedProcess = 1;
                } else if (remainingTime > 0) {
                    process.setRemainingTime(remainingTime - quantum);
                    totalExecutionTime += quantum;
                }
                if (process.getRemainingTime() == 0 && completedProcess == 1) {
                    remainingProcesses--;
                    process.setWaitingTime(totalExecutionTime - process.getExecuteTime() - process.getArrivalTime());
                    completedProcess = 0;
                }
            }
        }

        double totalWaitingTime = 0;
        System.out.println("Round Robin:");
        for (Process process : rrList) {
            totalWaitingTime += process.getWaitingTime();
            System.out.println("Process has "+process.getNr()+ " ended: "+process.getWaitingTime()+" ms");
        }

        System.out.printf("%s %.2f %s\n", "Total waiting time for RR: ", totalWaitingTime, " ms");
        System.out.printf("%s %.2f %s\n", "Average waiting time for RR: ", totalWaitingTime / rrList.size(), " ms");
    }

    public void srtfSimulation() {
        List<Process> srtfList = new ArrayList<>(processList);

        int currentTime = 0;
        int completedProcesses = 0;
        double totalWaitingTime = 0;

        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingTime).thenComparingInt(Process::getNr));
        System.out.println("SRTF:");
        while (completedProcesses < srtfList.size()) {
            for (Process p : srtfList) {
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
                    System.out.println("Process has "+p.getNr()+ " waited: "+p.getWaitingTime()+" ms");
                    completedProcesses++;
                } else {
                    readyQueue.offer(p);
                }
            }
            currentTime++;
        }

        System.out.printf("%s %.2f %s\n", "Total waiting time for SRTF: ", (totalWaitingTime), " ms");
        System.out.printf("%s %.2f %s\n", "Average waiting time for SRTF: ", (totalWaitingTime) / srtfList.size(), " ms");
    }


}
