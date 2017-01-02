package distasio.be.projetandroid.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import distasio.be.projetandroid.User;
import distasio.be.projetandroid.activity.UsersListActivity;
import distasio.be.projetandroid.singleton.UserList;

/**
 * Created by Anthony on 26-12-16.
 */

public class AsyncUserList extends AsyncTask<String, Void, Integer> {
    private UsersListActivity usersListActivity;
    private ProgressDialog progressDialog;

    public AsyncUserList(UsersListActivity usersListActivity){
        this.usersListActivity = usersListActivity;
        progressDialog = new ProgressDialog(usersListActivity);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setTitle("Veuillez patienter.");
        progressDialog.setMessage("Affichage des utilisateurs en cours...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        //Je récupère la liste des utilisateurs
        UserList userList = UserList.getInstance();
        //Je la clear
        userList.clearList();

        int code = 00;

        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://androidproject.16mb.com/RPC/lister_pseudos.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                User user;
                String res = "";
                String name = "";
                JsonReader json = new JsonReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                json.beginObject();
                while (json.hasNext()) {
                    name = json.nextName();
                    if (name.equals("code")) {
                        code = json.nextInt();
                    } else if (name.equals("utilisateurs")) {
                        json.beginArray();
                        while (json.hasNext()) {
                            user = new User();
                            json.beginObject();
                            while(json.hasNext()) {
                                name = json.nextName();
                                if (name.equals("pseudo")) {
                                    res = json.nextString();
                                    user.setUsername(res);
                                }
                            }
                            json.endObject();
                            //Ajout de l'utilisateur dans la list singleton
                            userList.add(user);
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
        usersListActivity.populate(result);
    }
}
