package AlgorithmsForPages;

import java.util.Arrays;
import java.util.LinkedList;

public class PageAlgo {

    private final int[] pages;
    private final int numFrames;

    public PageAlgo(int[] pages, int numFrames) {
        this.pages = pages;
        this.numFrames = numFrames;
    }

    public int fifo() {
        int faults = 0;
        int nextFrame = 0;
        int[] frames = new int[numFrames];
        Arrays.fill(frames, -1);

        for (int page : pages) {
            boolean found = false;
            for (int frame : frames) {
                if (frame == page) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                frames[nextFrame] = page;
                nextFrame = (nextFrame + 1) % numFrames;
                faults++;
            }
        }

        return faults;
    }

    public int lru() {
        int faults = 0;
        LinkedList<Integer> frames = new LinkedList<>();

        for (int page : pages) {
            if (frames.contains(page)) {
                // jeśli strona jest już w pamięci fizycznej,
                // usuwamy ją z listy i dodajemy na początek
                frames.remove((Integer) page);
                frames.addFirst(page);
            } else {
                // jeśli strona nie jest w pamięci fizycznej,
                // usuwamy najstarszą stronę z listy (ostatni element)
                // i dodajemy nową stronę na początek listy
                faults++;
                if (frames.size() == numFrames) {
                    frames.removeLast();
                }
                frames.addFirst(page);
            }
        }
        return faults;
    }

}
