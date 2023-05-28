from Processor import Processor
from Task import Task
from Strategy import *
import random
import copy


def generate_processors(processors_amount):
    cpu = []
    for i in range(processors_amount):
        cpu.append(Processor())
    return cpu


def generate_tasks(task_amount, processors_amount, max_execution_time, max_load):
    new_tasks = []
    for i in range(task_amount):
        cpu_id = random.randint(0, processors_amount - 1)
        arrival_time = random.randint(0, task_amount - 1)
        execution_time = random.randint(0, max_execution_time)
        load = random.randint(0, max_load)
        new_tasks.append(Task(cpu_id, arrival_time, execution_time, load))
    new_tasks.sort(key=lambda task: task.arrival_time)
    return new_tasks


z = 4
r = 30
p = 70

tasks_amount = 100000
cpu_amount = 80
max_execution_time = 300
max_load = 50

processors = generate_processors(cpu_amount)
tasks = generate_tasks(tasks_amount, cpu_amount, max_execution_time, max_load)

task_list1 = copy.deepcopy(tasks)
task_list2 = copy.deepcopy(tasks)
task_list3 = copy.deepcopy(tasks)

processor1 = copy.deepcopy(processors)
processor2 = copy.deepcopy(processors)
processor3 = copy.deepcopy(processors)

strategy_1(processor1, task_list1, p, z)
strategy_2(processor2, task_list2, p)
strategy_3(processor3, task_list3, p, r)
