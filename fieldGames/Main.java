package fieldGames;

// import fieldGames.tictactoe.*;
import fieldGames.fiveInARow.*;

public class Main {
    public static void main(String[] args) {
        // Game game = new TicTacToeGame(3);
        Game game = new FiveInARowGame(11, 5);
        game.play();
    }
}
