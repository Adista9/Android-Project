package distasio.be.projetandroid.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import distasio.be.projetandroid.User;
import distasio.be.projetandroid.activity.LoginActivity;

/**
 * Created by Anthony on 25-12-16.
 */

public class AsyncLogin extends AsyncTask<String, Void, ArrayList<Integer>> {
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
        protected ArrayList<Integer> doInBackground(String... params) {
        User connectedUser = new User();
        ArrayList<Integer> list_int = new ArrayList<Integer>();

        String name;
        int code = 10;
        int id;

        connectedUser.setUsername(params[0]);
        connectedUser.setPassword(params[1]);

        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://androidproject.16mb.com/RPC/se_connecter.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write("pseudo=" + connectedUser.getUsername() + "&mdp=" + connectedUser.getPassword());
            writer.flush();
            writer.close();
            os.close();
            connection.connect();

            if (connection.getResponseCode() == 200) {
                JsonReader json = new JsonReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                json.beginObject();
                if (json.hasNext()) {
                    name = json.nextName();
                    if (name.equals("code")) {
                        code = json.nextInt();
                        list_int.add(code);
                    }
                    if(code == 0) {
                        String nameid = json.nextName();
                        id = json.nextInt();
                        list_int.add(id);
                    }
                }
                json.endObject();
                return list_int;
            }
        } catch (MalformedURLException e) {

        } catch (IOException ex) {
        }
        return list_int;
    }

    @Override
    protected void onPostExecute(ArrayList<Integer> result) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        loginActivity.populateLogin(result);
    }
}