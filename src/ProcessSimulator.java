import java.util.*;

public class ProcessSimulator {
    private final List<Process> processes;

    public ProcessSimulator(int amount) {
        this.processes = new ArrayList<>();
        Random r = new Random();

        for (int i = 0; i < amount; i++) {
            processes.add(new Process(r.nextInt(100), r.nextInt(200), (i + 1)));
        }
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void showData() {
        for (Process process : processes) {
            System.out.println(process);
        }
        System.out.println();
    }

    public void fcfsSimulation() {
        processes.sort(Comparator.comparing(Process::getArrivalTime).thenComparing(Process::getNr));
        int currentTime = 0;
        double totalWaitingTime = 0;

        for (int i = 0; i < processes.size() - 1; i++) {
            currentTime += processes.get(i).getExecuteTime();
            try {
                Thread.sleep(currentTime);
                System.out.println("Process " + (i + 1) + ": " + currentTime+" ms");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            totalWaitingTime += currentTime;
        }
        System.out.printf("%s %.2f %s\n", "Average waiting time for FCFS: ", totalWaitingTime / getProcesses().size(), "ms");
    }


    public void sjfSimulation() {
        int currentTime = 0;
        double totalWaitingTime = 0;

        processes.sort(Comparator.comparingInt(Process::getExecuteTime).thenComparing(Process::getNr));

        for (int i = 0; i < processes.size() - 1; i++) {
            currentTime += processes.get(i).getExecuteTime();
            try {
                Thread.sleep(currentTime);
                System.out.println("Process " + (i + 1) + ": " + currentTime+" ms");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            totalWaitingTime += currentTime;
        }

        System.out.printf("%s %.2f %s\n", "Average waiting time for SJF: ", totalWaitingTime / getProcesses().size(),"ms");
    }

    public void rrSimulation(int quantumTime) {

    }

}
