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
            if (round % 2 == 0) {
                receiveInput(State.X);
            } else {
                receiveInput(State.O);
            }
            fieldState = field.getFieldState();
            round++;
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
     */
    private void receiveInput(State s) {
        while (true) {
            int x;
            int y;
            while (true) {
                System.out.printf("Enter the coordinates (%s): ", s.toString());
                String input = scanner.nextLine();
                if (input.equals("regret")) {
                    if (history.isEmpty()) {
                        System.out.println("No more steps to regret!");
                        continue;
                    }
                    else {
                        int[] step = history.pop();
                        field.playState(State.EMPTY, step[0], step[1]);
                        System.out.println(field);
                        continue;
                    }
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
            }
            State checkState;
            try {
                checkState = field.getSiteState(field.getFieldSize() - y, x - 1);
                if (checkState == State.EMPTY) {
                    field.playState(s, field.getFieldSize() - y, x - 1);
                    history.add(new int[] {field.getFieldSize() - y, x - 1});
                    break;
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            } catch (IllegalArgumentException e) {
                System.out.printf("Coordinates should be from 1 to %d!\n", field.getFieldSize());
            }
        }
    }
}
