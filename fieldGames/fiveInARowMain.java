package fieldGames;

import fieldGames.fiveInARow.*;

public class fiveInARowMain {
    public static void main(String[] args) {
        Game game = new FiveInARowGame(11, 5);
        game.play();
    }
}
