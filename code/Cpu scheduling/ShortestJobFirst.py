def find_shortest_job(queue):
    if not queue:
        return None

    min_phase_length = queue[0].phase_length
    index_of_shortest_job = 0
    for i in range(1, len(queue)):
        tmp = queue[i].phase_length
        if min_phase_length > tmp:
            min_phase_length = tmp
            index_of_shortest_job = i

    return queue[index_of_shortest_job]


class ShortestJobFirst:
    def __init__(self, processes):
        self.processes = processes

    def calculate(self):
        self.processes.sort(key=lambda process: process.arrival_time)
        total_waiting_time = 0
        processes_amount = len(self.processes)
        done = 0
        time = 0
        i = 0
        queue = []

        if processes_amount == 0:
            return 0

        while done < processes_amount:
            while i < processes_amount and self.processes[i].arrival_time <= time:
                queue.append(self.processes[i])
                i += 1

            curr = find_shortest_job(queue)

            if curr is None:
                time += 1
            else:
                runtime = curr.phase_length
                for _ in range(runtime):
                    time += 1
                queue.remove(curr)
                done += 1
                total_waiting_time += (time - curr.arrival_time - curr.phase_length)

        return total_waiting_time

    def schedule(self):
        average_waiting_time = self.calculate() / len(self.processes)
        print("Shortest job first:", average_waiting_time)
