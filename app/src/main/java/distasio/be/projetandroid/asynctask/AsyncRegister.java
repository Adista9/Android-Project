package distasio.be.projetandroid.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import distasio.be.projetandroid.User;
import distasio.be.projetandroid.activity.LoginActivity;


/**
 * Created by Anthony on 25-12-16.
 */

public class AsyncRegister extends AsyncTask<String, Void , Integer> {
    private LoginActivity loginActivity;
    private ProgressDialog progressDialog;

    public AsyncRegister(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
        progressDialog = new ProgressDialog(loginActivity);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setTitle("Veuillez patienter.");
        progressDialog.setMessage("Inscription en cours...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        User registeredUser = new User();
        registeredUser.setUsername(params[0]);
        registeredUser.setPassword(params[1]);

        String url_base = "http://androidproject.16mb.com/RPC/creer_compte.php";
        HttpURLConnection connection = null;
        int code = 0;
        try {
            // 1 --- Création de l'objet connection
            URL url = new URL(url_base);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");

            // Starts the query

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            BufferedWriter writer = new BufferedWriter(osw);
            writer.write("pseudo=" + registeredUser.getUsername() + "&mdp=" + registeredUser.getPassword());
            writer.flush();
            writer.close();
            os.close();
            connection.connect();

            if(connection.getResponseCode() == 200) {
                // 2 --- Récupération du contenu JSON
                String name = "";
                JsonReader json = new JsonReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                json.beginObject();
                while (json.hasNext()) {
                    name = json.nextName();
                    if (name.equals("code")) {
                        code = json.nextInt();
                    }
                }
                json.endObject();
            }
        } catch (Exception e) {
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
        loginActivity.populateRegister(result);
    }
}
