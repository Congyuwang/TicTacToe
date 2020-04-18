package fieldGames;

public enum State {
    EMPTY("·"),
    X("X"),
    O("O");

    private String value;
    State(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
