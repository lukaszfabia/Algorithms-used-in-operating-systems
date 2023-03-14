package discPlanning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Task {
    private int arrivalTime;
    private int deadline;
    private int done;
    private int cylinderNumber;

    public Task(int arrivalTime, int cylinderNumber){
        this.arrivalTime=arrivalTime;
        this.cylinderNumber=cylinderNumber;
    }

    public Task(int arrivalTime, int cylinderNumber, int deadline){
        this.arrivalTime=arrivalTime;
        this.cylinderNumber=cylinderNumber;
        this.deadline=deadline;
    }

    public Task(Task task){
        this.arrivalTime=task.arrivalTime;
        this.cylinderNumber=task.cylinderNumber;
        this.deadline=task.deadline;
        this.done= task.done;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getCylinderNumber() {
        return cylinderNumber;
    }

    public void setCylinderNumber(int cylinderNumber) {
        this.cylinderNumber = cylinderNumber;
    }

    @Override
    public String toString() {
        return String.format("%-5d %-5d %-5d\n", getArrivalTime(),getCylinderNumber(),getDeadline());
    }

    public static void compareWithOtherBlock(int currentBlock, ArrayList<Task> a){
        Comparator<Task> closestTaskComparator = Comparator.comparingInt(o -> Math.abs(o.cylinderNumber - currentBlock));
        a.sort(closestTaskComparator);
    }
}
