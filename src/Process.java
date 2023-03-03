public class Process {
    private int executeTime;
    private int arrivalTime;
    private final int nr;

    public Process(int executeTime, int arrivalTime, int nr) {
        this.executeTime = executeTime;
        this.arrivalTime = arrivalTime;
        this.nr = nr;
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

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getNr() {
        return nr;
    }

    @Override
    public String toString() {
        return String.format("%-20d %-20d %-20d",getNr(), getArrivalTime(),getExecuteTime());
    }
}
