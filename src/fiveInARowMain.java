import fieldGames.fiveInARow.*;
import fieldGames.*;

public class fiveInARowMain {
    public static void main(String[] args) {
        Game game = new FiveInARowGame(15, 6);
        game.play();
    }
}
