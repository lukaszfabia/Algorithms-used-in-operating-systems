class Process:
    def __init__(self, phase_length, arrival_time):
        self.phase_length = phase_length
        self.arrival_time = arrival_time
        self.waiting_time = 0
        self.turn_around_time = 0
        self.remaining_time = phase_length

