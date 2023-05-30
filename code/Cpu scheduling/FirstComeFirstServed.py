class FirstComeFirstServed:
    def __init__(self, processes):
        self.processes = processes

    def count_waiting_time(self):
        waiting_time = 0
        burst_time = self.processes[0].phase_length
        processes_amount = len(self.processes)

        if processes_amount == 0:
            return

        self.processes[0].waiting_time = 0

        for i in range(1, processes_amount):
            self.processes[i].waiting_time = waiting_time + burst_time
            waiting_time = self.processes[i].waiting_time
            burst_time = self.processes[i].phase_length

    def calculate_turn_around_time(self):
        for process in self.processes:
            process.turn_around_time = process.waiting_time + process.phase_length

    def schedule(self):
        total_waiting_time = 0
        self.count_waiting_time()
        self.calculate_turn_around_time()

        for process in self.processes:
            total_waiting_time += process.waiting_time
        print("FCFS: ", round(total_waiting_time / len(self.processes), 2))
