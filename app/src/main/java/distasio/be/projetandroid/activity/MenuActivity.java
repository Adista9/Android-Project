package distasio.be.projetandroid.activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import distasio.be.projetandroid.R;
import distasio.be.projetandroid.activity.AddScoreActivity;

public class MenuActivity extends AppCompatActivity {
    Button btn_addScore;
    Button btn_showTop10;
    Button btn_showGames;
    Button btn_showUsers;

    View.OnClickListener onClickAddScore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), AddScoreActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener onClickShowTop = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), TopListActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener onClickShowGames = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), GamesListActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener onClickShowUsers = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), UsersListActivity.class);
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_addScore    = (Button) findViewById(R.id.btn_addScore);
        btn_showTop10   = (Button) findViewById(R.id.btn_showTop10);
        btn_showGames   = (Button) findViewById(R.id.btn_showGames);
        btn_showUsers   = (Button) findViewById(R.id.btn_showUsers);

        btn_addScore.setOnClickListener (onClickAddScore);
        btn_showTop10.setOnClickListener(onClickShowTop);
        btn_showGames.setOnClickListener(onClickShowGames);
        btn_showUsers.setOnClickListener(onClickShowUsers);

        getConnectionState();
    }

    /***
     * La méthode getConnectionState test si l'appareil est connecté au réseau et le signal.
     */
    public void getConnectionState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        NetworkInfo.State networkState = networkInfo.getState();
        //String msg_connected = getString(R.string.msg_connected);
        //String msg_impossible = getString(R.string.msg_impossible);
        if (networkState.compareTo(NetworkInfo.State.CONNECTED) == 0) {
            displayMsg("Connection established");
        } else {
            displayMsg("Connection failed");
        }
    }
    /**
     * Méthode utilitaire qui sert à afficher les messages d'erreurs
     * @param message
     */
    private void displayMsg(String message)
    {
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }
}
