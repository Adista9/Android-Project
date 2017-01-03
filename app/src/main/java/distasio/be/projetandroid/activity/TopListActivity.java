package distasio.be.projetandroid.activity;

import android.app.ActionBar;
import android.content.Intent;
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
import distasio.be.projetandroid.singleton.TopList;

public class TopListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_list);

        Button btn_search_top10 = (Button) findViewById(R.id.btn_search_top10);
        btn_search_top10.setOnClickListener(check_top10);

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

    private View.OnClickListener check_top10 = new View.OnClickListener() {
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

    public void populateCustom(Integer result_code) {
        switch (result_code) {
            case 0:
                showMessage(getString(R.string.no_problem));

                for (CustomScoreUser custom : TopList.getInstance().getTopList()){
                    LinearLayout parent = (LinearLayout) findViewById(R.id.activity_top_list);
                    LinearLayout child = new LinearLayout(this);
                    child.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    child.setOrientation(LinearLayout.HORIZONTAL);

                    TextView tv_position = new TextView(this);
                    TextView tv_username = new TextView(this);
                    TextView tv_score = new TextView(this);

                    tv_position.setText(TopList.getInstance().getTopList().indexOf(custom) + 1  + " ");
                    tv_username.setText(custom.getUsername() + " ");
                    tv_score.setText(custom.getScore() + " ");

                    child.addView(tv_position);
                    child.addView(tv_username);
                    child.addView(tv_score);

                    parent.addView(child);
                }
                break;
            case 100:
                showMessage(getString(R.string.no_name_transmitted));
                break;
            case 300:
                showMessage(getString(R.string.score_not_found));
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