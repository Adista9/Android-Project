package distasio.be.projetandroid.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import distasio.be.projetandroid.Score;
import distasio.be.projetandroid.User;
import distasio.be.projetandroid.activity.GamesListActivity;
import distasio.be.projetandroid.activity.UsersListActivity;
import distasio.be.projetandroid.singleton.ScoreList;
import distasio.be.projetandroid.singleton.TopList;
import distasio.be.projetandroid.singleton.UserList;

/**
 * Created by Anthony on 26-12-16.
 */

public class AsyncGameList extends AsyncTask<String, Void, Integer> {
    private GamesListActivity gamesListActivity;
    private ProgressDialog progressDialog;

    public AsyncGameList(GamesListActivity gamesListActivity){
        this.gamesListActivity = gamesListActivity;
        progressDialog = new ProgressDialog(gamesListActivity);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setTitle("Veuillez patienter.");
        progressDialog.setMessage("Affichage des jeux en cours...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        //Je récupère la liste des jeux, ainsi que leur pseudo et le meilleur score
        TopList customList = TopList.getInstance();
        //Je la clear
        customList.clearList();

        int code = 00;

        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://androidproject.16mb.com/RPC/lister_jeux.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                CustomScoreUser customItem;
                String res = "";
                String name = "";
                JsonReader json = new JsonReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                json.beginObject();
                while (json.hasNext()) {
                    name = json.nextName();
                    if (name.equals("code")) {
                        code = json.nextInt();
                    } else if (name.equals("jeux")) {
                        json.beginArray();
                        while (json.hasNext()) {
                            customItem = new CustomScoreUser();
                            json.beginObject();
                            while(json.hasNext()) {
                                name = json.nextName();
                                //Je set le nom du jeu
                                if (name.equals("nom_jeu")) {
                                    res = json.nextString();
                                    customItem.setGameName(res);
                                    //Je set le pseudo du joueur
                                } else if (name.equals("pseudo")) {
                                    res = json.nextString();
                                    customItem.setUsername(res);
                                    //Je set le meilleur score
                                } else if (name.equals("score")) {
                                    res = String.valueOf(json.nextInt());
                                    customItem.setScore(Integer.valueOf(res));
                                }
                            }
                            json.endObject();
                            //Ajout de l'objet dans la list singleton
                            customList.add(customItem);
                        }
                        json.endArray();
                    }
                }
                json.endObject();
            }
        } catch (Exception e){
            Log.e("RPC", "Exception rencontrée.", e);
        } finally {
            try {
                connection.disconnect();
            } catch (NullPointerException e) {
                Log.e("Disconnect", "Exception rencontrée.", e);
            }
        }
        return code;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        gamesListActivity.populate(result);
    }
}
