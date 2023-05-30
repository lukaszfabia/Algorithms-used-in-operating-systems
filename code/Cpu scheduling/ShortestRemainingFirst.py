class ShortestRemainingFirst:
    def __init__(self, processes):
        self.processes = processes

    def calculate(self):
        size = len(self.processes)
        self.processes.sort(key=lambda process_to_sort: process_to_sort.arrival_time)
        queue = []
        done = 0
        time = 0
        while done != size:
            for process in self.processes:
                if time == process.arrival_time:
                    queue.append(process)

            if not queue:
                time += 1
                continue

            queue.sort(key=lambda process_to_sort: process_to_sort.remaining_time)
            curr = queue[0]
            curr.remaining_time -= 1
            for i in range(1, len(queue)):
                queue[i].waiting_time += 1

            if curr.remaining_time == 0:
                done += 1
                queue.pop(0)

            time += 1

            for process in self.processes:
                process.turn_around_time = process.waiting_time + process.phase_length

    def schedule(self):
        self.calculate()
        total_time = 0
        for process in self.processes:
            total_time += process.waiting_time
        print("Shortest Remaining Time First:", round(total_time / len(self.processes), 2))
