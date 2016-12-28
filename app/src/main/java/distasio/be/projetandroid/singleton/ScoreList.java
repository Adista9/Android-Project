package distasio.be.projetandroid.singleton;

/**
 * Created by Anthony on 28-12-16.
 */
public class ScoreList {
    private static ScoreList ourInstance = new ScoreList();

    public static ScoreList getInstance() {
        return ourInstance;
    }

    private ScoreList() {
    }
}
