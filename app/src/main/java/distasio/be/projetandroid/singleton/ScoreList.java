package distasio.be.projetandroid.singleton;

import java.util.ArrayList;

import distasio.be.projetandroid.Score;

/**
 * Created by Anthony on 28-12-16.
 */
public class ScoreList {
    private static ScoreList instance = null;

    private ArrayList<Score> scoreList = new ArrayList<Score>();

    private ScoreList() {
        super();
    }

    public final static ScoreList getInstance() {
        if (instance == null) {
            instance = new ScoreList();
        }
        return instance;
    }

    public ArrayList<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(ArrayList<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public void remove(Score score) {
        this.scoreList.remove(score);
    }

    public void add(Score score) {
        this.scoreList.add(score);
    }

    public  void clearList()
    {
        this.scoreList = new ArrayList<Score>();
    }
}

