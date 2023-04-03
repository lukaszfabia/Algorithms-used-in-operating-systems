package VirtualMemory;

import java.util.*;

public class PageAlgo {

    private final int[] pages;
    private final int numFrames;

    public PageAlgo(int[] pages, int numFrames) throws CustomException {
        this.pages = pages;
        if (numFrames == 0) {
            throw new CustomException("Wrong number of frames!");
        }
        this.numFrames = numFrames;
    }

    public int fifo() {
        int faults = 0;
        Queue<Integer> frames = new LinkedList<>();

        for (int page : pages) {
            if (!frames.contains(page)) {
                if (frames.size() == numFrames) {
                    frames.poll();
                }
                frames.offer(page);
                faults++;
            }
        }

        return faults;
    }


    public int lru() {
        int faults = 0;
        Queue<Integer> frames = new LinkedList<>();

        for (int page : pages) {
            if (frames.contains(page)) {
                frames.remove(page);
            } else {
                faults++;
                if (frames.size() == numFrames) {
                    frames.poll();
                }
            }
            frames.offer(page);
        }
        return faults;
    }

    public int random() {
        int faults = 0;
        List<Integer> frames = new LinkedList<>();
        Random rand = new Random();

        for (int page : pages) {
            if (!frames.contains(page)) {
                if (frames.size() < numFrames) {
                    frames.add(page);
                } else {
                    int index = rand.nextInt(numFrames);
                    frames.set(index,page);
                }
                faults++;
            }
        }
        return faults;
    }

    public int approximateLru() {
        int faults=0;
        List<Integer> frames = new ArrayList<>(numFrames);
        int[] bits = new int[numFrames];

        for (int page : pages) {
            int index = frames.indexOf(page);
            if (index == -1) {
                faults++;
                if (frames.size() < numFrames) {
                    frames.add(page);
                    bits[frames.size() - 1] = 1;
                } else {
                    int minBits = Integer.MAX_VALUE;
                    int minIndex = -1;
                    for (int i = 0; i < numFrames; i++) {
                        if (bits[i] < minBits) {
                            minBits = bits[i];
                            minIndex = i;
                        }
                    }
                    frames.set(minIndex, page);
                    bits[minIndex] = 1;
                }
            } else {
                bits[index] = 1;
            }
            for (int i = 0; i < numFrames; i++) {
                if (i != index && bits[i] > 0) {
                    bits[i]++;
                }
            }
        }

        return faults;

    }

    public int optimal() {
        int faults = 0;
        List<Integer> frames = new ArrayList<>(numFrames);
        int page, furthestPage;
        for (int i = 0; i < pages.length; i++) {
            page = pages[i];
            if (!frames.contains(page)) {
                if (frames.size() < numFrames) {
                    frames.add(page);
                } else {
                    furthestPage = findFurthestPage(i, pages, frames);
                    frames.remove(Integer.valueOf(furthestPage));
                    frames.add(page);
                }
                faults++;
            }
        }
        return faults;
    }

    private int findFurthestPage(int startIndex, int[] pages, List<Integer> frames) {
        int furthestPage = -1;
        int furthestIndex = -1;
        for (int frame : frames) {
            int index = findNextIndex(startIndex, pages, frame);
            if (index > furthestIndex) {
                furthestIndex = index;
                furthestPage = frame;
            }
        }
        return furthestPage;
    }

    private int findNextIndex(int startIndex, int[] pages, int page) {
        for (int i = startIndex; i < pages.length; i++) {
            if (pages[i] == page) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

}
