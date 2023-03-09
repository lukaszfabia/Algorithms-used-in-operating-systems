package cpuPlanning;

public class Process {
    private int executeTime;
    private int arrivalTime;
    private int remainingTime;
    private int waitingTime;
    private int nr;
    private int completionTime;

    public Process(int executeTime, int arrivalTime, int nr) {
        this.executeTime = executeTime;
        this.arrivalTime = arrivalTime;
        this.nr = nr;
        this.waitingTime = 0;
        this.remainingTime=executeTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime + arrivalTime;
    }

    public int getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(int executeTime) {
        this.executeTime = executeTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getNr() {
        return nr;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Override
    public String toString() {
        return String.format("%-20d %-20d %-20d", getNr(), getArrivalTime(), getExecuteTime());
    }
}
