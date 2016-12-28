package distasio.be.projetandroid.singleton;

/**
 * Created by Anthony on 28-12-16.
 */
public class GameList {
    private static GameList ourInstance = new GameList();

    public static GameList getInstance() {
        return ourInstance;
    }

    private GameList() {
    }
}
