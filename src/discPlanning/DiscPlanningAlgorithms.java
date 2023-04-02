package discPlanning;

import java.util.ArrayList;
import java.util.Comparator;
public class DiscPlanningAlgorithms {

    private final int DISC_SIZE = 256;
    private final ArrayList<Task> priorityQueue;
    private final ArrayList<Task> queue;

    public DiscPlanningAlgorithms(ArrayList<Task> queue, ArrayList<Task> priorityQueue) {
        this.priorityQueue = priorityQueue;
        this.queue = queue;
    }


    public int fcfsEdf() {
        int blocks = 0;//ilosc ruchów glowicy dysku
        int currentBlock = 0; // obecny nr ruchu glowicy
        int currentTime = 0;
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<Task> priorityTasks = new ArrayList<>();
        ArrayList<Task> waitingTasks = new ArrayList<>();
        ArrayList<Task> waitingPriorityTasks = new ArrayList<>();

        //kopiujemy taski do pomocniczych list
        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }

        while (currentTime != 100000) {

            //linia czasowa sie przesuwa i w ten sposob za pomoca helpMethod dodajemy zadania to list zgodnie z czasem przybycia
            helpMethod(currentTime, tasks, priorityTasks, waitingTasks, waitingPriorityTasks);

            // w pierwszej kolejnosci sa wykonywane zadanie real time czyli priorytetowe
            if (waitingPriorityTasks.size() != 0) {
                if (waitingPriorityTasks.get(0).getCylinderNumber() == currentBlock) {
                    waitingPriorityTasks.remove(0);
                }

                waitingTasks.sort(Comparator.comparing(Task::getDeadline));

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
                // tutaj wykonujemy reszte zdan bez deadline'u
            } else if (waitingTasks.size() != 0) {
                if (currentBlock == waitingTasks.get(0).getCylinderNumber()) {
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
        }
        return blocks;
    }

    public int fcfsFdScan() {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;

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

            helpMethod(currentTime, tasks, priorityTasks, waitingTasks, waitingPriorityTasks);
            if (waitingPriorityTasks.size() != 0) {
                for (int i = 0; i < waitingPriorityTasks.size(); i++) {
                    waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).getDeadline() - 1);
                    if (currentBlock == waitingPriorityTasks.get(i).getCylinderNumber()) {
                        waitingPriorityTasks.remove(i);
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
            } else if (waitingTasks.size() != 0) {
                if (currentBlock == waitingTasks.get(0).getCylinderNumber()) {
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
        }
        return blocks;
    }

    private void helpMethod(int currentTime, ArrayList<Task> tasks, ArrayList<Task> priorityTasks, ArrayList<Task> waitingTasks, ArrayList<Task> waitingPriorityTasks) {
        for (Task task : tasks) {
            if (currentTime == task.getArrivalTime()) {
                waitingTasks.add(task);
            }
        }
        for (Task priorityTask : priorityTasks) {
            if (currentTime == priorityTask.getArrivalTime()) {
                waitingPriorityTasks.add(priorityTask);
            }
        }
    }

    public int sstfEdf() {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;

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

            helpMethod(currentTime, tasks, priorityTasks, waitingTasks, waitingPriorityTasks);

            if (waitingPriorityTasks.size() != 0) {
                if (waitingPriorityTasks.get(0).getCylinderNumber() == currentBlock) {
                    waitingPriorityTasks.remove(0);
                }

                waitingPriorityTasks.sort(Comparator.comparing(Task::getDeadline));

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
                if (currentBlock == waitingTasks.get(0).getCylinderNumber()) {
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
        }
        return blocks;
    }

    public int sstfFdScan() {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;

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

            helpMethod(currentTime, tasks, priorityTasks, waitingTasks, waitingPriorityTasks);

            if (waitingPriorityTasks.size() != 0) {
                if (waitingPriorityTasks.get(0).getCylinderNumber() == currentBlock) {
                    waitingPriorityTasks.remove(0);
                }

                waitingPriorityTasks.sort(Comparator.comparing(Task::getDeadline));


                if (waitingPriorityTasks.size() != 0) {
                    for (int i = 0; i < waitingPriorityTasks.size(); i++) {
                        waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).getDeadline() - 1);
                        if (currentBlock == waitingPriorityTasks.get(i).getCylinderNumber()) {
                            waitingPriorityTasks.remove(i);
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
                }
            } else if (waitingTasks.size() != 0) {
                if (currentBlock == waitingTasks.get(0).getCylinderNumber()) {
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
        }
        return blocks;
    }

    public int scanEdf() {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;
        boolean forwards = true;

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

            helpMethod(currentTime, tasks, priorityTasks, waitingTasks, waitingPriorityTasks);
            if (waitingPriorityTasks.size() != 0) {

                if (waitingPriorityTasks.get(0).getCylinderNumber() == currentBlock) {
                    waitingPriorityTasks.remove(0);
                }

                waitingPriorityTasks.sort(Comparator.comparing(Task::getDeadline));

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
                for (int i = 0; i < waitingTasks.size(); i++) {
                    if (currentBlock == waitingTasks.get(i).getCylinderNumber()) {
                        waitingTasks.remove(i);
                    }
                }
                if (waitingTasks.size() != 0) {
                    if (forwards) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    if (currentBlock == DISC_SIZE) {
                        forwards = false;
                    }
                    if (currentBlock == 0) {
                        forwards = true;
                    }
                    blocks++;
                }
            }
            currentTime++;
        }
        return blocks;
    }

    public int scanFdScan() {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;
        boolean forwards = true;

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

            helpMethod(currentTime, tasks, priorityTasks, waitingTasks, waitingPriorityTasks);

            if (waitingPriorityTasks.size() != 0) {
                if (waitingPriorityTasks.get(0).getCylinderNumber() == currentBlock) {
                    waitingPriorityTasks.remove(0);
                }

                waitingPriorityTasks.sort(Comparator.comparing(Task::getDeadline));

                if (waitingPriorityTasks.size() != 0) {
                    for (int i = 0; i < waitingPriorityTasks.size(); i++) {
                        waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).getDeadline() - 1);
                        if (currentBlock == waitingPriorityTasks.get(i).getCylinderNumber()) {
                            waitingPriorityTasks.remove(i);
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
                }
            } else if (waitingTasks.size() != 0) {
                for (int i = 0; i < waitingTasks.size(); i++) {
                    if (currentBlock == waitingTasks.get(i).getCylinderNumber()) {
                        waitingTasks.remove(i);
                    }
                }
                if (waitingTasks.size() != 0) {
                    if (forwards) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    if (currentBlock == DISC_SIZE) {
                        forwards = false;
                    }
                    if (currentBlock == 0) {
                        forwards = true;
                    }
                    blocks++;
                }
            }
            currentTime++;
        }
        return blocks;
    }

    public int cScanEdf() {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;

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

            helpMethod(currentTime, tasks, priorityTasks, waitingTasks, waitingPriorityTasks);
            if (waitingPriorityTasks.size() != 0) {
                if (waitingPriorityTasks.get(0).getCylinderNumber() == currentBlock) {
                    waitingPriorityTasks.remove(0);
                }

                waitingPriorityTasks.sort(Comparator.comparing(Task::getDeadline));

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
                for (int i = 0; i < waitingTasks.size(); i++) {
                    if (currentBlock == waitingTasks.get(i).getCylinderNumber()) {
                        waitingTasks.remove(i);
                    }
                }
                if (waitingTasks.size() != 0) {
                    if (currentBlock == DISC_SIZE) {
                        currentBlock = 0;
                    }
                    currentBlock++;
                    blocks++;
                }
            }
            currentTime++;
        }
        return blocks;

    }

    public int cScanFdScan() {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;

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
            helpMethod(currentTime, tasks, priorityTasks, waitingTasks, waitingPriorityTasks);
            if (waitingPriorityTasks.size() != 0) {
                if (waitingPriorityTasks.get(0).getCylinderNumber() == currentBlock) {
                    waitingPriorityTasks.remove(0);
                }

                waitingPriorityTasks.sort(Comparator.comparing(Task::getDeadline));

                if (waitingPriorityTasks.size() != 0) {
                    for (int i = 0; i < waitingPriorityTasks.size(); i++) {
                        waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).getDeadline() - 1);
                        if (currentBlock == waitingPriorityTasks.get(i).getCylinderNumber()) {
                            waitingPriorityTasks.remove(i);
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
                }
            } else if (waitingTasks.size() != 0) {
                for (int i = 0; i < waitingTasks.size(); i++) {
                    if (currentBlock == waitingTasks.get(i).getCylinderNumber()) {
                        waitingTasks.remove(i);
                    }
                }
                if (waitingTasks.size() != 0) {

                    if (currentBlock == DISC_SIZE) {
                        currentBlock = 0;
                    }
                    currentBlock++;
                    blocks++;
                }
            }
            currentTime++;
        }
        return blocks;
    }
}
