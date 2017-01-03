package distasio.be.projetandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import distasio.be.projetandroid.R;

public class MenuActivity extends AppCompatActivity {
    Button btn_add_score;
    Button btn_show_top10;
    Button btn_show_games;
    Button btn_show_users;

    View.OnClickListener onclick_add_score = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentReceived = getIntent();
            Intent intentSend = new Intent(v.getContext(), AddScoreActivity.class);
            intentSend.putExtra("username", intentReceived.getStringExtra("username"));
            intentSend.putExtra("pwd", intentReceived.getStringExtra("pwd"));
            intentSend.putExtra("id_user", intentReceived.getIntExtra("id_user", 0));
            startActivity(intentSend);
        }
    };

    View.OnClickListener onclick_show_top = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), TopListActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener onclick_show_games = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), GamesListActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener onclick_show_users = new View.OnClickListener() {
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
        String username = intent.getStringExtra("username");
        String pwd = intent.getStringExtra("pwd");
        int id_user = intent.getIntExtra("id_user", 0);
        //Log.d("test ", pseudo + " " + mdp + " " + id_utilisateur + " ");
        btn_add_score    = (Button) findViewById(R.id.btn_addScore);
        btn_show_top10   = (Button) findViewById(R.id.btn_showTop10);
        btn_show_games   = (Button) findViewById(R.id.btn_showGames);
        btn_show_users   = (Button) findViewById(R.id.btn_showUsers);

        btn_add_score.setOnClickListener (onclick_add_score);
        btn_show_top10.setOnClickListener(onclick_show_top);
        btn_show_games.setOnClickListener(onclick_show_games);
        btn_show_users.setOnClickListener(onclick_show_users);
    }
}
