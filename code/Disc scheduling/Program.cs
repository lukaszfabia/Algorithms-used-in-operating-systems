namespace app;
using System;
class Program
{
    static void Main(string[] args)
    {
        Task task = new(3, 5, 7);
        Task task1 = new(31, 5, 7);
        Task task2 = new(1, 5, 7);
        Task task3 = new(0, 5, 7);
        List<Task> tasks = new()
        {
            task,
            task1,
            task2,
            task3
        };

        tasks.Sort((task, task1) => task.GetArrivalTime().CompareTo(task1.GetArrivalTime()));
        foreach (Task t in tasks)
        {
            Console.WriteLine(t);
        }

    }
}
