package distasio.be.projetandroid.singleton;

import java.util.ArrayList;

import distasio.be.projetandroid.asynctask.CustomScoreUser;
/**
 * Created by Anthony on 01-01-17.
 */

public final class TopList {

    private static TopList instance = null;

    private ArrayList<CustomScoreUser> scoreList = new ArrayList<CustomScoreUser>();

    private TopList() {
        super();
    }

    public final static TopList getInstance() {
        if (instance == null) {
            instance = new TopList();
        }
        return instance;
    }

    public ArrayList<CustomScoreUser> getTopList() {
        return scoreList;
    }

    public void setTopList(ArrayList<CustomScoreUser> scoreList) {
        this.scoreList = scoreList;
    }

    public void remove(CustomScoreUser customScoreUser) {
        this.scoreList.remove(customScoreUser);
    }

    public void add(CustomScoreUser customScoreUser) {
        this.scoreList.add(customScoreUser);
    }

    public  void clearList()
    {
        this.scoreList = new ArrayList<CustomScoreUser>();
    }
}
