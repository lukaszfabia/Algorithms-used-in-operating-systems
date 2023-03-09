package cpuPlanning;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //http://users.pja.edu.pl/~sered/sop/wyklad5/w5.htm
        //https://mycareerwise.com/content/sjf-process-and-examples/content/exam/gate/computer-science
        List<Process> processList = new ArrayList<>();
        processList.add(new Process(10, 0, 1));
        processList.add(new Process(9, 1, 2));
        processList.add(new Process(12, 2, 3));
        processList.add(new Process(6, 3, 4));




        System.out.printf("%-20s %-20s %-20s\n","Number", "Arrival time", "Execute time");
        //System.out.printf("%-20s %-20s %-20s\n","P", "CP", "CTF");
        ProcessSimulator processSimulator = new ProcessSimulator(processList);
        processSimulator.showData();

        processSimulator.rrSimulation(8);
        System.out.println();
        processSimulator.fcfsSimulation();
        System.out.println();
        processSimulator.srtfSimulation();
        System.out.println();
        System.out.println();
        processSimulator.sjfSimulation();


    }
}