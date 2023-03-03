public class Main {
    public static void main(String[] args) {
        //System.out.printf("%-20s %-20s %-20s\n","Number", "Arrival time", "Execute time");
        System.out.printf("%-20s %-20s %-20s\n","P", "CP", "CTF");
        ProcessSimulator processSimulator = new ProcessSimulator(4);
        processSimulator.showData();
        /*processSimulator.fcfsSimulation();
        System.out.println();
        processSimulator.sjfSimulation();
        System.out.println();
         */

        processSimulator.rrSimulation(4);
    }
}