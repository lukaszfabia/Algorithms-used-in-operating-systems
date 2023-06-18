namespace app;
class Program
{
    static void Main(string[] args)
    {
        List<Task> tasks = GetTasks(100, 256, 100, 100);
        List<Task> for_fcfs = tasks.ToList();
        List<Task> for_sstf = tasks.ToList();
        List<Task> for_scan = tasks.ToList();
        List<Task> for_cscan = tasks.ToList();
        List<Task> for_edf = tasks.ToList();
        List<Task> for_fdscan = tasks.ToList();

        Scheduler.Fcfs(for_fcfs);
        Console.WriteLine("--------------------------");    
        Scheduler.Sstf(for_sstf);
        Console.WriteLine("--------------------------");
        Scheduler.Scan(for_scan);
        Console.WriteLine("--------------------------");
        Scheduler.CScan(for_cscan);
        Console.WriteLine("--------------------------");
        Scheduler.Edf(for_edf);
        Console.WriteLine("--------------------------");
        Scheduler.FdScan(for_fdscan);
        Console.WriteLine("--------------------------");

        Console.ReadKey();
    }

    static List<Task> GetTasks(int maxDeadline, int maxPosition, int maxArrivalTime, int numberOfTasks)
    {
        List<Task> tasks = new();
        Random random = new();
        for (int i = 0; i < numberOfTasks; i++)
        {
            int arrivalTime = random.Next(0, maxArrivalTime);
            int position = random.Next(0, maxPosition);
            int deadline = random.Next(0, maxDeadline);
            tasks.Add(new Task(arrivalTime, position, deadline));
        }

        return tasks;
    }

    class Scheduler
    {
        private static int Disc_size = 256;
        public static void Fcfs(List<Task> tasks)
        {
            int head_position = 0;
            int time = 0;
            List<Task> completed_tasks = new();
            List<int> distances = new();
            int total_distance = 0;

            while (tasks.Count > 0)
            {
                if (tasks[0].GetArrivalTime() <= time)
                {
                    Task current_task = tasks[0];
                    time += Math.Abs(head_position - current_task.GetPosition());
                    total_distance += Math.Abs(head_position - current_task.GetPosition());
                    distances.Add(Math.Abs(head_position - current_task.GetPosition()));
                    completed_tasks.Add(current_task);
                    head_position = current_task.GetPosition();
                    tasks.Remove(tasks[0]);
                }
                else
                {
                    time++;
                }
            }

            Console.WriteLine("Przebyty dystans: " + $"FCFS: {total_distance}");

            Console.WriteLine("Srednie wychylenie glowicy dysku: " + $"FCFS: {GetAverageDistance(distances)}");
        }

        public static void Sstf(List<Task> tasks)
        {
            int time = 0;
            int head_position = 0;
            List<Task> completed_tasks = new List<Task>();
            List<int> distances = new List<int>();
            List<Task> uncompleted_tasks = new List<Task>();
            int total_distance = 0;

            while (tasks.Count > 0 || uncompleted_tasks.Count > 0)
            {
                foreach (Task task in tasks.ToList())
                {
                    if (task.GetArrivalTime() <= time)
                    {
                        uncompleted_tasks.Add(task);
                        tasks.Remove(task);
                    }
                }

                if (uncompleted_tasks.Count == 0)
                {
                    time++;
                }
                else
                {
                    Task nearest = uncompleted_tasks[0];
                    int minDistance = Math.Abs(head_position - nearest.GetPosition());

                    foreach (Task task in uncompleted_tasks)
                    {
                        int distance = Math.Abs(head_position - task.GetPosition());

                        if (distance < minDistance)
                        {
                            nearest = task;
                            minDistance = distance;
                        }
                    }

                    int dist = Math.Abs(head_position - nearest.GetPosition());
                    total_distance += dist;
                    time += dist;
                    distances.Add(dist);
                    completed_tasks.Add(nearest);
                    head_position = nearest.GetPosition();
                    uncompleted_tasks.Remove(nearest);
                }
            }

            Console.WriteLine("Przebyty dystans: " + $"SSTF: {total_distance}");
            Console.WriteLine("Średnie wychylenie głowicy dysku: " + $"SSTF: {GetAverageDistance(distances)}");
        }

