import java.util.*;


public class FramesAlgo implements FrameAllocationStrategy {

    private final int frameSize;
    private final int pagesNr;
    private final int processesAmount;
    private final int range;
    private final Process[] processes;

    public FramesAlgo(int frameSize, int pagesNr, int range, int processesAmount) {
        this.frameSize = frameSize;
        this.pagesNr = pagesNr;
        this.processesAmount = processesAmount;
        this.range = range;
        this.processes = new Process[processesAmount];
        this.otherGenerate(0.03, 20);
        //generate();
    }

    private void generate() {
        ArrayList<Page> pageReferences = new ArrayList<>();

        for (int i = 0; i < pagesNr; i++) {
            int k = (int) (Math.random() * processesAmount);
            // mnozymy przez jakiegos randoma zaby zwiekszyc zakres losowania numeru stron - zasada lokalnosci odwolan?
            int r = (int) (Math.random() * this.range);

            pageReferences.add(new Page(r, 0, k));

        }

        for (int i = 0; i < processesAmount; i++) {
            processes[i] = new Process(new ArrayList<>(), 0);
            for (Page pageReference : pageReferences) {
                if (pageReference.getProcess() == i) {
                    processes[i].requests.add(pageReference);
                }
            }
        }

    }

    private void otherGenerate(double localProbability, int localAmount) {
        Random rand = new Random();

        // Generowanie procesów
        for (int i = 0; i < processesAmount; i++) {
            int processID = i + 1;
            ArrayList<Page> processRequests = new ArrayList<>();
            Process process = new Process(processRequests, frameSize, processID);
            processes[i] = process;
        }

        // Generowanie odwołań do stron
        for (int i = 0; i < pagesNr; i++) {
            int processIndex = rand.nextInt(processesAmount);
            Process process = processes[processIndex];
            int pageID = rand.nextInt(range) + 1;

            double localChance = rand.nextDouble();
            if (localChance < localProbability) {
                // Generowanie lokalnych odwołań
                int startPageID = Math.max(1, pageID - localAmount);
                int endPageID = Math.min(range, pageID + localAmount);

                for (int j = startPageID; j <= endPageID; j++) {
                    int pageRef = rand.nextInt(100) + 1; // Przykładowa generacja odwołania
                    Page page = new Page(j, pageRef, process.getProcessId());
                    //Page page = new Page(process.getProcessId(), pageRef, j);
                    process.requests.add(page);
                }
            } else {
                // Generowanie losowych odwołań
                int pageRef = rand.nextInt(100) + 1; // Przykładowa generacja odwołania
                Page page = new Page(pageID, pageRef, process.getProcessId());
                //Page page = new Page(process.getProcessId(), pageRef, pageID);
                process.requests.add(page);
            }
        }
    }

    @Override
    public int equalAllocation() {
        Process[] copiedProcesses = new Process[processesAmount]; // tworzymy sobie tablice pomocnicza do skopiowania referencji
        int sumOfPageFaults = 0;

        int newFrameSize = frameSize / (processes.length); // domyslnie przydzielony rozmiar ramki przez ilosc procesow

        for (int i = 0; i < processes.length; i++) {
            copiedProcesses[i] = new Process(processes[i]); // kopiujemy
            sumOfPageFaults += lru(copiedProcesses[i].requests, newFrameSize); // liczymy liczbe bledow kazdego procesu i sumujemy

        }

        return sumOfPageFaults;
    }

    @Override
    public int proportionalAllocation() {
        int newFrameSize;
        int sumOfPageFaults = 0;

        Process[] copiedProcesses = new Process[processesAmount];

        for (int i = 0; i < processes.length; i++) {
            copiedProcesses[i] = new Process(processes[i]);
        }

        for (Process copiedProcess : copiedProcesses) {
            newFrameSize = copiedProcess.requests.size() * frameSize / pagesNr;
            sumOfPageFaults += lru(copiedProcess.requests, newFrameSize);
        }
        return sumOfPageFaults;
    }

    public void propModded() {
        int newFrameSize;
        int sumOfPageFaults = 0;
        Process[] copiedProcesses = new Process[processesAmount];

        for (int i = 0; i < processes.length; i++) {
            copiedProcesses[i] = new Process(processes[i]);
        }


        for (int i = 0; i < processes.length; i++) {
            newFrameSize = copiedProcesses[i].requests.size() * frameSize / pagesNr;
            int processNumber = i + 1;
            int pagesNumber = copiedProcesses[i].requests.size();
            int assignedFrame = lru(copiedProcesses[i].requests, newFrameSize);

            System.out.println("Proces " + processNumber + ": Liczba stron - " + pagesNumber +
                    ", Przydzielona ramka - " + newFrameSize);

            sumOfPageFaults += assignedFrame;
        }
        System.out.println("Przydział proporcjonalny: " + sumOfPageFaults + "\n");
    }

