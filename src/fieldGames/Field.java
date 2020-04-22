package fieldGames;

import java.util.HashMap;

public abstract class Field {

    private final int fieldSize;
    private FieldState fieldState;
    private final State[][] field;
    private final HashMap<State, Integer> numberOfStates;

    /**
     * initialize all sites to EMPTY state.
     * @param size the size of the field.
     */
    protected Field(int size) throws IllegalArgumentException {

        if (size < 1) {
            throw new IllegalArgumentException("Field Size must be greater than 0");
        }

        fieldSize = size;
        fieldState = FieldState.UNFINISHED;

        field = new State[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = State.EMPTY;
            }
        }

        numberOfStates = new HashMap<>();
        numberOfStates.put(State.EMPTY, fieldSize * fieldSize);
        for (State s : State.values()) {
            if (s != State.EMPTY) {
                numberOfStates.put(s, 0);
            }
        }
    }

    /**
     * {@code playState} changes the state of a site (x, y) in the field to State s,
     * updates {@code numberOfStates} which records the number of sites in each state,
     * and lastly updates the TicTacToe field state using {@code updateFieldState}.
     *
     * @param s the {@code State} which the site (s, y) will change to
     * @param xAxis the first coordinate
     * @param yAxis the second coordinate
     */
    public void playState(State s, int xAxis, int yAxis) throws IllegalArgumentException {
        if (xAxis < 0 || xAxis > getFieldSize() - 1) {
            throw new IllegalArgumentException("Coordinate xAxis exceeds range!");
        }
        if (yAxis < 0 || yAxis > getFieldSize() - 1) {
            throw new IllegalArgumentException("Coordinate yAxis exceeds range!");
        }
        State currentState = getSiteState(xAxis, yAxis);
        numberOfStates.put(currentState, numberOfStates.get(currentState) - 1);
        setSiteState(s, xAxis, yAxis);
        numberOfStates.put(s, numberOfStates.get(s) + 1);
        updateFieldState(xAxis, yAxis);
    }

    abstract protected void updateFieldState(int xAxis, int yAxis) throws IllegalArgumentException;

    abstract public String toString();

    /**
     * Return the {@code State} of the site at (x, y) in the field.
     *
     * @param xAxis x coordinate
     * @param yAxis y coordinate
     * @return the state of the site at (x, y)
     * @throws IllegalArgumentException when the coordinate is out of range
     */
    public State getSiteState(int xAxis, int yAxis) throws IllegalArgumentException {
        if (xAxis < 0 || xAxis > fieldSize - 1) {
            throw new IllegalArgumentException("Coordinate xAxis exceeds range!");
        }
        if (yAxis < 0 || yAxis > fieldSize - 1) {
            throw new IllegalArgumentException("Coordinate yAxis exceeds range!");
        }
        return field[xAxis][yAxis];
    }

    protected void setSiteState(State s, int xAxis, int yAxis) throws IllegalArgumentException {
        if (xAxis < 0 || xAxis > fieldSize - 1) {
            throw new IllegalArgumentException("Coordinate xAxis exceeds range!");
        }
        if (yAxis < 0 || yAxis > fieldSize - 1) {
            throw new IllegalArgumentException("Coordinate yAxis exceeds range!");
        }
        field[xAxis][yAxis] = s;
    }

    /**
     * returns the number of sites in the field that are in {@code State} s.
     * @param s the {@code State} s to query
     * @return the number of sites in the field that are in {@code State} s
     */
    protected int getNumberOfStates(State s) {
        return numberOfStates.get(s);
    }

    public int getFieldSize() {
        return fieldSize;
    }

    protected void setFieldState(FieldState fieldState) {
        this.fieldState = fieldState;
    }

    public FieldState getFieldState() {
        return fieldState;
    }

}
