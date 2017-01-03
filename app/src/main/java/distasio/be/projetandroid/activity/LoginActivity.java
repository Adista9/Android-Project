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
                v.setOnClickListener(checkRegister);
            }
        });
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
                showMessage(getString(R.string.error_pwd));

        }
    };

    public void populateLogin(ArrayList<Integer> list_result) {
        switch (list_result.get(0)) {
            case 0:
                this.showMessage(getString(R.string.sign_in_ok));
                EditText et_username = (EditText) findViewById(R.id.et_userName);
                EditText et_password = (EditText) findViewById(R.id.et_password1);

                //Si les identifiants sont bons, alors on start la prochaine activity
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                intent.putExtra("username", et_username.getText());
                intent.putExtra("pwd", et_password.getText());
                intent.putExtra("id_user", list_result.get(1));
                startActivity(intent);
                break;
            case 100:
                this.showMessage(getString(R.string.error_username_login));
                break;
            case 110:
                this.showMessage(getString(R.string.error_password_login));
                break;
            case 200:
                this.showMessage(getString(R.string.error_usrpwd_login));
                break;
            case 1000:
                this.showMessage(getString(R.string.error_db));
                break;
            case 2000:
                this.showMessage(getString(R.string.error_other_problem));
                break;
        }
    }

    public void populateRegister(ArrayList<Integer> resultCode) {
        switch (resultCode.get(0)) {
            case 0:
                showMessage(getString(R.string.save_db_ok));
                EditText et_username = (EditText) findViewById(R.id.et_userName);
                EditText et_password = (EditText) findViewById(R.id.et_password1);
                //Si les identifiants sont bons, alors on start la prochaine activity
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                intent.putExtra("pseudo", et_username.getText());
                intent.putExtra("mdp", et_password.getText());
                intent.putExtra("id_utilisateur", resultCode.get(1));
                startActivity(intent);
                break;
            case 100:
                showMessage(getString(R.string.error_username_login));
                break;
            case 110:
                showMessage(getString(R.string.error_password_login));
                break;
            case 200:
                showMessage(getString(R.string.error_exist_username));
                break;
            case 1000:
                showMessage(getString(R.string.error_db));
                break;
            case 2000:
                showMessage(getString(R.string.error_other_problem));
                break;
        }
    }

    private void showMessage(String message) {
        CharSequence text = message;
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