        public static void Scan(List<Task> tasks)
        {
            int total_distance = 0;
            int direction = 1;
            tasks.Sort();
            int head_position = 0;
            Queue<Task> queue_of_tasks = new Queue<Task>();
            Queue<Task> waiting_tasks = new Queue<Task>();
            int size = tasks.Count;
            int amount_of_changes = 0;

            while (size > 0)
            {
                foreach (Task task in tasks.ToList())
                {
                    if (task.GetArrivalTime() <= total_distance)
                    {
                        if ((direction > 0 && task.GetPosition() >= head_position) || (direction < 0 && task.GetPosition() <= head_position))
                        {
                            queue_of_tasks.Enqueue(task);
                        }
                        if ((direction < 0 && task.GetPosition() > head_position) || (direction > 0 && task.GetPosition() < head_position))
                        {
                            waiting_tasks.Enqueue(task);
                        }
                        tasks.Remove(task);
                    }
                }

                if (queue_of_tasks.Count > 0)
                {
                    if (direction > 0)
                    {
                        queue_of_tasks = new Queue<Task>(queue_of_tasks.OrderBy(task => task.GetPosition()));
                    }
                    else
                    {
                        queue_of_tasks = new Queue<Task>(queue_of_tasks.OrderByDescending(task => task.GetPosition()));
                    }

                    while (queue_of_tasks.Count > 0 && queue_of_tasks.Peek().GetPosition() == head_position)
                    {
                        queue_of_tasks.Dequeue();
                        size--;
                    }
                }

                total_distance++;
                head_position += direction;

                if (head_position == Disc_size - 1 || head_position == 0)
                {
                    direction *= -1;
                    amount_of_changes++;

                    foreach (var task in waiting_tasks)
                    {
                        queue_of_tasks.Enqueue(task);
                    }

                    waiting_tasks = new Queue<Task>();
                }
            }

            Console.WriteLine("Przebyty dystans: " + $"SCAN: {total_distance}");
            Console.WriteLine("Ilość zmian kierunku: " + $"SCAN: {amount_of_changes}");
        }

        public static void CScan(List<Task> tasks)
        {
            int total_distance = 0;
            int head_position = 0;
            tasks.Sort();

            int turns = 0;
            Queue<Task> queue = new();
            List<Task> waitingQueue = new();

            int size = tasks.Count;

            while (size > 0)
            {
                foreach (var req in tasks.ToList())
                {
                    if (req.GetArrivalTime() <= total_distance)
                    {
                        if (req.GetPosition() >= head_position)
                        {
                            queue.Enqueue(req);
                        }
                        else
                        {
                            waitingQueue.Add(req);
                        }

                        tasks.Remove(req);
                    }
                }

                if (queue.Count > 0)
                {
                    var sortedQueue = queue.OrderBy(q => q.GetPosition()).ToList();
                    while (sortedQueue.Count > 0 && sortedQueue[0].GetPosition() == head_position)
                    {
                        sortedQueue.RemoveAt(0);
                        size--;
                    }
                }

                total_distance++;
                head_position++;
                if (head_position == Disc_size + 1)
                {
                    head_position = 0;
                    turns++;
                    foreach (var task in waitingQueue)
                    {
                        queue.Enqueue(task);
                    }

                    waitingQueue.Clear();
                }
            }

            Console.WriteLine("Przebyty dystans: " + $"CSCAN: {total_distance}");
            Console.WriteLine("Ilosc zmian kierunku: " + $"CSCAN: {turns}");
        }
        public static void Edf(List<Task> tasks)
        {
            int total_distance = 0;
            int head_position = 0;
            tasks.Sort();
            List<Task> completed_tasks = new List<Task>();
            List<int> distances = new List<int>();
            int time = 0;
            List<Task> uncompleted_tasks = new List<Task>(tasks);

            while (uncompleted_tasks.Count > 0)
            {
                var tasksToRemove = new List<Task>();

                foreach (var task in uncompleted_tasks)
                {
                    if (task.GetArrivalTime() <= time)
                    {
                        tasksToRemove.Add(task);
                        completed_tasks.Add(task);
                    }
                }

                foreach (var taskToRemove in tasksToRemove)
                {
                    uncompleted_tasks.Remove(taskToRemove);
                }

                if (uncompleted_tasks.Count == 0)
                {
                    time++;
                }
                else
                {
                    uncompleted_tasks.Sort((task1, task2) =>
                    {
                        int deadlineComparison = task1.GetDeadline().CompareTo(task2.GetDeadline());
                        if (deadlineComparison == 0)
                        {
                            return task1.GetArrivalTime().CompareTo(task2.GetArrivalTime());
                        }
                        return deadlineComparison;
                    });

                    var current = uncompleted_tasks[0];
                    total_distance += Math.Abs(current.GetPosition() - head_position);
                    time += Math.Abs(current.GetPosition() - head_position);
                    distances.Add(Math.Abs(current.GetPosition() - head_position));
                    head_position = current.GetPosition();
                    uncompleted_tasks.Remove(current);
                }
            }

            Console.WriteLine("Przebyty dystans: " + $"EDF: {total_distance}");
            Console.WriteLine("Srednie odchylenie glowicy dysku: " + $"EDF: {GetAverageDistance(distances)}");
        }

