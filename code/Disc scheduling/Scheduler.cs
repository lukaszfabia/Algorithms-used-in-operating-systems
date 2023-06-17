class Scheduler : IStrategy
{
    public Fcfs(List<Task> tasks)
    {
        tasks.Sort();
        int head_position = 0;
        int time=0;
        List<Task> completed_tasks = new();
        List<int> distances = new();
        int total_distance = 0;

        while (tasks.Count>0)
        {
            if (tasks[0].GetArrivalTime() <= time)
            {
                Task current_task = tasks[0];
                time += Math.Abs(head_position - current_task.GetPosition());
                total_distance += Math.Abs(head_position - current_task.GetPosition());
                distances.Add(Math.Abs(head_position - current_task.GetPosition()));
                completed_tasks.Add(current_task);
                head_position = current_task.GetPosition();
                tasks.Remove(current_task);
            }
            else
            {
                time++;
            }
        }

        Console.WriteLine("Przebyty dystans: "+$"FCFS: {total_distance}");

        Console.WriteLine("Srednie wychylenie glowicy dysku: "+$"FCFS: {GetTotalDistance(distances)}");
        
    }
    public void Sstf(List<Task> tasks)
    {
        // Shortest Seek Time First
    }
    public void Scan(List<Task> tasks)
    {
        // Scan
    }
    public void CScan(List<Task> tasks)
    {
        // Circular Scan
    }
    public void Edf(List<Task> tasks)
    {
        // Earliest Deadline First
    }
    public void FdScan(List<Task> tasks)
    {
        // FD-Scan
    }

    private static double GetTotalDistance(List<int> distances)
    {
        int total_distance = 0;
        foreach (int distance in distances)
        {
            total_distance += distance;
        }

        return total_distance/distances.Count;
    }
}