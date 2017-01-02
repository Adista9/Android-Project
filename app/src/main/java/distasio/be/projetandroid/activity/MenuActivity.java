package distasio.be.projetandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import distasio.be.projetandroid.R;

public class MenuActivity extends AppCompatActivity {
    Button btn_addScore;
    Button btn_showTop10;
    Button btn_showGames;
    Button btn_showUsers;

    View.OnClickListener onClickAddScore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentReceived = getIntent();
            Intent intentSend = new Intent(v.getContext(), AddScoreActivity.class);
            intentSend.putExtra("pseudo", intentReceived.getStringExtra("pseudo"));
            intentSend.putExtra("mdp", intentReceived.getStringExtra("mdp"));
            intentSend.putExtra("id_utilisateur", intentReceived.getIntExtra("id_utilisateur", 0));
            startActivity(intentSend);
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

        //Je récupère l'intent de la page de login
        Intent intent = getIntent();
        String pseudo = intent.getStringExtra("pseudo");
        String mdp = intent.getStringExtra("mdp");
        int id_utilisateur = intent.getIntExtra("id_utilisateur", 0);
        //Log.d("test ", pseudo + " " + mdp + " " + id_utilisateur + " ");
        btn_addScore    = (Button) findViewById(R.id.btn_addScore);
        btn_showTop10   = (Button) findViewById(R.id.btn_showTop10);
        btn_showGames   = (Button) findViewById(R.id.btn_showGames);
        btn_showUsers   = (Button) findViewById(R.id.btn_showUsers);

        btn_addScore.setOnClickListener (onClickAddScore);
        btn_showTop10.setOnClickListener(onClickShowTop);
        btn_showGames.setOnClickListener(onClickShowGames);
        btn_showUsers.setOnClickListener(onClickShowUsers);


    }

    private void showMessage(String message) {
        CharSequence text = message;
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
