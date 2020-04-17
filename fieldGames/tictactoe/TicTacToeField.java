package fieldGames.tictactoe;

import fieldGames.*;

class TicTacToeField extends Field {

    /**
     * Explain the following three fields.
     * <p>
     *     These fields are created for analyzing the state of the TicTacToeField.
     *     They are updated only when a new site is changed from EMPTY to X or O.
     * </p>
     * <p>
     *     {@code rowStates} records number of Xs <i>minus</i> number of Os of each row.
     *     {@code colStates} records number of Xs <i>minus</i> number of Os of each column.
     *     {@code diagonalStates} records number of Xs <i>minus</i> number of Ys of each diagonal.
     *     When one of these states reach {@code fieldSize} or {@code -fieldSize}, X or O wins accordingly.
     * </p>
     */
    private final int[] rowStates = new int[getFieldSize()];
    private final int[] colStates = new int[getFieldSize()];
    private final int[] diagonalStates = new int[2];

    TicTacToeField(int fieldSize) throws IllegalArgumentException {
        super(fieldSize);
        if (fieldSize < 1 || fieldSize > 9) {
            throw new IllegalArgumentException("Only support size between 1 and 9!");
        }
    }

    /**
     * The private method {@code updateFieldState} updates the TicTacToe Game state by checking only one site (x, y).
     * It first updates {@code rowStates}, {@code colStates}, and {@code diagonalStates}.
     * Then, it checks whether X or Y wins or not.
     * Finally, it updates {@code fieldStates} according to the following logic:
     * <p>
     *     If the difference between the numbers of O and X exceeds 1, returns IMPOSSIBLE.
     *     If both O and X wins, returns IMPOSSIBLE,
     *     else if X Wins, returns X_WINS,
     *     else if O Wins, returns O_WINS.
     *     If none of them wins and there are still empty sites, returns UNFINISHED.
     *     Otherwise, returns DRAW.
     * </p>
     *
     * @param xAxis the first coordinate
     * @param yAxis the second coordinate
     */
    public void updateFieldState(int xAxis, int yAxis) {

        /*
         * Update rowStates, colStates, diagonalStates.
         */
        switch (getSiteState(xAxis, yAxis)) {
            case X:
                rowStates[xAxis]++;
                colStates[yAxis]++;
                if (xAxis == yAxis) {
                    diagonalStates[0]++;
                }
                if (xAxis + yAxis == getFieldSize() - 1) {
                    diagonalStates[1]++;
                }
                break;
            case O:
                rowStates[xAxis]--;
                colStates[yAxis]--;
                if (xAxis == yAxis) {
                    diagonalStates[0]--;
                }
                if (xAxis + yAxis == getFieldSize() - 1) {
                    diagonalStates[1]--;
                }
                break;
            default:
                break;
        }

        /*
         * Check whether X or Y wins.
         */
        boolean xWins = false;
        boolean oWins = false;

        // if the diagonal is all X or all Y, X or Y wins.
        if (diagonalStates[0] == getFieldSize() || diagonalStates[1] == getFieldSize()) {
            xWins = true;
        }
        if (diagonalStates[0] == -getFieldSize() || diagonalStates[1] == -getFieldSize()) {
            oWins = true;
        }

        // if a row or a column is all X, X wins.
        if (!xWins) {
            for (int i = 0; i < getFieldSize(); i++) {
                if (rowStates[i] == getFieldSize() || colStates[i] == getFieldSize()) {
                    xWins = true;
                    break;
                }
            }
        }

        // if a row or a column is all Y, Y wins.
        if (!oWins) {
            for (int i = 0; i < getFieldSize(); i++) {
                if (rowStates[i] == -getFieldSize() || colStates[i] == -getFieldSize()) {
                    oWins = true;
                    break;
                }
            }
        }

        /*
         * Update fieldState.
         */
        if (Math.abs(getNumberOfStates(State.O) - getNumberOfStates(State.X)) > 1) {
            setFieldState(FieldState.IMPOSSIBLE);
        }
        if (xWins && oWins) {
            setFieldState(FieldState.IMPOSSIBLE);
        } else if (xWins) {
            setFieldState(FieldState.X_WINS);
        } else if (oWins) {
            setFieldState(FieldState.O_WINS);
        } else if (getNumberOfStates(State.EMPTY) > 0) {
            setFieldState(FieldState.UNFINISHED);
        } else {
            setFieldState(FieldState.DRAW);
        }
    }

    /**
     * The rest of this class deals with field printing.
     *
     * @return the string to be printed.
     */
    @Override
    public String toString() {
        String bar = this.horizontalBar();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(spaces(5));
        stringBuilder.append(bar);

        int i;
        for(i = 0; i < this.getFieldSize(); ++i) {
            stringBuilder.append(spaces(4));
            stringBuilder.append(this.getFieldSize() - i);
            stringBuilder.append("| ");

            for(int j = 0; j < this.getFieldSize(); ++j) {
                char endSpace = ' ';
                stringBuilder.append(this.getSiteState(i, j));
                stringBuilder.append(endSpace);
            }

            stringBuilder.append('|');
            stringBuilder.append('\n');
        }

        stringBuilder.append(spaces(5));
        stringBuilder.append(bar);
        stringBuilder.append(spaces(6));

        for(i = 1; i <= this.getFieldSize(); ++i) {
            stringBuilder.append(' ');
            stringBuilder.append(i);
        }

        stringBuilder.append('\n');
        return stringBuilder.toString();
    }

    /**
     * {@code horizontalBar} is a utility method for {@code toString}
     *
     * @return the a proper horizontal bar "----"
     */
    private String horizontalBar() {
        char[] barChar = new char[2 * getFieldSize() + 4];
        for (int i = 0; i < 2 * getFieldSize() + 3; i++) {
            barChar[i] = '-';
        }
        barChar[2 * getFieldSize() + 3] = '\n';
        return new String(barChar);
    }

    private String spaces(int l) {
        char[] spaces = new char[l];
        for (int i = 0; i < l; i++) {
            spaces[i] = ' ';
        }
        return new String(spaces);
    }
}
