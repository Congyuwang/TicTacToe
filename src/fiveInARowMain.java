import fieldGames.fiveInARow.*;
import fieldGames.*;

public class fiveInARowMain {
    public static void main(String[] args) {
        Game game = new FiveInARowGame(10, 5);
        game.play();
    }
}
