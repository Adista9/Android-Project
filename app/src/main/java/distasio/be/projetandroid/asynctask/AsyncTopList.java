package distasio.be.projetandroid.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import distasio.be.projetandroid.Score;
import distasio.be.projetandroid.activity.TopListActivity;
import distasio.be.projetandroid.singleton.TopList;

/**
 * Created by Anthony on 26-12-16.
 */

public class AsyncTopList extends AsyncTask<String, Void, ArrayList> {
    private TopListActivity topListActivity;
    private ProgressDialog progressDialog;

    public AsyncTopList(TopListActivity topListActivity){
        this.topListActivity = topListActivity;
        progressDialog = new ProgressDialog(topListActivity);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setTitle("Veuillez patienter.");
        progressDialog.setMessage("Affichage du top 10 en cours...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected ArrayList doInBackground(String... params) {
        Score score = new Score();
        score.setJeu(params[0]);

        TopList topList = TopList.getInstance();
        topList.clearList();

        //Liste des pseudos + scores
        ArrayList<CustomScoreUser> topScoreUserList = new ArrayList<CustomScoreUser>();
        int code = 0;

        String url_base = "http://androidproject.16mb.com/RPC/afficher_top.php?";
        String url_params = "jeu=" + score.getJeu();
        HttpURLConnection connection = null;

        try {
            URL url = new URL(url_base + url_params);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                CustomScoreUser item;
                String res = "";
                String name = "";
                JsonReader json = new JsonReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                json.beginObject();
                while (json.hasNext()) {
                    name = json.nextName();
                    if (name.equals("code")) {
                        code = json.nextInt();
                    } else if (name.equals("joueurs")) {
                        json.beginArray();
                        while (json.hasNext()) {
                            item = new CustomScoreUser();
                            item.setCode(code);
                            json.beginObject();
                            while(json.hasNext()) {
                                name = json.nextName();
                                if (name.equals("pseudo")) {
                                    res = json.nextString();
                                    item.setUsername(res);
                                } else if (name.equals("score")) {
                                    res = String.valueOf(json.nextInt());
                                    item.setScore(Integer.valueOf(res));
                                }
                            }
                            json.endObject();
                            //J'ajoute l'objet crée avec le json
                            topScoreUserList.add(item);
                        }
                        json.endArray();
                    }
                }
                json.endObject();
            }
        }
        catch (Exception e){
            Log.e("RPC", "Exception rencontrée.", e);
        }
        finally {
            try {
                connection.disconnect();
            }
            catch (NullPointerException e) {
                Log.e("Disconnect", "Exception rencontrée.", e);
            }
        }
        return topScoreUserList;
    }

    @Override
    protected void onPostExecute(ArrayList result) {
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if(result.size() != 0)
            topListActivity.populateCustom(result);
    }
}
