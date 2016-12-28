package distasio.be.projetandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                if(!et_password2.getText().equals(""))
                    v.setOnClickListener(checkRegister);
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
                displayMsg(e.getMessage());
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
                    displayMsg(e.getMessage());
                }
            } else
                displayMsg("Les mdp ne correspondent pas.");

        }
    };

    /**
     * Reçoit le résultat de l'appel à la classe AsyncLogin
     * @param resultCode
     */
    public void populateLogin(String resultCode)
    {
        // #TODO equals 0 bizarre
        if(resultCode.equals("0"))
            this.displayMsg("Connexion OK. ");
        else if(resultCode.equals("100"))
            this.displayMsg("Problème de pseudo (non transmis ou vide). ");
        else if(resultCode.equals("110"))
            this.displayMsg("Problème de mot de passe (non transmis ou vide). ");
        else if(resultCode.equals("200"))
            this.displayMsg("Combinaison pseudo/mot de passe incorrecte. ");
        else if(resultCode.equals("1000"))
            this.displayMsg("Problème de connexion à la DB. ");
        else if(resultCode.equals("2000"))
            this.displayMsg("Un problème autre est survenu. ");
    }

    /**
     * Méthode qui reçoit la réponse de la classe AsyncRegister
     * @param resultCode
     */
    public void populateRegister(Integer resultCode)
    {
        /**
         * Récupération des messages dans notre fichier string.xml pour la langue
         */
        String code0    = "Sauvegarde dans la DB OK. ";
        String code100  = "Problème de pseudo (non transmis ou vide). ";
        String code110  = "Problème de mot de passe (non transmis ou vide). ";
        String code200  = "Pseudo déjà existant. ";
        String code1000 = "Problème de connexion à la DB. ";
        String code2000 = "Un problème autre est survenu. ";

        switch (resultCode) {
            case 0: displayMsg(code0);
                break;
            case 100: displayMsg(code100);
                break;
            case 110: displayMsg(code110);
                break;
            case 200: displayMsg(code200);
                break;
            case 1000: displayMsg(code1000);
                break;
            case 2000: displayMsg(code2000);
                break;
        }
    }
    /**
     * Méthode utilitaire qui sert à afficher les messages d'erreurs
     * @param message
     */
    private void displayMsg(String message)
    {
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }
}
