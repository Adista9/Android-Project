package distasio.be.projetandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import distasio.be.projetandroid.R;
import distasio.be.projetandroid.asynctask.AsyncAddScore;
public class AddScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        Button btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(checkAddScore);
    }

    private View.OnClickListener checkAddScore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText et_game = (EditText)findViewById(R.id.et_game_name);
            EditText et_score = (EditText) findViewById(R.id.et_score);
            Intent intent = getIntent();
            String pseudo = intent.getStringExtra("pseudo");
            String mdp = intent.getStringExtra("mdp");
            int id_utilisateur = intent.getIntExtra("id_utilisateur", 0);
            try {
                new AsyncAddScore(AddScoreActivity.this).execute(et_game.getText().toString(), et_score.getText().toString(), Integer.toString(id_utilisateur));
            } catch (Exception e){
                showMessage(e.getMessage());
            }
        }
    };

    public void populate(String resultCode) {
        switch (resultCode) {
            case "0":
                showMessage("Score ajouté. ");
                Intent intentReceived = getIntent();
                Intent intent = new Intent(AddScoreActivity.this, MenuActivity.class);
                intent.putExtra("pseudo", intentReceived.getStringExtra("pseudo"));
                intent.putExtra("mdp", intentReceived.getStringExtra("mdp"));
                intent.putExtra("id_utilisateur", intentReceived.getIntExtra("id_utilisateur", 0));
                startActivity(intent);
                break;
            case "100":
                showMessage("Problème de score (non transmis ou 0). ");
                break;
            case "110":
                showMessage("Problème de nom du jeu (non transmis ou vide). ");
                break;
            case "120":
                showMessage("Problème d'id (non transmis ou 0). ");
                break;
            case "1000":
                showMessage("Problème de connexion à la DB. ");
                break;
            case "2000":
                showMessage("Un problème autre est survenu. ");
                break;
        }
        if(resultCode.length()==2) {
            showMessage("Score ajouté. ");
            Intent intentReceived = getIntent();
            Intent intent = new Intent(AddScoreActivity.this, MenuActivity.class);
            intent.putExtra("pseudo", intentReceived.getStringExtra("pseudo"));
            intent.putExtra("mdp", intentReceived.getStringExtra("mdp"));
            intent.putExtra("id_utilisateur", intentReceived.getIntExtra("id_utilisateur", 0));
            startActivity(intent);
        }
    }
    private void showMessage(String message) {
        CharSequence text = message;
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
