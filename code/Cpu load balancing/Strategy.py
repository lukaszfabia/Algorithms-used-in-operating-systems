import random
from statistics import mean
import matplotlib.pyplot as plt


def strategy_1(processors, tasks, p, z):
    load_time = 0  # czas obiciazenia
    avg_loads = []
    asks_for_loads = 0  # zapytania dot. obciazenia
    time = 0
    migrations = 0  # oddanie taska innemu cpu

    while tasks:
        # zachowanie jak kolejka bo taski sa ulozone w kolejnosci przybycia wiec zachowanie jak queue
        if tasks[0].arrival_time <= time:
            curr_task = tasks[0]
            curr_processor = processors[curr_task.id]
            # szukamy z razy
            for i in range(z):
                rand = random.randint(0, len(processors) - 1)
                asks_for_loads += 1
                # pytamy o obciazenie < progu p
                if processors[rand].get_load_sum() < p:
                    curr_processor = processors[rand]
                    migrations += 1
                    break
            curr_processor.tasks.append(curr_task)
            tasks.remove(curr_task)

        time += 1

        if time % 10 == 0:
            loads = []
            for processor in processors:
                loads.append(processor.get_load_sum())
                if processor.get_load_sum() > 100:
                    load_time += 1
            avg_loads.append(mean(loads))

        for processor in processors:
            processor.update_execution_time()

    count_stats(avg_loads, asks_for_loads, migrations, load_time, "Strategia 1", time)


def strategy_2(processors, tasks, p):
    load_time = 0
    avg_loads = []
    asks_for_loads = 0
    time = 0
    migrations = 0

    while tasks:
        if tasks[0].arrival_time <= time:
            curr_task = tasks[0]
            curr_processor = processors[curr_task.id]
            asks_for_loads += 1
            if curr_processor.get_load_sum() > p:
                free_processors = []
                for processor in processors:
                    asks_for_loads += 1
                    # szukamy cpu ktore bedzie moglo przejac taska
                    if processor.get_load_sum() < p:
                        free_processors.append(processor)
                if free_processors:
                    # oddajemy taska do losowego cpu
                    rand = random.randint(0, len(free_processors) - 1)
                    curr_processor = free_processors[rand]
                    migrations += 1
            curr_processor.tasks.append(curr_task)
            tasks.remove(curr_task)

        time += 1

        if time % 10 == 0:
            loads = []
            for processor in processors:
                loads.append(processor.get_load_sum())
                if processor.get_load_sum() > 100:
                    load_time += 1
            avg_loads.append(mean(loads))

        for processor in processors:
            processor.update_execution_time()

    count_stats(avg_loads, asks_for_loads, migrations, load_time, "Strategia 2", time)


def strategy_3(processors, tasks, p, r):
    load_time = 0
    avg_loads = []
    asks_for_loads = 0
    time = 0
    migrations = 0

    while tasks:
        above_p = []  # powyzej progu p
        below_r = []  # ponizej progu r
        for processor in processors:
            asks_for_loads += 1
            if processor.get_load_sum() < r:
                below_r.append(processor)
            if processor.get_load_sum() > p:
                above_p.append(processor)
        if below_r and above_p:
            for processor in below_r:
                # bierzemy z listy above p jakis cpu
                temp = random.choice(above_p)
                if temp.tasks:
                    # wybieramy losowe zadanie tego cpu
                    task = random.choice(temp.tasks)
                    # o usuwamy je z wybranego cpu
                    temp.tasks.remove(task)
                    # i dajemy tego taska cpu ktory ma cpu dobrze radzace sobie
                    processor.tasks.append(task)
                    migrations += 1
        # tak jak w strategii 2
        if tasks[0].arrival_time <= time:
            curr_task = tasks[0]
            curr_processor = processors[curr_task.id]
            asks_for_loads += 1
            if curr_processor.get_load_sum() > p:
                free_processors = []
                for processor in processors:
                    if processor.get_load_sum() < p:
                        free_processors.append(processor)
                if free_processors:
                    rand = random.randint(0, len(free_processors) - 1)
                    curr_processor = free_processors[rand]
                    migrations += 1
            curr_processor.tasks.append(curr_task)
            tasks.remove(curr_task)

        time += 1

        if time % 10 == 0:
            loads = []
            for processor in processors:
                loads.append(processor.get_load_sum())
                if processor.get_load_sum() > 100:
                    load_time += 1
            avg_loads.append(mean(loads))

        for processor in processors:
            processor.update_execution_time()

    count_stats(avg_loads, asks_for_loads, migrations, load_time, "Strategia 3", time)


def count_stats(avg_loads, asks_for_loads, migrations, load_time, strategy_name, time):
    avg = mean(avg_loads)
    dev = []

    for load in avg_loads:
        dev.append(abs(avg - load))

    print(strategy_name)
    print("Srednie obciazenie: ", round(avg, 3))
    print("Odchylenie: ", round((mean(dev)), 3))
    print("Zapytania o obciazenie: ", asks_for_loads)
    print("Liczba migracji: ", migrations)
    print("Czas przeciazenia: ", load_time)
    print("Czas: ", time, "\n")

    plot_stats(avg_loads, strategy_name)


def plot_stats(avg_loads, strategy_name):
    x = [i * 10 for i in range(1, len(avg_loads) + 1)]  # max wart czasu
    y = avg_loads

    fig, ax = plt.subplots()
    ax.plot(x, y)
    ax.grid(True)
    ax.set_xlabel('Czas')
    ax.set_ylabel('Średnie obciążenie')
    ax.set_title('Zmiany średniego obciążenia w czasie', fontweight='bold')
    ax.legend([strategy_name])
    plt.xticks(fontsize=10)
    plt.yticks(fontsize=10)
    plt.show()
