package fieldGames;

public enum FieldState {
    IMPOSSIBLE("Impossible"),
    DRAW("Draw"),
    UNFINISHED("Game not finished"),
    X_WINS("X wins"),
    O_WINS("O wins");

    private String value;
    FieldState(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
