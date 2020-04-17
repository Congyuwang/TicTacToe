package tictactoe;

import java.util.Arrays;

public class FiveInARowField extends Field {

    private final int nInARow;
    private final char[] alphabets = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final int[] rowStatesX = new int[getFieldSize()];
    private final int[] colStatesX = new int[getFieldSize()];
    private final int[] rowStatesO = new int[getFieldSize()];
    private final int[] colStatesO = new int[getFieldSize()];
    private final int[] diagonalStatesLUX = new int[getFieldSize() * 2 - 1];
    private final int[] diagonalStatesRUX = new int[getFieldSize() * 2 - 1];
    private final int[] diagonalStatesLUO = new int[getFieldSize() * 2 - 1];
    private final int[] diagonalStatesRUO = new int[getFieldSize() * 2 - 1];

    FiveInARowField(int fieldSize, int nInARow) {
        super(fieldSize);
        if (fieldSize < nInARow || nInARow <= 1 || fieldSize > 26) {
            throw new IllegalArgumentException("Unsupported parameters!");
        }
        this.nInARow = nInARow;
    }

    @Override
    void updateFieldState(int xAxis, int yAxis) throws IllegalArgumentException {
        int posLU = getFieldSize() - 1 + xAxis - yAxis;
        int posRU = xAxis + yAxis;
        switch (getSiteState(xAxis, yAxis)) {
            case X:
                rowStatesX[xAxis]++;
                colStatesX[yAxis]++;
                diagonalStatesLUX[posLU]++;
                diagonalStatesRUX[posRU]++;
                break;
            case O:
                rowStatesO[xAxis]++;
                colStatesO[yAxis]++;
                diagonalStatesLUO[posLU]++;
                diagonalStatesRUO[posRU]++;
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
        if (max(diagonalStatesLUX) >= nInARow || max(diagonalStatesRUX) >= nInARow) {
            xWins = true;
        }
        if (max(diagonalStatesLUO) >= nInARow || max(diagonalStatesRUO) >= nInARow) {
            oWins = true;
        }

        // check rows and columns
        if (max(rowStatesX) >= nInARow || max(colStatesX) >= nInARow) {
            xWins = true;
        }
        if (max(rowStatesO) >= nInARow || max(colStatesO) >= nInARow) {
            oWins = true;
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
        for (i = 0; i < this.getFieldSize(); ++i) {
            stringBuilder.append(spaces(4));
            stringBuilder.append(alphabets[this.getFieldSize() - i - 1]);
            stringBuilder.append("| ");

            for (int j = 0; j < this.getFieldSize(); ++j) {
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

        for (i = 0; i < this.getFieldSize(); ++i) {
            stringBuilder.append(' ');
            stringBuilder.append(alphabets[i]);
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

    private int max(int[] a) {
        return Arrays.stream(a).max().orElse(0);
    }
}