    public void equalModded() {
        Process[] copiedProcesses = new Process[processesAmount]; // tworzymy sobie tablice pomocnicza do skopiowania referencji
        int sumOfPageFaults = 0;

        int newFrameSize = frameSize / (processes.length); // domyslnie przydzielony rozmiar ramki przez ilosc procesow

        for (int i = 0; i < processes.length; i++) {
            copiedProcesses[i] = new Process(processes[i]); // kopiujemy
            int processNumber = i + 1;
            int pagesNumber = copiedProcesses[i].requests.size();
            int assignedFrame = lru(copiedProcesses[i].requests, newFrameSize);

            System.out.println("Proces " + processNumber + ": Liczba stron - " + pagesNumber +
                    ", Przydzielona ramka - " + newFrameSize);

            sumOfPageFaults += assignedFrame;
        }
        System.out.println("Przydział równy: " + sumOfPageFaults + "\n");

    }

    @Override
    public int faultRateControl() {
        int PFMax = (int) (0.70 * processesAmount); // wartosc max czestosc bledow strony
        //int newFrameSize = frameSize / processes.length; // rozmiar ramki wd strategi proporcjonalnej

        Process[] copiedProcesses = new Process[processesAmount];
        for (int i = 0; i < processesAmount; i++) {
            copiedProcesses[i] = new Process(processes[i]); // kopiujemy procesy
            copiedProcesses[i].setFrame(frameSize*copiedProcesses[i].requests.size()/pagesNr); // ustawiamy sobie nowy rozmiar ramki
        }
        int freeFrames = 0;
        int size = processesAmount;
        int sumOfPageFaults = 0;

        while (size != 0) {
            int min = Integer.MAX_VALUE;
            int max = 0;
            int minI = 0; // ind prc ktory daje min bled
            int maxI = 0; // indeks procesu ktory daje najwiecej bledow
            for (int i = 0; i < copiedProcesses.length; i++) {
                Process helpProcess = copiedProcesses[i];
                if (helpProcess != null && !helpProcess.requests.isEmpty()) {
                    if (size == 1) {
                        //dodanie wolnych ramek do procesu
                        copiedProcesses[i].setFrame(copiedProcesses[i].getFrameSize() + freeFrames);
                        freeFrames = 0;
                    }
                    int pf = helpProcess.getPageFaultFrequency();
                    //pojedyncze bledy strony
                    int pfSingle = helpProcess.lru(helpProcess.requests);
                    //szukamy procesow ktore daj max/min bledow
                    if (pf > max) {
                        max = pf;
                        maxI = i;
                    }
                    if (pf < min) {
                        min = pf;
                        minI = i;
                    }
                    helpProcess.requests.remove(0);
                    sumOfPageFaults += pfSingle;
                } else if (helpProcess != null) {

                    if (copiedProcesses[maxI] != null && maxI != i) {
                        //gdy dany proces jest zakonczony
                        copiedProcesses[maxI].setFrame(copiedProcesses[maxI].getFrameSize() + copiedProcesses[i].getFrameSize());
                    } else {
                        freeFrames += copiedProcesses[i].getFrameSize();
                    }
                    copiedProcesses[i] = null;
                    size--;
                }

            }
            // Zmiana rozmiaru stron, gdy liczba błędów stron przekroczyła wartość graniczną
            if (copiedProcesses[minI] != null && copiedProcesses[maxI] != null && copiedProcesses[minI].getPFrame() != 1 && max > PFMax) {
                if (copiedProcesses[minI].getFrameSize() > 3) {
                    // -1 do ramki procesu generujacego najmniej bledow
                    copiedProcesses[minI].setFrame(copiedProcesses[minI].getFrameSize() - 1);
                    // dolozenie ramki do tego ktory generuje max bledow
                    copiedProcesses[maxI].setFrame(copiedProcesses[maxI].getFrameSize() + 1 + freeFrames);
                    freeFrames = 0;
                }
            }
        }
        return sumOfPageFaults;
    }

