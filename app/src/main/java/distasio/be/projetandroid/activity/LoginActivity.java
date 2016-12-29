package distasio.be.projetandroid.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import distasio.be.projetandroid.asynctask.AsyncLogin;
import distasio.be.projetandroid.asynctask.AsyncRegister;
import distasio.be.projetandroid.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText et_password2 = (EditText) findViewById(R.id.et_password2);
        //Je rend invisible le champ password2
        et_password2.setVisibility(View.INVISIBLE);
        Button btn_login = (Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(checkLogin);

        Button btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Je rend visible l'edit text du 2ème mdp quand on clique sur register
                EditText et_password2 = (EditText) findViewById(R.id.et_password2);
                et_password2.setVisibility(View.VISIBLE);
                if(!et_password2.getText().equals("")){
                    Button btn_login = (Button)findViewById(R.id.btn_login);
                    btn_login.setVisibility(View.INVISIBLE);
                    v.setOnClickListener(checkRegister);
                } else {
                    Button btn_login = (Button)findViewById(R.id.btn_login);
                    btn_login.setVisibility(View.VISIBLE);
                }



            }
        });

        //btn_register.setOnClickListener(checkRegister);
    }

    //Login part
    private View.OnClickListener checkLogin = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            EditText et_username = (EditText)findViewById(R.id.et_userName);
            EditText et_password = (EditText) findViewById(R.id.et_password1);
            try {
                new AsyncLogin(LoginActivity.this).execute(et_username.getText().toString(), et_password.getText().toString());
            }
            catch (Exception e){
                showMessage(e.getMessage());
            }
        }
    };
    //Register part
    private View.OnClickListener checkRegister = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            EditText et_userName = (EditText)findViewById(R.id.et_userName);
            EditText et_password1 = (EditText) findViewById(R.id.et_password1);
            EditText et_password2 = (EditText) findViewById(R.id.et_password2);
            if(et_password1.getText().toString().equals(et_password2.getText().toString())){
                try{
                    new AsyncRegister(LoginActivity.this).execute(et_userName.getText().toString(), et_password1.getText().toString());
                }
                catch (Exception e){
                    showMessage(e.getMessage());
                }
            } else
                showMessage("Les mdp ne correspondent pas.");

        }
    };

    public void populateLogin(String resultCode) {
        /**
         * Problème ici, les codes retours 0 et 200 n'entrent pas dans le switch case
         * j'ai vérifié leurs tailles, en faisant un "resultCode.length()" quand il ressortait le 0, il mettait
         * que la taille est égale à 2, alors qu'il n'y a qu'un chiffre, et pour 200, la taille = 4,
         * je ne comprends pas
         */
        switch (resultCode) {
            case "0":
                this.showMessage("Connexion OK. ");
                break;
            case "100":
                this.showMessage("Problème de pseudo (non transmis ou vide). ");
                break;
            case "110":
                this.showMessage("Problème de mot de passe (non transmis ou vide). ");
                break;
            case "200":
                this.showMessage("Combinaison pseudo/mot de passe incorrecte. ");
                break;
            case "1000":
                this.showMessage("Problème de connexion à la DB. ");
                break;
            case "2000":
                this.showMessage("Un problème autre est survenu. ");
                break;
        }
        if(resultCode.length()==2) {
            this.showMessage("Connexion OK. ");
            EditText et_username = (EditText) findViewById(R.id.et_userName);
            EditText et_password = (EditText) findViewById(R.id.et_password1);
            //Si les identifiants sont bons, alors on start la prochaine activity
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            intent.putExtra("pseudo", et_username.getText());
            intent.putExtra("mdp", et_password.getText());
            startActivity(intent);
        }

        if(resultCode.length()==4)
            this.showMessage("Combinaison pseudo/mot de passe incorrecte. ");
    }

    /**
     * Méthode qui reçoit la réponse de la classe AsyncRegister
     * @param resultCode
     */
    public void populateRegister(ArrayList<Integer> resultCode) {
        //showMessage(resultCode.get(0) + " et " + resultCode.get(1));
        switch (resultCode.get(0)) {
            case 0:
                showMessage("Sauvegarde dans la DB OK. ");
                EditText et_username = (EditText) findViewById(R.id.et_userName);
                EditText et_password = (EditText) findViewById(R.id.et_password1);
                //Si les identifiants sont bons, alors on start la prochaine activity
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                intent.putExtra("pseudo", et_username.getText());
                intent.putExtra("mdp", et_password.getText());
                //intent.putExtra("id", resultCode.get(1));
                startActivity(intent);
                break;
            case 100:
                showMessage("Problème de pseudo (non transmis ou vide). ");
                break;
            case 110:
                showMessage("Problème de mot de passe (non transmis ou vide). ");
                break;
            case 200:
                showMessage("Pseudo déjà existant. ");
                break;
            case 1000:
                showMessage("Problème de connexion à la DB. ");
                break;
            case 2000:
                showMessage("Un problème autre est survenu. ");
                break;
        }
    }
    /**
     * Méthode utilitaire qui sert à afficher les messages d'erreurs
     * @param message
     */
    private void showMessage(String message) {
        CharSequence text = message;
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
