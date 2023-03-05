import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Process> processList = new ArrayList<>();
        processList.add(new Process(3, 0, 1));
        processList.add(new Process(4, 1, 2));
        processList.add(new Process(7, 1, 3));
        processList.add(new Process(2, 4, 3));


        //System.out.printf("%-20s %-20s %-20s\n","Number", "Arrival time", "Execute time");
        System.out.printf("%-20s %-20s %-20s\n","P", "CP", "CTF");
        ProcessSimulator processSimulator = new ProcessSimulator(processList);
        processSimulator.showData();

        System.out.println();
        processSimulator.srtfSimulation();
        System.out.println();

    }
}