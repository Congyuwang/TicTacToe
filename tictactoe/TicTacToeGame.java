package tictactoe;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

class TicTacToeGame implements Game {

    private Field field;

    /**
     * Initialize the game.
     * @param size size of the game.
     */
    TicTacToeGame(int size) {
        field = new TicTacToeField(size);
    }

    /**
     * This is the main interface: just play!
     */
    public void play() {
        FieldState fieldState;
        int round = 0;
        do {
            System.out.print(field);
            if (round % 2 == 0) {
                receiveInput(State.X);
            } else {
                receiveInput(State.O);
            }
            fieldState = field.getFieldState();
            round++;
        } while (fieldState == FieldState.UNFINISHED);
        System.out.print(field);
        System.out.print(fieldState);
    }

    /**
     * receive input and update the field to {@code State s}.
     */
    private void receiveInput(State s) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int x;
            int y;
            while (true) {
		    System.out.printf("Enter the coordinates (%s): ", s.toString());
                String input = scanner.nextLine();
                Scanner scanInts = new Scanner(input);
                try {
                    x = scanInts.nextInt();
                    y = scanInts.nextInt();
                    break;
                } catch (InputMismatchException e1) {
                    System.out.println("You should enter numbers!");
                } catch (NoSuchElementException e2) {
                    System.out.println("You should enter two numbers!");
                }
                scanInts.close();
            }
            State checkState;
            try {
                checkState = field.getSiteState(field.getFieldSize() - y, x - 1);
                if (checkState == State.EMPTY) {
                    field.playState(s, field.getFieldSize() - y, x - 1);
                    break;
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            } catch (IllegalArgumentException e) {
                System.out.printf("Coordinates should be from 1 to %d!\n", field.getFieldSize());
            }
        }
        scanner.close();
    }
}
