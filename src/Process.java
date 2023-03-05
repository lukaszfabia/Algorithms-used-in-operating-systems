public class Process {
    private int executeTime;
    private int arrivalTime;
    private int remainingTime;
    private int waitingTime;
    private int startTime;
    private final int nr;

    public Process(int executeTime, int arrivalTime, int nr) {
        this.executeTime = executeTime;
        this.arrivalTime = arrivalTime;
        this.nr = nr;
        this.waitingTime=0;
        this.startTime=0;
        this.remainingTime =executeTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getExecuteTime() {
        return executeTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setExecuteTime(int executeTime) {
        this.executeTime = executeTime;
    }

    public void setRemainingTime(int waitingTime) {
        this.remainingTime = waitingTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getNr() {
        return nr;
    }
    public boolean isCompleted() {
        return remainingTime == 0;
    }

    public void execute() {
        remainingTime--;
    }

    @Override
    public String toString() {
        return String.format("%-20d %-20d %-20d",getNr(), getArrivalTime(),getExecuteTime());
    }

    public void incrementWaitingTime() {
        waitingTime++;
    }
}
