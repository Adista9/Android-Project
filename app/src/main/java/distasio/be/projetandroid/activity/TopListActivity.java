package distasio.be.projetandroid.activity;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import distasio.be.projetandroid.R;
import distasio.be.projetandroid.asynctask.AsyncTopList;
import distasio.be.projetandroid.asynctask.CustomScoreUser;

public class TopListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_list);

        Button btn_search_top10 = (Button) findViewById(R.id.btn_search_top10);
        btn_search_top10.setOnClickListener(checkTop10);

        EditText et_search_name = (EditText)findViewById(R.id.et_search_name);
        if(et_search_name == null)
            showMessage("Nom du jeu non transmis ou vide.");
    }

    private View.OnClickListener checkTop10 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText et_search_name = (EditText)findViewById(R.id.et_search_name);
            try {
                new AsyncTopList(TopListActivity.this).execute(et_search_name.getText().toString());
            } catch (Exception e){
                showMessage(e.getMessage());
            }
        }
    };

    public void populateCustom(ArrayList<CustomScoreUser> resultCode) {
        switch (resultCode.get(0).getCode()) {
            case 0:
                showMessage("Aucun problème. ");
                for (CustomScoreUser custom : resultCode){
                    LinearLayout parent = (LinearLayout) findViewById(R.id.activity_top_list);
                    LinearLayout child = new LinearLayout(this);
                    child.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    child.setOrientation(LinearLayout.HORIZONTAL);

                    TextView tv_position = new TextView(this);
                    TextView tv_username = new TextView(this);
                    TextView tv_score = new TextView(this);

                    tv_position.setText(resultCode.indexOf(custom) + 1  + " ");
                    tv_username.setText(custom.getUsername() + " ");
                    tv_score.setText(custom.getScore() + " ");

                    child.addView(tv_position);
                    child.addView(tv_username);
                    child.addView(tv_score);

                    parent.addView(child);
                }
                break;
            case 100:
                showMessage("Nom du jeu non transmis ou vide.");
                break;
            case 300:
                showMessage("Aucun score trouvé. ");
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