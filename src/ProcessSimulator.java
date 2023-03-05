import java.util.*;

public class ProcessSimulator {
    private List<Process> processList1;
    private List<Process> processList;

    public ProcessSimulator(int amount) {
        this.processList1 = new ArrayList<>();
        Random r = new Random();

        for (int i = 0; i < amount; i++) {
            processList1.add(new Process(r.nextInt(100), r.nextInt(200), (i + 1)));
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
            try {
                Thread.sleep(currentTime);
                System.out.println("Process " + (i + 1) + ": " + currentTime + " ms");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            totalWaitingTime += currentTime;
        }
        System.out.printf("%s %.2f %s\n", "Average waiting time for FCFS: ", totalWaitingTime / getProcesses().size(), "ms");
    }


    public void sjfSimulation() {
        double totalWaitingTime = 0;

        processList.sort(Comparator.comparing(Process::getArrivalTime).thenComparing(Process::getExecuteTime).thenComparing(Process::getNr));

        for (Process process : processList) {
            totalWaitingTime += process.getExecuteTime();
        }

        System.out.printf("%s %.2f %s\n", "Total waiting time: ", (totalWaitingTime - processList.get(0).getArrivalTime()), " ms");
        System.out.printf("%s %.2f %s\n", "Average waiting time for SJF: ", (totalWaitingTime - processList.get(0).getArrivalTime()) / getProcesses().size(), "ms");
    }

    public void rrSimulation(int quantum) {
        Queue<Process> queue = new LinkedList<>(processList);
        int time = 0;
        double ave = 0;
        while (!queue.isEmpty()) {
            Process process = queue.poll();
            int remainingTime = process.getExecuteTime() - quantum;
            time += quantum;
            if (remainingTime > 0) {
                process.setExecuteTime(remainingTime);
                queue.add(process);
            } else {
                time += remainingTime;
                System.out.println(process.getNr() + " zakończony w czasie " + time);
                ave += time;
            }
        }
        System.out.printf("%s %.2f %s\n", "Total waiting time for RR: ", ave, " ms");
        System.out.printf("%s %.2f %s\n", "Average waiting time for RR: ", ave / processList.size(), " ms");
    }

    public void srtfSimulation() {
        int n = processList.size();

        int[] remainingTime = new int[n];
        int[] waitingTime = new int[n];

        // kopiowanie czasów trwania procesów do remainingTime
        for (int i = 0; i < n; i++) {
            remainingTime[i] = processList.get(i).getExecuteTime();
        }

        int complete = 0; // liczba zakończonych procesów
        int t = 0; // aktualny czas
        int minRemainingTimeIndex; // indeks procesu o najkrótszym pozostałym czasie

        while (complete != n) {
            minRemainingTimeIndex = -1;

            // szukanie procesu o najkrótszym pozostałym czasie
            for (int i = 0; i < n; i++) {
                if (remainingTime[i] > 0 && (minRemainingTimeIndex == -1 || remainingTime[i] < remainingTime[minRemainingTimeIndex])) {
                    minRemainingTimeIndex = i;
                }
            }

            if (minRemainingTimeIndex == -1) {
                t++;
                continue;
            }

            remainingTime[minRemainingTimeIndex]--;

            if (remainingTime[minRemainingTimeIndex] == 0) {
                complete++;

                int completionTime = t + 1;
                int turnaroundTime = completionTime - processList.get(minRemainingTimeIndex).getArrivalTime();
                int waitingTimeProcess = turnaroundTime - processList.get(minRemainingTimeIndex).getExecuteTime();

                waitingTime[minRemainingTimeIndex] = Math.max(waitingTimeProcess, 0);
            }

            t++;
        }

        // obliczanie średniego czasu oczekiwania procesu w kolejce
        int totalWaitingTime = 0;

        for (int i = 0; i < n; i++) {
            totalWaitingTime += waitingTime[i];
        }

        double averageWaitingTime = (double) totalWaitingTime / n;
        System.out.println("Średni czas oczekiwania procesu w kolejce: " + averageWaitingTime);


    }


}
