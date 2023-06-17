interface IStrategy{
    void Fcfs(List<Task> tasks);
    void Sstf(List<Task> tasks);
    void Scan(List<Task> tasks);
    void CScan(List<Task> tasks);
    void Edf(List<Task> tasks);
    void FdScan(List<Task> tasks);
}