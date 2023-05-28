class Processor:
    def __init__(self):
        self.tasks = []
        self.ended_task = []

    def get_load_sum(self):
        load_sum = 0
        for task in self.tasks:
            load_sum += task.load
        return load_sum

    def update_execution_time(self):
        for task in self.tasks:
            task.execute_time -= 1
            if task.execute_time <= 0:
                self.ended_task.append(task)
                self.tasks.remove(task)