    public void faultRateControlModded() {
        int PFMax = (int) (0.7 * processesAmount); // wartosc max czestosc bledow strony
        //int newFrameSize = frameSize / processes.length; // rozmiar ramki wd strategi proporcjonalnej
        int[] sizes = new int[processes.length];
        Process[] copiedProcesses = new Process[processesAmount];
        for (int i = 0; i < processes.length; i++) {
            sizes[i] = processes[i].requests.size();
            copiedProcesses[i] = new Process(processes[i]); // kopiujemy procesy
            copiedProcesses[i].setFrame(frameSize*copiedProcesses[i].requests.size()/pagesNr); // ustawiamy sobie nowy rozmiar ramki
        }
        int freeFrames = 0;
        int size = processesAmount;
        int sumOfPageFaults = 0;

        while (size != 0) {
            int min = range;
            int max = 0;
            int minI = 0; // ind prc ktory daje min bled
            int maxI = 0; // indeks procesu ktory daje najwiecej bledow
            for (int i = 0; i < copiedProcesses.length; i++) {
                Process helpProcess = copiedProcesses[i];
                if (helpProcess != null && helpProcess.requests.size() != 0) {
                    if (size == 1) {
                        //dodanie wolnych ramek do procesu
                        copiedProcesses[i].setFrame(copiedProcesses[i].getFrameSize() + freeFrames);
                        freeFrames = 0;
                    }
                    int pf = helpProcess.getPageFaultFrequency();
                    //pojedyncze bledy strony
                    int pfSingle = helpProcess.lru(helpProcess.requests);
                    //szukamy procesow ktore daj max/min bledow
                    if (pf > max) {
                        max = pf;
                        maxI = i;
                    }
                    if (pf < min) {
                        min = pf;
                        minI = i;
                    }

                    helpProcess.requests.remove(0);
                    sumOfPageFaults += pfSingle;
                } else if (helpProcess != null) {
                    int processNumber = i + 1;
                    int pagesNumber = sizes[i];

                    System.out.println("Proces " + processNumber + ": Liczba stron - " + pagesNumber +
                            ", Przydzielona ramka - " + copiedProcesses[i].getFrameSize());
                    if (copiedProcesses[maxI] != null && maxI != i) {
                        //gdy dany proces jest zakonczony
                        copiedProcesses[maxI].setFrame(copiedProcesses[maxI].getFrameSize() + copiedProcesses[i].getFrameSize());

                    } else {
                        freeFrames += copiedProcesses[i].getFrameSize();
                    }
                    copiedProcesses[i] = null;
                    size--;
                }

            }
            // Zmiana rozmiaru stron, gdy liczba błędów stron przekroczyła wartość graniczną
            if (copiedProcesses[minI] != null && copiedProcesses[maxI] != null && copiedProcesses[minI].getPFrame() != 1
                    && max > PFMax) {
                if (copiedProcesses[minI].getFrameSize() > 3) {
                    // -1 do ramki procesu generujacego najmniej bledow
                    copiedProcesses[minI].setFrame(copiedProcesses[minI].getFrameSize() - 1);
                    // dolozenie ramki do tego ktory generuje max bledow
                    copiedProcesses[maxI].setFrame(copiedProcesses[maxI].getFrameSize() + 1 + freeFrames);

                    freeFrames = 0;
                }
            }
        }
        System.out.println("Przydział wd. sterowania czestoscia bladow strony: " + sumOfPageFaults + "\n");
    }

    @Override
    public int zoneModel(int zone) {
        int sumOfPageFaults = 0;
        int freeFrames = frameSize;
        int done = -1;
        //skopiowanie referencji
        Process[] copiedProcesses = new Process[processesAmount];
        for (int i = 0; i < processes.length; i++) {
            copiedProcesses[i] = new Process(processes[i]);
            //rozmiar ramki jako liczba duplikatow w stefie
            copiedProcesses[i].setFrame(workingSetSize(copiedProcesses[i].requests, zone)); // ustawiamy rozmiar ramki
        }

        do {
            for (int i = done + 1; i < processes.length; i++) {
                if (freeFrames > copiedProcesses[i].getFrameSize()) {
                    done++;
                    freeFrames -= copiedProcesses[i].getFrameSize(); // odejemujemy zalokowane ramki
                    if (copiedProcesses[i].getFrameSize() != 0) {
                        sumOfPageFaults += lru(copiedProcesses[i].requests, copiedProcesses[i].getFrameSize());
                    }
                }
            }
            freeFrames = frameSize;
        }
        while (done != processesAmount - 1); // czekamy na procesy

        return sumOfPageFaults;
    }

