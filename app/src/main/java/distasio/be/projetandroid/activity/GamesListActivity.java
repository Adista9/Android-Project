package distasio.be.projetandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import distasio.be.projetandroid.R;
import distasio.be.projetandroid.arrayadapter.CustomScoreUserAdapter;
import distasio.be.projetandroid.asynctask.AsyncGameList;
import distasio.be.projetandroid.asynctask.CustomScoreUser;
import distasio.be.projetandroid.singleton.TopList;

public class GamesListActivity extends AppCompatActivity {
    private ListView lv_game_user_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        try {
            new AsyncGameList(GamesListActivity.this).execute();
        } catch (Exception e){
            showMessage(e.getMessage());
        }
        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(onclick_back);

        lv_game_user_score = (ListView) findViewById(R.id.lv_game_user_score);
        lv_game_user_score.setOnItemClickListener(listenerListe);
    }

    //J'ouvre le top 10 du jeu sélectionné
    AdapterView.OnItemClickListener listenerListe = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                ListView lv = (ListView) findViewById(R.id.lv_game_user_score);
                CustomScoreUser itemValue = (CustomScoreUser) lv.getItemAtPosition(position);
                Intent intent = new Intent(view.getContext(), TopListActivity.class);
                intent.putExtra("name_game", itemValue.getGameName());
                startActivity(intent);
            } catch (Exception e) {
                Log.v("Error : ", e.getMessage());
            }
        }
    };

    private View.OnClickListener onclick_back = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), MenuActivity.class);
            startActivity(intent);
        }
    };

    public void populate(Integer result_code) {
        switch (result_code) {
            case 0:
                showMessage(getString(R.string.no_problem));

                final Button btn_prev = (Button)findViewById(R.id.btn_previous);
                final Button btn_next = (Button)findViewById(R.id.btn_next);
                TextView title        = (TextView)findViewById(R.id.title);

                TopList custom_list_singleton = TopList.getInstance();
                ArrayList<CustomScoreUser> custom_list = new ArrayList<CustomScoreUser>();

                final int MAX_ITEM = 5;
                final int NBR_ITEM = custom_list_singleton.getTopList().size();

                for (int i = 0; i < MAX_ITEM; i++)
                    custom_list.add(custom_list_singleton.getTopList().get(i));

                CustomScoreUserAdapter adapter = new CustomScoreUserAdapter(this, custom_list);
                ListView listView = (ListView) findViewById(R.id.lv_game_user_score);
                listView.setAdapter(adapter);

                btn_next.setEnabled(false);

                //On gère les pages
                //Si le nombre d'item - le maximum d'item est plus grand que 0 alors j'affiche le bouton next
                if((NBR_ITEM - MAX_ITEM) > 0) {
                    btn_next.setEnabled(true);
                    btn_next.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            TopList custom_list_singleton = TopList.getInstance();
                            ArrayList<CustomScoreUser> custom_list = new ArrayList<CustomScoreUser>();
                            for (int i = MAX_ITEM + 1; i < MAX_ITEM + 6; i++)
                                custom_list.add(custom_list_singleton.getTopList().get(i));
                            CustomScoreUserAdapter adapter = new CustomScoreUserAdapter(GamesListActivity.this, custom_list);
                            ListView listView = (ListView) findViewById(R.id.lv_game_user_score);
                            listView.setAdapter(adapter);
                            // 5 - (10 - 5)
                            if(MAX_ITEM - (NBR_ITEM - 5) > 0)
                                btn_next.setEnabled(true);
                            else
                                btn_next.setEnabled(false);
                            btn_prev.setEnabled(true);
                            btn_prev.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    TopList custom_list_singleton = TopList.getInstance();
                                    ArrayList<CustomScoreUser> custom_list = new ArrayList<CustomScoreUser>();
                                    for (int i = 0; i < MAX_ITEM; i++)
                                        custom_list.add(custom_list_singleton.getTopList().get(i));
                                    CustomScoreUserAdapter adapter = new CustomScoreUserAdapter(GamesListActivity.this, custom_list);
                                    ListView listView = (ListView) findViewById(R.id.lv_game_user_score);
                                    listView.setAdapter(adapter);
                                    btn_prev.setEnabled(false);
                                    btn_next.setEnabled(true);
                                }
                            });
                        }
                    });
                }

                btn_prev.setEnabled(false);

                break;
            case 300:
                showMessage(getString(R.string.game_not_found));
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
