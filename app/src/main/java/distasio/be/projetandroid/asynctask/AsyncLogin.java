package distasio.be.projetandroid.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import distasio.be.projetandroid.User;
import distasio.be.projetandroid.activity.LoginActivity;

/**
 * Created by Anthony on 25-12-16.
 */

public class AsyncLogin extends AsyncTask<String, Void, String> {
    private LoginActivity loginActivity;
    private ProgressDialog progressDialog;

    public AsyncLogin(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        progressDialog = new ProgressDialog(loginActivity);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setTitle("Veuillez patienter.");
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
        protected String doInBackground(String... params) {
        User connectedUser = new User();
        String res = "";

        connectedUser.setUsername(params[0]);
        connectedUser.setPassword(params[1]);

        String url_base = "http://androidproject.16mb.com/RPC/se_connecter.php";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(url_base);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");

            // Starts the query

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            BufferedWriter writer = new BufferedWriter(osw);
            writer.write("pseudo=" + connectedUser.getUsername() + "&mdp=" + connectedUser.getPassword());
            writer.flush();
            writer.close();
            os.close();
            connection.connect();
            if(connection.getResponseCode() == 200) {
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream(), "UTF-8");
                Scanner sc = new Scanner(inputStream);

                while (sc.hasNext()) {
                    res += sc.next();
                }
            }
        }
        catch (Exception e){
            Log.e("RPC", "Exception rencontrée.", e);
            //return 100;
        }
        finally {
            try {
                connection.disconnect();
            }
            catch (NullPointerException e) {
                Log.e("Disconnect", "Exception rencontrée.", e);
            }
        }
        return res;
    }

    @Override
    protected void onPostExecute (String result){
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        loginActivity.populateLogin(result);
    }
}