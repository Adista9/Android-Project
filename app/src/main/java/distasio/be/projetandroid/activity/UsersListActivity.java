package distasio.be.projetandroid.activity;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import distasio.be.projetandroid.R;
import distasio.be.projetandroid.User;
import distasio.be.projetandroid.arrayadapter.UserAdapter;
import distasio.be.projetandroid.asynctask.AsyncUserList;
import distasio.be.projetandroid.asynctask.CustomScoreUser;
import distasio.be.projetandroid.singleton.UserList;

public class UsersListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        try {
            new AsyncUserList(UsersListActivity.this).execute();
        } catch (Exception e){
            showMessage(e.getMessage());
        }
    }

    public void populate(Integer resultCode) {
        switch (resultCode) {
            case 0:
                showMessage("Aucun problème. ");
                UserList userListSingleton = UserList.getInstance();
                ArrayList<User> userList = new ArrayList<User>();

                for (int i = 0; i < userListSingleton.getUserList().size(); i++)
                    userList.add(userListSingleton.getUserList().get(i));

                UserAdapter adapter = new UserAdapter(this, userList);
                ListView listView = (ListView) findViewById(R.id.lv_users);
                listView.setAdapter(adapter);
                break;
            case 300:
                showMessage("Aucun pseudo trouvé (la table des scores est vide pour le moment).");
                break;
            case 1000:
                showMessage("Problème de connexion à la DB. ");
                break;
            case 2000:
                showMessage("Un problème autre est survenu. ");
                break;
        }
        if(resultCode == null)
            showMessage("Aucun score trouvé. ");
    }
    private void showMessage(String message) {
        CharSequence text = message;
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