        public static void FdScan(List<Task> tasks)
        {
            int total_distance = 0;
            int head_position = 0;
            tasks.Sort();
            int direction = 1;

            Queue<Task> queue = new Queue<Task>();
            List<Task> waitingQueue = new List<Task>();
            int size = tasks.Count;
            int amount_of_changes = 0;

            while (size > 0)
            {
                var tasksToRemove = new List<Task>();

                foreach (var task in tasks)
                {
                    if (task.GetArrivalTime() <= total_distance)
                    {
                        bool thrown = false;

                        if (task.GetDeadline() != Disc_size + 1)
                        {
                            if (head_position > task.GetPosition())
                            {
                                amount_of_changes++;
                                direction = -1;
                            }
                            else
                            {
                                amount_of_changes++;
                                direction = 1;
                            }
                        }

                        if ((direction > 0 && task.GetPosition() >= head_position) || (direction < 0 && task.GetPosition() <= head_position))
                        {
                            queue.Enqueue(task);
                        }

                        if ((direction < 0 && task.GetPosition() > head_position) || (direction > 0 && task.GetPosition() < head_position))
                        {
                            waitingQueue.Add(task);
                        }

                        if (!thrown)
                        {
                            tasksToRemove.Add(task);
                        }
                    }
                }

                foreach (var taskToRemove in tasksToRemove)
                {
                    tasks.Remove(taskToRemove);
                }

                if (queue.Count > 0)
                {
                    if (direction > 0)
                    {
                        queue = new Queue<Task>(queue.OrderBy(q => (q.GetDeadline(), q.GetPosition())));
                    }
                    else
                    {
                        queue = new Queue<Task>(queue.OrderByDescending(q => (q.GetDeadline(), q.GetPosition())));
                    }

                    while (queue.Count > 0 && queue.Peek().GetPosition() == head_position)
                    {
                        queue.Dequeue();
                        size--;
                    }
                }

                total_distance++;
                head_position += direction;

                if (head_position == Disc_size || head_position == 0)
                {
                    direction *= -1;
                    amount_of_changes++;
                    foreach (var task in waitingQueue)
                    {
                        queue.Enqueue(task);
                    }

                    waitingQueue.Clear();
                }
            }

            Console.WriteLine("Przebyty dystans: " + $"FDSCAN: {total_distance}");
            Console.WriteLine("Ilosc zmian kierunku: " + $"FDSCAN: {amount_of_changes}");
        }


        private static double GetAverageDistance(List<int> distances)
        {
            int total_distance = 0;
            foreach (int distance in distances)
            {
                total_distance += distance;
            }

            return total_distance / distances.Count;
        }
    }
}
