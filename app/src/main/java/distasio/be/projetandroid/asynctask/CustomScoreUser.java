package distasio.be.projetandroid.asynctask;

/**
 * Created by Anthony on 01-01-17.
 */

public class CustomScoreUser {
    private int code;
    private String game_name;
    private String username;
    private int score;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getGameName(){ return game_name; }
    public void setGameName(String game_name) { this.game_name = game_name; }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) { this.username = username; }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "CustomScoreUser{" +
                "code='" + code + '\'' +
                ", game name= '" + game_name + '\'' +
                ", username='" + username + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}