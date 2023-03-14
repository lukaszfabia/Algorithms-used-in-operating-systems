package discPlanning;

import com.sun.source.doctree.SummaryTree;

import java.util.*;

public class Results {
    private int amountTasks;
    private int priority;
    private int cycles;

    public Results(int amountTasks, int priority, int cycles) {
        this.amountTasks = amountTasks;
        this.priority = priority;
        this.cycles = cycles;
    }

    public int getAmountTasks() {
        return amountTasks;
    }

    public int getPriority() {
        return priority;
    }

    public int getCycles() {
        return cycles;
    }

    private Map<String, Integer> generate(){
        Random r=new Random();
        ArrayList<Task> randomTasks=new ArrayList<>();
        ArrayList<Task> randomPriorityTasks=new ArrayList<>();
        Map<String, Integer> resultMap=new HashMap<>();
        int [] sumsOfTurnAround =new int[8];
        String [] algorithmsNames = {"FCFS EDF", "FCFS FD-SCAN", "SSTF EDF", "SSTF FD-SCAN", "SCAN EDF", "SCAN FD-SCAN","C-SCAN EDF", "C-SCAN FD-SCAN"};
        for (int i = 0; i < getCycles(); i++) {
            for (int j = 0; j <getAmountTasks()*(100-getPriority())/100 ; j++) {
                randomTasks.add(new Task(r.nextInt(500)+1, r.nextInt(200)+1));
            }

            for (int j = 0; j <getAmountTasks()*getPriority()/100 ; j++) {
                randomPriorityTasks.add(new Task(r.nextInt(500)+1, r.nextInt(200)+1, r.nextInt(100)+50));
            }

            DiscPlanningAlgorithms ds = new DiscPlanningAlgorithms(randomTasks, randomPriorityTasks);
            /*
            sumsOfTurnAround[0]+=
            sumsOfTurnAround[1]+=
            sumsOfTurnAround[2]+=
            sumsOfTurnAround[3]+=
            sumsOfTurnAround[4]+=
            sumsOfTurnAround[5]+=
            sumsOfTurnAround[6]+=
            sumsOfTurnAround[7]+=

             */
        }

        for (int i : sumsOfTurnAround) {
            resultMap.put(algorithmsNames[i], sumsOfTurnAround[i]);
        }

        return resultMap;
    }

    public String results(Map<String, Integer> restultMap){
        StringBuilder sb = new StringBuilder();
        sb.append("Results:");
        for (Map.Entry<String, Integer> string : restultMap.entrySet()) {
            sb.append("\n").append(String.format("%-5s, %-5d", string.getKey(), string.getValue()));
        }
        return sb.toString();
    }
}
