package pages;

public class Page {
    private final int id;
    private boolean present;
    private int lastReference;

    public Page(int id) {
        this.id = id;
        this.present=false;
        this.lastReference=-1;
    }

    // metoda zwraca unikalne id strony
    public int getId() {
        return id;
    }

    // metoda zwraca true, jeśli strona jest obecnie w pamięci fizycznej, false w przeciwnym razie
    public boolean isPresent() {
        return present;
    }

    // metoda ustawia flagę present na true
    public void setPresent() {
        present = true;
    }

    // metoda ustawia czas ostatniego odwołania
    public void setLastReference(int time) {
        lastReference = time;
    }

    // metoda zwraca czas ostatniego odwołania
    public int getLastReference() {
        return lastReference;
    }
}
