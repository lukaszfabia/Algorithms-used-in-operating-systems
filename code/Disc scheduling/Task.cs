namespace app;

class Task
{
    private int arrivalTime;
    private int position;
    private int deadline;

    public Task(int arrivalTime, int position, int deadline)
    {
        this.arrivalTime = arrivalTime;
        this.position = position;
        this.deadline = deadline;
    }

    public int GetArrivalTime()
    {
        return arrivalTime;
    }

    public int GetPosition()
    {
        return position;
    }

    public int GetDeadline()
    {
        return deadline;
    }

    public void SetArrivalTime(int arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public void GetPosition(int position)
    {
        this.position = position;
    }

    public void SetDeadline(int deadline)
    {
        this.deadline = deadline;
    }

    public override string ToString()
    {
        return $"Task: arrivalTime={arrivalTime}, position={position}, deadline={deadline}";
    }

}
