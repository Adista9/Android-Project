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
        btn_add.setOnClickListener(check_add_score);
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

    private View.OnClickListener check_add_score = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText et_game = (EditText)findViewById(R.id.et_game_name);
            EditText et_score = (EditText) findViewById(R.id.et_score);

            Intent intent = getIntent();
            String username = intent.getStringExtra("username");
            String pwd = intent.getStringExtra("pwd");
            int id_user = intent.getIntExtra("id_user", 0);

            try {
                new AsyncAddScore(AddScoreActivity.this).execute(et_game.getText().toString(), et_score.getText().toString(), Integer.toString(id_user));
            } catch (Exception e){
                showMessage(e.getMessage());
            }
        }
    };

    public void populate(String resultCode) {
        switch (resultCode) {
            case "0":
                showMessage(getString(R.string.score_added));

                Intent intentReceived = getIntent();
                Intent intent = new Intent(AddScoreActivity.this, MenuActivity.class);
                intent.putExtra("username", intentReceived.getStringExtra("username"));
                intent.putExtra("pwd", intentReceived.getStringExtra("pwd"));
                intent.putExtra("id_user", intentReceived.getIntExtra("id_user", 0));
                startActivity(intent);
                break;
            case "100":
                showMessage(getString(R.string.no_score_transmitted));
                break;
            case "110":
                showMessage(getString(R.string.no_name_game_transmitted));
                break;
            case "120":
                showMessage(getString(R.string.no_id_transmitted));
                break;
            case "1000":
                showMessage(getString(R.string.error_db));
                break;
            case "2000":
                showMessage(getString(R.string.error_other_problem));
                break;
        }

        if(resultCode.length() == 2) {
            showMessage("Score ajouté. ");
            Intent intentReceived = getIntent();
            Intent intent = new Intent(AddScoreActivity.this, MenuActivity.class);
            intent.putExtra("username", intentReceived.getStringExtra("username"));
            intent.putExtra("pwd", intentReceived.getStringExtra("pwd"));
            intent.putExtra("id_user", intentReceived.getIntExtra("id_user", 0));
            startActivity(intent);
        }
    }

    private void showMessage(String message) {
        CharSequence text = message;
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
