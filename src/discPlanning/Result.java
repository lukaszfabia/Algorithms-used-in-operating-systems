package discPlanning;

public record Result(int discMoves, int name) {

    @Override
    public String toString() {
        return String.format("%-5s, %-5d", name(), discMoves());
    }
}

