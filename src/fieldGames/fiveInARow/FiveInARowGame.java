package fieldGames.fiveInARow;

import fieldGames.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class FiveInARowGame implements Game {

    private final Field field;
    private final Scanner scanner = new Scanner(System.in);
    private Stack<int[]> history = new Stack<>();
    /**
     * provides game FiveInARow
     * @param size size of the field
     * @param nInARow number that decides winning
     */
    public FiveInARowGame(int size, int nInARow) {
        field = new FiveInARowField(size, nInARow);
    }

    /**
     * This is the main interface: just play!
     */
    public void play() {
        try {
            String title = Files.readString(Paths.get("resource/fiveInARow_title"));
            System.out.println(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FieldState fieldState;
        int round = 0;
        do {
            System.out.println(field);
            int status = 0;
            if (round % 2 == 0) {
                status = receiveInput(State.X);
            } else {
                status = receiveInput(State.O);
            }
            fieldState = field.getFieldState();
            switch (status) {
                case 0: case 1:
                    round++;
                    break;
                case -1:
                    return;
                default:
                    break;
            }
        } while (fieldState == FieldState.UNFINISHED);
        System.out.println(field);
        String salute = "";
        switch (fieldState) {
            case X_WINS:
                salute = "resource/x_wins";
                break;
            case O_WINS:
                salute = "resource/o_wins";
                break;
            case DRAW:
                salute = "resource/draw";
                break;
            default:
                break;
        }
        try {
            String title = Files.readString(Paths.get(salute));
            System.out.println(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();
    }

    /**
     * receive input and update the field to {@code State s}.
     * Input two integers as coordinates for normal states.
     * Input {@code exit} or {@code ^D} for exiting the game.
     * Input {@code regret} for regretting steps.
     * @return return 0 if normal, 1 if regret, 2 if no more step to regret, -1 if exit.
     */
    private int receiveInput(State s) {
        while (true) {
            int x;
            int y;
            while (true) {
                System.out.printf("Enter the coordinates (%s): ", s.toString());
                try {
                    String input = scanner.nextLine();
                    if (input.equals("regret")) {
                        if (history.isEmpty()) {
                            System.out.println("No more steps to regret!");
                            return 2;
                        } else {
                            int[] step = history.pop();
                            field.playState(State.EMPTY, step[0], step[1]);
                            return 0;
                        }
                    } else if (input.equals("exit")) {
                        return -1;
                    }
                    Scanner scanInts = new Scanner(input);
                    try {
                        x = scanInts.nextInt();
                        y = scanInts.nextInt();
                        scanInts.close();
                        break;
                    } catch (InputMismatchException e1) {
                        System.out.println("You should enter numbers!");
                    } catch (NoSuchElementException e2) {
                        System.out.println("You should enter two numbers!");
                    }
                    scanInts.close();
                } catch (NoSuchElementException e) {
                    return -1;
                }
            }
            State checkState;
            try {
                checkState = field.getSiteState(field.getFieldSize() - y, x - 1);
                if (checkState == State.EMPTY) {
                    field.playState(s, field.getFieldSize() - y, x - 1);
                    history.add(new int[] {field.getFieldSize() - y, x - 1});
                    return 0;
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            } catch (IllegalArgumentException e) {
                System.out.printf("Coordinates should be from 1 to %d!\n", field.getFieldSize());
            }
        }
    }
}
