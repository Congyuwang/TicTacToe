package fieldGames;

import fieldGames.tictactoe.*;

public class ticTacToeMain {
    public static void main(String[] args) {
        Game game = new TicTacToeGame(3);
        game.play();
    }
}
