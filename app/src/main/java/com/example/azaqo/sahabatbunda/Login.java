package com.example.azaqo.sahabatbunda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.azaqo.sahabatbunda.MainMenu.MainActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    @BindView(R.id.input_username)
    EditText unameinput;

    @BindView(R.id.input_password)
    EditText inputpasswd;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        ButterKnife.bind(this);

        preferences = getSharedPreferences("MAIN",MODE_PRIVATE);
        editor = preferences.edit();

        String logedin = preferences.getString("USERNAME","kosong");
        if (!logedin.equals("kosong")){
            Log.d("PHP", "onCreate login: "+logedin);
            //start main activity
            Intent ten  = new Intent(this,MainActivity.class);
            ten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(ten);
        }
    }

    @OnClick(R.id.btn_login)
    public void login(){
        HashMap<String,String> data = new HashMap<>();
        data.put("username",unameinput.getText().toString());
        data.put("passwd",inputpasswd.getText().toString());
        new Request(data,"http://sahabatbundaku.org/request_android/do_login.php").execute();
    }

    @OnClick(R.id.link_signup)
    public void toSignup(){
        startActivity(new Intent(this,Signup.class));
    }

    public class Request extends PostRequest{

        public Request(HashMap<String, String> data, String url) {
            super(data, url);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("PHP", "onPostExecute: "+s);
            super.onPostExecute(s);
            if (!s.equals("1")){
                //login berhasil start main activity
                Log.d("PHP", "login: "+s);
                editor.putString("USERNAME",s);
                editor.commit();
                Intent ten  = new Intent(Login.this,MainActivity.class);
                ten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ten);

            } else{
                Toast.makeText(Login.this,R.string.login_failed,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
