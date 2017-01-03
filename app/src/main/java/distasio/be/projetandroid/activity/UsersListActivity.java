package distasio.be.projetandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import distasio.be.projetandroid.R;
import distasio.be.projetandroid.User;
import distasio.be.projetandroid.arrayadapter.UserAdapter;
import distasio.be.projetandroid.asynctask.AsyncUserList;
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
        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(onclick_back);
    }

    private View.OnClickListener onclick_back = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), MenuActivity.class);
            startActivity(intent);
        }
    };

    public void populate(Integer resultCode) {
        switch (resultCode) {
            case 0:
                showMessage(getString(R.string.no_problem));
                UserList userListSingleton = UserList.getInstance();
                ArrayList<User> userList = new ArrayList<User>();

                for (int i = 0; i < userListSingleton.getUserList().size(); i++)
                    userList.add(userListSingleton.getUserList().get(i));

                UserAdapter adapter = new UserAdapter(this, userList);
                ListView listView = (ListView) findViewById(R.id.lv_users);
                listView.setAdapter(adapter);
                break;
            case 300:
                showMessage(getString(R.string.user_not_found));
                break;
            case 1000:
                showMessage(getString(R.string.error_db));
                break;
            case 2000:
                showMessage(getString(R.string.error_other_problem));
                break;
        }
    }
    private void showMessage(String message) {
        CharSequence text = message;
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
