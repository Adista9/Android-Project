package distasio.be.projetandroid.singleton;

import java.util.ArrayList;

import distasio.be.projetandroid.User;

/**
 * Created by Anthony on 28-12-16.
 */
public class UserList {
        private static UserList instance = null;

        private ArrayList<User> userList = new ArrayList<User>();

        private UserList() {
            super();
        }

        public final static UserList getInstance() {
            if (instance == null) {
                instance = new UserList();
            }
            return instance;
        }

        public ArrayList<User> getUserList() {
            return userList;
        }

        public void setUserList(ArrayList<User> userList) {
            this.userList = userList;
        }

        public void remove(User user) {
            this.userList.remove(user);
        }

        public void add(User user) {
            this.userList.add(user);
        }

        public  void clearList()
        {
            this.userList = new ArrayList<User>();
        }
}

