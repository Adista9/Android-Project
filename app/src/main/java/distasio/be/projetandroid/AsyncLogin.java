package distasio.be.projetandroid;

import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Anthony on 25-12-16.
 */

public class AsyncLogin extends AsyncTask<String, Void , String> {
    private LoginActivity screen = null;

    /*@Override protected void onPreExecute() {
        // Prétraitement de l'appel

    }
    @Override protected void onProgressUpdate(Y... progress) {
        // Gestion de l'avancement de la tâche

    }*/
    public AsyncLogin(LoginActivity s) {
        screen = s;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            // URL de l'application
            //http://3in.16mb.com/APP11/APP11.php?username=toto&password=test
            //URL url = new URL("http://3in.16mb.com/APP11/APP11.php?username=" + params[0] + "&password="+ params[1]);
            //URL url = new URL("http://3in.16mb.com/APP11/APP11.php?username=toto&password=mdp");
            //URL url = new URL("http://3in.16mb.com/APP11/APP11.php");
            URL url = new URL("http://androidskurt.16mb.com/RPC/se_connecter.php?pseudo=" + params[0] + "&mdp="+ params[1]);

            // instantier l'objet grâce à la méthode "openConnection()"
            HttpURLConnection connection;

            // effectuer une connexion au serveur web spécifié dans l'URL. Retourne un objet de type "URLConnexion".
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            // Fixer le nombre de milisecondes qu'on est prêt à attendre pour effectuer une connection.
            connection.setConnectTimeout(10000);

            connection.connect();
            int responseCode = connection.getResponseCode();

            if (responseCode == 200)
            {
                // Retourne la réponse du serveur
                InputStream in = connection.getInputStream();
                connection.disconnect();
                Scanner scanner = new Scanner(in);
                String strRep = "";
                if (scanner.hasNext()){
                    switch (scanner.next()){
                        case "0"    : strRep = "OK"; break;
                        case "10"   : strRep = "Combinaison 'login/mot de passe' incorrecte"; break;
                        case "20"   : strRep = "Problème avec le login"; break;
                        case "30"   : strRep = "Problème avec le mot de passe"; break;
                    }
                }
                return strRep;
            }
            // Déconnecte la connection
            connection.disconnect();
            //return -1 + "";
            return params[0] + " " + params[1];
        }
        catch (MalformedURLException e) { e.printStackTrace(); }
        catch (Exception e){ e.getStackTrace(); }
        return params[0] + " " + params[1];
    }

    @Override
    protected void onPostExecute(String result) {
        // Callback
        // Renvoie les informations dans la fonction populate du mainactivity
        screen.populateLogin(result);
    }
}