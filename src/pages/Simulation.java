package pages;

import java.util.*;

public class Simulation {
    public int simulateFIFO(List<Integer> referenceString, int numFrames) {
        int numPageFaults = 0;
        Queue<Page> pageQueue = new LinkedList<>(); // kolejka stron

        // utwórz listę stron o id od 0 do n-1, gdzie n to liczba unikalnych stron w ciągu odwołań
        List<Page> pages = new ArrayList<>();
        for (int i = 0; i < Collections.max(referenceString) + 1; i++) {
            pages.add(new Page(i));
        }

        // przejdź przez ciąg odwołań do stron
        for (int i = 0; i < referenceString.size(); i++) {
            int pageNumber = referenceString.get(i);
            Page page = pages.get(pageNumber);

            // sprawdź, czy strona jest już w pamięci fizycznej
            if (page.isPresent()) {
                page.setLastReference(i); // ustaw czas ostatniego odwołania strony
            } else {
                // jeśli pamięć fizyczna nie jest jeszcze pełna, dodaj stronę do kolejki i do pamięci fizycznej
                if (pageQueue.size() < numFrames) {
                    pageQueue.add(page);
                    page.setPresent();
                    page.setLastReference(i);
                } else {
                    // w przeciwnym razie usuń pierwszą stronę z kolejki i dodaj nową stronę
                    Page oldestPage = pageQueue.remove();
                    numPageFaults++;
                    pageQueue.add(page);
                    page.setPresent();
                    page.setLastReference(i);
                }
            }
        }

        return numPageFaults;
    }

}
