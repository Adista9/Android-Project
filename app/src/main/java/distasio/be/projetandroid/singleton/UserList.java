package distasio.be.projetandroid.singleton;

/**
 * Created by Anthony on 28-12-16.
 */
public class UserList {
    private static UserList ourInstance = new UserList();

    public static UserList getInstance() {
        return ourInstance;
    }

    private UserList() {
    }
}
