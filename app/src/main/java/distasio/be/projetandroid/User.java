package distasio.be.projetandroid;

/**
 * Created by Anthony on 26-12-16.
 */

public class User {
    private int id;
    private String username;
    private String password;

    /***
     * GETTERS AND SETTERS
     */

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
