package cpuPlanning;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //http://users.pja.edu.pl/~sered/sop/wyklad5/w5.htm
        //https://mycareerwise.com/content/sjf-process-and-examples/content/exam/gate/computer-science
        List<Process> processList = new ArrayList<>();
        processList.add(new Process(4, 0, 1));
        processList.add(new Process(3, 1, 2));
        processList.add(new Process(1, 2, 3));
        processList.add(new Process(2, 3, 4));
        processList.add(new Process(5, 4, 5));



        //System.out.printf("%-20s %-20s %-20s\n","P", "CP", "CTF");
        ProcessSimulator processSimulator = new ProcessSimulator(processList);
        processSimulator.showData();

        //processSimulator.rrSimulation(8);
        System.out.println();
        //processSimulator.fcfsSimulation();
        System.out.println();
        processSimulator.srtfSimulation();
        System.out.println();
        System.out.println();
        //processSimulator.sjfSimulation();


    }
}