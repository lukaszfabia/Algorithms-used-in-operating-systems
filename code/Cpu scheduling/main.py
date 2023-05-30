import random
import copy

from FirstComeFirstServed import FirstComeFirstServed
from Process import Process
from RoundRobin import RoundRobin
from ShortestJobFirst import ShortestJobFirst
from ShortestRemainingFirst import ShortestRemainingFirst


def generate_processes(amount, max_phase_length, max_arrival_time):
    new_tasks = []
    for i in range(amount):
        random_arrival = random.randint(0, max_phase_length)
        random_phase = random.randint(0, max_arrival_time)
        new_tasks.append(Process(random_phase, random_arrival))
    return new_tasks


def quantum(list_with_tasks):
    phase_lengths = 0
    for process in list_with_tasks:
        phase_lengths += process.phase_length

    return phase_lengths / len(list_with_tasks)


tasks = generate_processes(40, 400, 400)

for_fcfs = copy.deepcopy(tasks)
for_sjf = copy.deepcopy(tasks)
for_rr = copy.deepcopy(tasks)
for_srtf = copy.deepcopy(tasks)

FirstComeFirstServed(for_fcfs).schedule()
ShortestJobFirst(for_sjf).schedule()
ShortestRemainingFirst(for_srtf).schedule()
RoundRobin(for_rr, quantum(for_rr)).schedule()
