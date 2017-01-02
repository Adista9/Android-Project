package distasio.be.projetandroid.asynctask;

/**
 * Created by Anthony on 01-01-17.
 */

public class CustomScoreUser {
    private int code;
    private String username;
    private int score;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

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
                ", username='" + username + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}