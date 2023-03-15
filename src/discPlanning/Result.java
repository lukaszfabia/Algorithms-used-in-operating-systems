package discPlanning;

public record Result(String name, int discMoves) {

    @Override
    public String toString() {
        return String.format("%-20s %-20d", name(), discMoves());
    }
}

