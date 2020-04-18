package fieldGames.fiveInARow;

import java.util.Arrays;
import fieldGames.*;

class FiveInARowField extends Field {

    private final int nInARow;

    FiveInARowField(int fieldSize, int nInARow) {
        super(fieldSize);
        if (fieldSize < nInARow || nInARow <= 1 || fieldSize > 26) {
            throw new IllegalArgumentException("Unsupported parameters!");
        }
        this.nInARow = nInARow;
    }

    @Override
    public void updateFieldState(int a, int b) throws IllegalArgumentException {
        int[] rowStatesX = new int[getFieldSize()];
        int[] colStatesX = new int[getFieldSize()];
        int[] rowStatesO = new int[getFieldSize()];
        int[] colStatesO = new int[getFieldSize()];
        int[] diagonalStatesLUX = new int[getFieldSize() * 2 - 1];
        int[] diagonalStatesRUX = new int[getFieldSize() * 2 - 1];
        int[] diagonalStatesLUO = new int[getFieldSize() * 2 - 1];
        int[] diagonalStatesRUO = new int[getFieldSize() * 2 - 1];

        for (int xAxis = 0; xAxis < getFieldSize(); xAxis++) {
            int rowX = 0;
            int rowO = 0;
            for (int yAxis = 0; yAxis < getFieldSize(); yAxis++) {
                switch (getSiteState(xAxis, yAxis)) {
                    case X:
                        rowO = 0;
                        rowX++;
                        break;
                    case O:
                        rowO++;
                        rowX = 0;
                        break;
                    case EMPTY:
                        rowO = 0;
                        rowX = 0;
                    default:
                        break;
                }
                if (rowX > rowStatesX[xAxis]) {
                    rowStatesX[xAxis] = rowX;
                }
                if (rowO > rowStatesO[xAxis]) {
                    rowStatesO[xAxis] = rowO;
                }
            }
        }

        for (int yAxis = 0; yAxis < getFieldSize(); yAxis++) {
            int colX = 0;
            int colO = 0;
            for (int xAxis = 0; xAxis < getFieldSize(); xAxis++) {
                switch (getSiteState(xAxis, yAxis)) {
                    case X:
                        colO = 0;
                        colX++;
                        break;
                    case O:
                        colO++;
                        colX = 0;
                        break;
                    case EMPTY:
                        colO = 0;
                        colX = 0;
                    default:
                        break;
                }
                if (colX > colStatesX[yAxis]) {
                    colStatesX[yAxis] = colX;
                }
                if (colO > colStatesO[yAxis]) {
                    colStatesO[yAxis] = colO;
                }
            }
        }

        for (int lu = 0; lu < getFieldSize() * 2 - 1; lu++) {
            int dX = 0;
            int dO = 0;
            for (int yAxis = Math.min(lu, getFieldSize() - 1), xAxis = lu - yAxis;
                 xAxis < getFieldSize() && yAxis >= 0; xAxis++, yAxis--) {
                switch (getSiteState(xAxis, yAxis)) {
                    case X:
                        dX++;
                        dO = 0;
                        break;
                    case O:
                        dX = 0;
                        dO++;
                        break;
                    case EMPTY:
                        dX = 0;
                        dO = 0;
                        break;
                    default:
                        break;
                }
                if (dX > diagonalStatesLUX[lu]) {
                    diagonalStatesLUX[lu] = dX;
                }
                if (dO > diagonalStatesLUO[lu]) {
                    diagonalStatesLUO[lu] = dO;
                }
            }
        }

        for (int ru = 0; ru < getFieldSize() * 2 - 1; ru++) {
            int dX = 0;
            int dO = 0;
            for (int xAxis = Math.max(0, ru - getFieldSize()), yAxis = Math.max(0, getFieldSize() - 1 - ru);
                 xAxis < getFieldSize() && yAxis < getFieldSize(); xAxis++, yAxis++) {
                switch (getSiteState(xAxis, yAxis)) {
                    case X:
                        dX++;
                        dO = 0;
                        break;
                    case O:
                        dX = 0;
                        dO++;
                        break;
                    case EMPTY:
                        dX = 0;
                        dO = 0;
                    default:
                        break;
                }
                if (dX > diagonalStatesRUX[ru]) {
                    diagonalStatesRUX[ru] = dX;
                }
                if (dO > diagonalStatesRUO[ru]) {
                    diagonalStatesRUO[ru] = dO;
                }
            }
        }

        /*
         * Check whether X or Y wins.
         */
        boolean xWins = false;
        boolean oWins = false;

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
            stringBuilder.append(spaces(1 + 2 * ((i + this.getFieldSize()) % 2)));
            stringBuilder.append(String.format("%2d", this.getFieldSize() - i));
            if ((i + this.getFieldSize() + 1) % 2 == 1) {
                stringBuilder.append("--");
            }
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

        for (i = 1; i <= this.getFieldSize(); ++i) {
            if (i % 2 == 1) {
                stringBuilder.append(String.format("%2d", i));
            } else {
                stringBuilder.append(" |");
            }
        }
        stringBuilder.append('\n');

        stringBuilder.append(spaces(6));
        for (i = 1; i <= this.getFieldSize(); ++i) {
            if (i % 2 == 1) {
                stringBuilder.append(spaces(2));
            } else {
                stringBuilder.append(String.format("%2d", i));
            }
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
