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
import java.util.ArrayList;

import distasio.be.projetandroid.User;
import distasio.be.projetandroid.activity.LoginActivity;


/**
 * Created by Anthony on 25-12-16.
 */

public class AsyncRegister extends AsyncTask<String, Void , ArrayList> {
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
    protected ArrayList doInBackground(String... params) {
        User registeredUser = new User();
        String name;
        int code = 10;
        ArrayList<Integer> res = new ArrayList<>();

        registeredUser.setUsername(params[0]);
        registeredUser.setPassword(params[1]);

        String url_base = "http://androidproject.16mb.com/RPC/creer_compte.php?";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(url_base);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            BufferedWriter writer = new BufferedWriter(osw);
            writer.write("pseudo=" + registeredUser.getUsername() + "&mdp=" + registeredUser.getPassword());
            writer.flush();
            writer.close();
            connection.connect();

            if(connection.getResponseCode() == 200) {
                JsonReader json = new JsonReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                json.beginObject();
                if (json.hasNext()) {
                    name = json.nextName();
                    if (name.equals("code")) {
                        code = json.nextInt();
                        res.add(code);
                    }
                    if(code == 0) {
                        // #TODO à corriger
                        if (json.hasNext()) {
                            String name2 = json.nextName();
                            int id = json.nextInt();
                            res.add(id);
                        }
                    }
                }
                json.endObject();
                return res;
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
        return res;
    }

    @Override
    protected void onPostExecute(ArrayList result) {
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        loginActivity.populateRegister(result);
    }
}
