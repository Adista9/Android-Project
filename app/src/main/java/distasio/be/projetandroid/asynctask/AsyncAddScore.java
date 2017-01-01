package distasio.be.projetandroid.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import distasio.be.projetandroid.Score;
import distasio.be.projetandroid.activity.AddScoreActivity;

/**
 * Created by Anthony on 26-12-16.
 */

public class AsyncAddScore extends AsyncTask<String, Void, String> {
    private AddScoreActivity addScoreActivity;
    private ProgressDialog progressDialog;

    public AsyncAddScore(AddScoreActivity addScoreActivity){
        this.addScoreActivity = addScoreActivity;
        progressDialog = new ProgressDialog(addScoreActivity);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setTitle("Veuillez patienter.");
        progressDialog.setMessage("Ajout du score en cours...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        Score score = new Score();
        String res = "";
        score.setJeu(params[0]);
        int score_game = Integer.parseInt(params[1].toString());
        int id_utilisateur = Integer.parseInt(params[2].toString());
        String url_base = "http://androidproject.16mb.com/RPC/ajouter_score.php?";
        String url_params = "jeu=" + score.getJeu() + "&score=" + score_game + "&id_utilisateur=" + id_utilisateur;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(url_base + url_params);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode() == 200) {
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());

                Scanner sc = new Scanner(inputStream);
                while (sc.hasNext()) {
                    res += sc.next();
                }
                return res;
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
        return res+ "0tez";
    }

    @Override
    protected void onPostExecute(String result) {
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        addScoreActivity.populate(result);
    }
}
