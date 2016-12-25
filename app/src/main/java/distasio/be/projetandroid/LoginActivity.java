package distasio.be.projetandroid;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                    v.setOnClickListener(checkRegister);
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
            TextView tv_res = (TextView)findViewById(R.id.tv_Result);
            EditText et_username = (EditText)findViewById(R.id.et_userName);
            EditText et_password = (EditText) findViewById(R.id.et_password1);
            try{
                new AsyncLogin(LoginActivity.this).execute(et_username.getText().toString(), et_password.getText().toString());
            }
            catch (Exception e){
                tv_res.setText(e.getMessage());
            }
        }
    };
    //Register part
    private View.OnClickListener checkRegister = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            TextView tv_Result = (TextView)findViewById(R.id.tv_Result);
            EditText et_userName = (EditText)findViewById(R.id.et_userName);
            EditText et_password1 = (EditText) findViewById(R.id.et_password1);
            EditText et_password2 = (EditText) findViewById(R.id.et_password2);
            if(et_password1.getText().toString().equals(et_password2.getText().toString())){
                try{
                    new AsyncRegister(LoginActivity.this).execute(et_userName.getText().toString(), et_password1.getText().toString());
                }
                catch (Exception e){
                    tv_Result.setText(e.getMessage());
                }
            } else
                tv_Result.setText("Les mdp ne correspondent pas.");

        }
    };

    public void populateLogin(String res) {
        TextView tv_Result = (TextView) findViewById(R.id.tv_Result);
        String colorCode = "#";
        if (res.equals("OK")) {
            tv_Result.setText("Vous avez accès à l'application");
            colorCode += "00ff00";
        } else {
            tv_Result.setText("[CONNEXION IMPOSSIBLE] " + res);
            colorCode += "ff0000";
        }
        tv_Result.setTextColor(Color.parseColor(colorCode));
    }

    public void populateRegister(String res) {
        TextView tv_Result = (TextView) findViewById(R.id.tv_Result);
        String colorCode = "#";
        if (res.equals("OK")) {
            tv_Result.setText("Vous avez accès à l'application");
            colorCode += "00ff00";
        } else {
            tv_Result.setText("[CONNEXION IMPOSSIBLE] " + res);
            colorCode += "ff0000";
        }
        tv_Result.setTextColor(Color.parseColor(colorCode));
    }
}