    public void zoneModelModded(int zone) {
        int sumOfPageFaults = 0;
        int freeFrames = frameSize;
        int done = -1;
        //copying page references
        Process[] copiedProcesses = new Process[processesAmount];
        for (int i = 0; i < processes.length; i++) {
            copiedProcesses[i] = new Process(processes[i]);
            //rozmiar ramki jako liczba duplikatow w stefie
            copiedProcesses[i].setFrame(workingSetSize(copiedProcesses[i].requests, zone)); // ustawiamy rozmiar ramki
        }

        do {
            for (int i = done + 1; i < processes.length; i++) {
                if (freeFrames > copiedProcesses[i].getFrameSize()) {
                    done++;
                    freeFrames -= copiedProcesses[i].getFrameSize(); // odejemujemy zalokowane ramki
                    if (copiedProcesses[i].getFrameSize() != 0) {
                        int processNumber = i + 1;
                        int pagesNumber = copiedProcesses[i].requests.size();
                        int assignedFrame = lru(copiedProcesses[i].requests, copiedProcesses[i].getFrameSize());
                        System.out.println("Proces " + processNumber + ": Liczba stron - " + pagesNumber +
                                ", Przydzielona ramka - " + copiedProcesses[i].getFrameSize());
                        sumOfPageFaults += assignedFrame;
                    }
                }
            }
            freeFrames = frameSize;
        }
        while (done != processesAmount - 1); // czekamy na procesy
        System.out.println("Przydział wd. modelu strefowego: " + sumOfPageFaults + "\n");
    }

    public void zoneModelOther(int zone) {
        int sumOfPageFaults = 0;
        int freeFrames = frameSize;
        int done = -1;

        Process[] copiedProcesses = new Process[processesAmount];
        for (int i = 0; i < processes.length; i++) {
            copiedProcesses[i] = new Process(processes[i]);
            //rozmiar ramki jako liczba duplikatow w stefie
        }

        for (Process copiedProcess : copiedProcesses) {
            int workingSet = copiedProcess.requests.size() * frameSize / pagesNr;
            copiedProcess.setFrame(workingSet);
        }


        while (done != processesAmount - 1) {
            for (Process process : copiedProcesses) {
                int workingSet = workingSetSize(process.requests, zone); // Update zbioru roboczego
                process.setFrame(workingSet);
            }
            for (int i = done + 1; i < processes.length; i++) {
                // Wolne ramki
                if (freeFrames > copiedProcesses[i].getFrameSize()) {
                    done += 1;
                    //int w = copiedProcesses[i].getFrameSize();
                    // Odejmowanie zajętych ramek
                    freeFrames -= copiedProcesses[i].getFrameSize();
                    // Jeśli brakuje ramek, to nie idziemy dalej, czyli usypiamy na iterację
                    if (freeFrames < 0) {
                        freeFrames += copiedProcesses[i].getFrameSize(); // Dodajemy ramki uspionego
                        continue;
                    }
                    if (copiedProcesses[i].getFrameSize() != 0) {
                        int processNumber = i + 1;
                        int pagesNumber = copiedProcesses[i].requests.size();
                        int assignedFrame = lru(copiedProcesses[i].requests, copiedProcesses[i].getFrameSize());
                        System.out.println("Proces " + processNumber + ": Liczba stron - " + pagesNumber +
                                ", Przydzielona ramka - " + copiedProcesses[i].getFrameSize());
                        sumOfPageFaults += assignedFrame;
                    }
                }
                freeFrames = frameSize;
            }
        }
        System.out.println("Przydział wd. modelu strefowego: " + sumOfPageFaults + "\n");
    }

    private int lru(ArrayList<Page> pageRefs, int frameSize) {
        int faults = 0;
        Queue<Page> frames = new LinkedList<>();

        for (Page page : pageRefs) {
            boolean pageFound = false;
            for (Page p : frames) {
                if (p.getNr() == page.getNr()) {
                    frames.remove(p);
                    frames.offer(page);
                    pageFound = true;
                    break;
                }
            }
            if (!pageFound) {
                if (frames.size() == frameSize) {
                    frames.poll();
                }
                frames.offer(page);
                faults++;
            }
        }
        return faults;
    }


    // wyznaczenie rozmiaru zbioru roboczego
    private int workingSetSize(ArrayList<Page> pages, int zone) {
        HashSet<Integer> set = new HashSet<>();
        if (zone > pages.size()) {
            zone = pages.size();
        }
        for (int i = 0; i < zone; i++) {
            set.add(pages.get(i).getNr());
        }
        return set.size();
    }

    @Override
    public void showResults(int zone) {
        System.out.println("\nWyniki: ");
        System.out.println("Przydział równy: " + equalAllocation());
        System.out.println("Przydział proporcjonalny: " + proportionalAllocation());
        System.out.println("Przydział wd. modelu strefowego: " + zoneModel(zone));
        System.out.println("Przydział wd. sterowania czestoscia bladow strony: " + faultRateControl());
        System.out.println();
    }

    public void showAllResults(int zone) {
        System.out.println("\nWyniki: ");
        equalModded();
        propModded();
        zoneModelOther(zone);
        faultRateControlModded();

    }

    public void showArray() {
        for (Process process : processes) {
            System.out.println(process.requests);
        }
    }

}