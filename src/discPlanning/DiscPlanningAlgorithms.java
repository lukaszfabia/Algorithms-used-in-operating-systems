package discPlanning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiscPlanningAlgorithms {
    private final int DISC_SIZE = 256;
    private ArrayList<Task> priorityQueue;
    private ArrayList<Task> queue;

    public DiscPlanningAlgorithms(ArrayList<Task> queue, ArrayList<Task> priorityQueue) {
        this.priorityQueue = priorityQueue;
        this.queue = queue;
    }

    public int fcfsEdf() {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;
        int rejected = 0;
        List<Task> tasks = new ArrayList<>();
        List<Task> priorityTasks = new ArrayList<>();
        List<Task> waitingTasks = new ArrayList<>();
        List<Task> waitingPriorityTasks = new ArrayList<>();

        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }

        while (currentTime != 100000) {

            for (Task task : tasks) {
                if (currentTime == task.getArrivalTime()) {
                    waitingTasks.add(task);
                    System.out.println("Waiting tasks: " + waitingTasks);
                }
            }

            for (Task priorityTask : priorityTasks) {
                if (currentTime == priorityTask.getArrivalTime()) {
                    waitingPriorityTasks.add(priorityTask);
                    System.out.println("Priority waiting tasks: " + waitingPriorityTasks);
                }
            }

            if (!waitingPriorityTasks.isEmpty()) {
                System.out.println("Priority: " + waitingPriorityTasks);
                if (waitingPriorityTasks.get(0).getCylinderNumber() == currentBlock) {
                    System.out.println("Tasks done: " + waitingPriorityTasks.get(0));
                    waitingPriorityTasks.remove(0);
                }

                waitingPriorityTasks.sort(Comparator.comparing(Task::getDeadline));

                if (waitingPriorityTasks.size() != 0 && currentBlock == waitingPriorityTasks.get(0).getCylinderNumber()) {
                    if (waitingPriorityTasks.get(0).getDeadline() < Math.abs(waitingPriorityTasks.get(0).getCylinderNumber() - currentBlock)) {
                        System.out.println("rejection");
                        rejected++;
                    }
                }

                if (waitingPriorityTasks.size() != 0) {
                    if (waitingPriorityTasks.get(0).getCylinderNumber() > currentBlock) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    blocks++;
                    for (Task waitingPriorityTask : waitingPriorityTasks) {
                        waitingPriorityTask.setDeadline(waitingPriorityTask.getDeadline() - 1);
                    }
                }
            }
            // rozpoczęcie drugiej listy z zadaniami bez deadline

            else if (!waitingTasks.isEmpty()) {
                System.out.println("Normal tasks:");
                if (currentBlock == waitingTasks.get(0).getCylinderNumber()) {
                    System.out.println("Normal task has executed: " + waitingTasks.get(0));
                    waitingTasks.remove(0);
                }

                if (waitingTasks.size() != 0) {
                    waitingTasks.sort(Comparator.comparing(Task::getArrivalTime));
                    if (waitingTasks.get(0).getCylinderNumber() > currentBlock) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    blocks++;
                }
            }
            currentTime++;

            if (waitingPriorityTasks.size() != 0 || waitingTasks.size() != 0) {
                System.out.println("Current time: " + currentTime);
                System.out.println("Current block: " + currentBlock);
                System.out.println("Blocks: " + blocks);
            }
        }
        return blocks;
    }

    public int fcfsFdScan() {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;
        List<Task> tasks = new ArrayList<>();
        List<Task> priorityTasks = new ArrayList<>();
        List<Task> waitingTasks = new ArrayList<>();
        List<Task> waitingPriorityTasks = new ArrayList<>();
        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }

        while (currentTime != 100000) {
            for (Task task : tasks) {
                if (currentTime == task.getArrivalTime()) {
                    waitingTasks.add(task);
                    System.out.println("Waiting tasks: " + waitingTasks);
                }
            }

            for (Task priorityTask : priorityTasks) {
                if (currentTime == priorityTask.getArrivalTime()) {
                    waitingPriorityTasks.add(priorityTask);
                    System.out.println("Priority waiting tasks: " + waitingPriorityTasks);
                }
            }

            if (!waitingPriorityTasks.isEmpty()) {
                for (Task waitingPriorityTask : waitingPriorityTasks) {
                    waitingPriorityTask.setDeadline(waitingPriorityTask.getDeadline() - 1);

                    if (currentBlock == waitingPriorityTask.getCylinderNumber()) {
                        System.out.println("Removed: " + waitingPriorityTask);
                        waitingPriorityTasks.remove(waitingPriorityTask);
                    }
                }

                if (waitingPriorityTasks.size() > 0) {
                    if (waitingPriorityTasks.get(0).getCylinderNumber() > currentBlock) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    blocks++;
                }
            } else if (!waitingTasks.isEmpty()) {
                System.out.println("Normal tasks:");
                if (currentBlock == waitingTasks.get(0).getCylinderNumber()) {
                    System.out.println("Normal task has executed: " + waitingTasks.get(0));
                    waitingTasks.remove(0);
                }
                if (waitingTasks.size() != 0) {
                    waitingTasks.sort(Comparator.comparing(Task::getArrivalTime));
                    if (waitingTasks.get(0).getCylinderNumber() > currentBlock) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    blocks++;
                }
            }
            currentTime++;

            if (waitingPriorityTasks.size() != 0 || waitingTasks.size() != 0) {
                System.out.println("Current time: " + currentTime);
                System.out.println("Current block: " + currentBlock);
                System.out.println("Blocks: " + blocks);
            }
        }
        return blocks;
    }

    public int sstfEdf() {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;
        int rejected = 0;
        List<Task> tasks = new ArrayList<>();
        List<Task> priorityTasks = new ArrayList<>();
        ArrayList<Task> waitingTasks = new ArrayList<>();
        List<Task> waitingPriorityTasks = new ArrayList<>();
        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }

        while (currentTime != 100000) {
            for (Task task : tasks) {
                if (currentTime == task.getArrivalTime()) {
                    waitingTasks.add(task);
                    System.out.println("Waiting tasks: " + waitingTasks);
                }
            }

            for (Task priorityTask : priorityTasks) {
                if (currentTime == priorityTask.getArrivalTime()) {
                    waitingPriorityTasks.add(priorityTask);
                    System.out.println("Priority waiting tasks: " + waitingPriorityTasks);
                }
            }

            if (!priorityTasks.isEmpty()) {
                if (waitingPriorityTasks.get(0).getCylinderNumber() == currentBlock) {
                    System.out.println("Priority task has executed: " + waitingPriorityTasks.get(0));
                    waitingPriorityTasks.remove(0);
                }

                waitingPriorityTasks.sort(Comparator.comparing(Task::getDeadline));

                if (waitingPriorityTasks.size() != 0 && currentBlock == waitingPriorityTasks.get(0).getCylinderNumber()) {
                    if (waitingPriorityTasks.get(0).getDeadline() < Math.abs(waitingPriorityTasks.get(0).getCylinderNumber() - currentBlock)) {
                        System.out.println("Rejection");
                        rejected++;
                    }
                }

                if (waitingPriorityTasks.size() != 0) {
                    if (waitingPriorityTasks.get(0).getCylinderNumber() > currentBlock) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    blocks++;

                    for (Task waitingPriorityTask : waitingPriorityTasks) {
                        waitingPriorityTask.setDeadline(waitingPriorityTask.getDeadline() - 1);
                    }
                }
            } else if (waitingTasks.size() != 0) {
                System.out.println("Normal tasks: " + waitingTasks);

                if (currentBlock == waitingTasks.get(0).getCylinderNumber()) {
                    System.out.println("Normal task has executed: " + waitingTasks.get(0));
                    waitingTasks.remove(0);
                }

                if (waitingTasks.size() != 0) {
                    Task.compareWithOtherBlock(currentBlock, waitingTasks);
                    if (waitingTasks.get(0).getCylinderNumber() > currentBlock) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    blocks++;
                }
            }

            currentTime++;

            if (waitingPriorityTasks.size() != 0 || waitingTasks.size() != 0) {
                System.out.println("Current time: " + currentTime);
                System.out.println("Current block: " + currentBlock);
                System.out.println("Blocks: " + blocks);
            }
        }
        return blocks;
    }

    public int sstfFdScan() {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;
        int rejected = 0;
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<Task> priorityTasks = new ArrayList<>();
        ArrayList<Task> waitingTasks = new ArrayList<>();
        ArrayList<Task> waitingPriorityTasks = new ArrayList<>();
        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }

        while (currentTime != 100000) {
            for (Task task : tasks) {
                if (currentTime == task.getArrivalTime()) {
                    waitingTasks.add(task);
                    System.out.println("Waiting tasks: " + waitingTasks);
                }
            }

            for (Task priorityTask : priorityTasks) {
                if (currentTime == priorityTask.getArrivalTime()) {
                    waitingPriorityTasks.add(priorityTask);
                    System.out.println("Priority waiting tasks: " + waitingPriorityTasks);
                }
            }

            if (!waitingPriorityTasks.isEmpty()) {
                if (waitingPriorityTasks.get(0).getCylinderNumber() == currentBlock) {
                    System.out.println("Priority task has executed: " + waitingPriorityTasks.get(0));
                    waitingPriorityTasks.remove(0);
                }

                waitingPriorityTasks.sort(Comparator.comparing(Task::getDeadline));

                if (waitingPriorityTasks.size() != 0 && currentBlock == waitingPriorityTasks.get(0).getCylinderNumber()) {
                    if (waitingPriorityTasks.get(0).getDeadline() < Math.abs(waitingPriorityTasks.get(0).getCylinderNumber() - currentBlock)) {
                        System.out.println("Rejection");
                        rejected++;
                    }
                }
                if (!waitingPriorityTasks.isEmpty()) {
                    for (Task waitingPriorityTask : waitingPriorityTasks) {
                        waitingPriorityTask.setDeadline(waitingPriorityTask.getDeadline() - 1);

                        if (currentBlock == waitingPriorityTask.getCylinderNumber()) {
                            System.out.println("Removed: " + waitingPriorityTask);
                            waitingPriorityTasks.remove(waitingPriorityTask);
                        }
                    }
                    if (waitingPriorityTasks.size() > 0) {
                        if (waitingPriorityTasks.get(0).getCylinderNumber() > currentBlock) {
                            currentBlock++;
                        }
                        else {
                            currentBlock--;
                        }
                        blocks++;
                    }
                }
            } else if (waitingTasks.size() != 0) {
                if (currentBlock == waitingTasks.get(0).getCylinderNumber()) {
                    System.out.println("Normal task has executed: " + waitingTasks.get(0));
                    waitingTasks.remove(0);
                }
                if (waitingTasks.size() != 0) {
                    Task.compareWithOtherBlock(currentBlock, waitingTasks);
                    if (waitingTasks.get(0).getCylinderNumber() > currentBlock) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    blocks++;
                }

            }
            currentTime++;
            if (waitingPriorityTasks.size() != 0 || waitingTasks.size() != 0) {
                System.out.println("Current time: " + currentTime);
                System.out.println("Current block: " + currentBlock);
                System.out.println("Blocks: " + blocks);
            }
        }
        return blocks;
    }

    public int scanEdf(){
        return 1;
    }
}
