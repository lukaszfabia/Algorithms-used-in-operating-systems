from queue import Queue


class RoundRobin:
    def __init__(self, processes, quantum):
        self.processes = processes
        self.quantum = quantum

    def calculate(self):
        processes_number = len(self.processes)

        if processes_number == 0:
            return

        queue = Queue()
        queue.put(0)
        current_time = 0
        completed = 0
        mark = [0] * processes_number
        mark[0] = 1

        remaining_burst_time = [process.phase_length for process in self.processes]

        while completed != processes_number:
            index = queue.get()

            if remaining_burst_time[index] == self.processes[index].phase_length:
                current_time = max(current_time, self.processes[index].arrival_time)

            if remaining_burst_time[index] - self.quantum > 0:
                remaining_burst_time[index] -= self.quantum
                current_time += self.quantum
            else:
                current_time += remaining_burst_time[index]
                self.processes[index].turn_around_time = (current_time - self.processes[index].arrival_time)
                completed += 1
                remaining_burst_time[index] = 0

            for i in range(1, processes_number):
                if remaining_burst_time[i] > 0 and self.processes[i].arrival_time <= current_time and mark[i] == 0:
                    mark[i] = 1
                    queue.put(i)

            if remaining_burst_time[index] > 0:
                queue.put(index)

            if not queue.empty():
                for i in range(1, processes_number):
                    if remaining_burst_time[i] > 0:
                        mark[i] = 1
                        queue.put(i)
                        break

    def calculate_waiting_time(self):
        for process in self.processes:
            process.waiting_time = process.turn_around_time - process.phase_length

    def schedule(self):
        self.calculate()
        self.calculate_waiting_time()
        total_waiting_time = 0
        for process in self.processes:
            total_waiting_time += process.waiting_time

        print("Round robin: ", round(total_waiting_time / len(self.processes), 2))
