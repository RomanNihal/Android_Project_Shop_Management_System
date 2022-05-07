package com.example.u_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Sign_Up extends AppCompatActivity {

    private static final Pattern pass_pattern = Pattern.compile("^"+"(?=.*[@#$%^&+=])"+"(?=\\S+$)"+".{6,}"+"$");

    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    TextView t6;
    TextView t7;

    EditText et1;
    EditText et2;
    EditText et3;

    CheckBox c1;

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        t1 = findViewById(R.id.tvsup);
        t2 = findViewById(R.id.tvname);
        t3 = findViewById(R.id.tvmail);
        t4 = findViewById(R.id.tvpass);
        t5 = findViewById(R.id.tvterms);
        t6 = findViewById(R.id.tvhac);
        t7 = findViewById(R.id.tvsignin);

        et1 = findViewById(R.id.etename);
        et2 = findViewById(R.id.etemail);
        et3 = findViewById(R.id.etepass);

        c1 = findViewById(R.id.caggree);

        b1 = findViewById(R.id.bcreate);
    }

    public void create_account(View v){
        Intent to = new Intent(this,MainActivity.class);

        Data_base d = new Data_base(this);

        String name = et1.getText().toString().trim();
        String mail = et2.getText().toString().trim();
        String pass = et3.getText().toString().trim();

        int flag=0;
        if(name.isEmpty()){
            et1.setError("Can't be empty");
        }
        else if(name.length()>15){
            et1.setError("Too long");
        }
        else{
            et1.setError(null);
            flag++;
        }
        if(mail.isEmpty()){
            et2.setError("Can't be empty");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            et2.setError("Enter a verified mail");
        }
        else{
            et2.setError(null);
            flag++;
        }
        if(pass.isEmpty()){
            et3.setError("Can't be empty");
        }
        else if(!pass_pattern.matcher(pass).matches()){
            et3.setError("Password is too weak");
        }
        else{
            et3.setError(null);
            flag++;
        }
        boolean z = c1.isChecked();
        if(z){
            flag++;
        }
        else{
            Toast.makeText(this,"Fill The Check Box",Toast.LENGTH_SHORT).show();
        }
        if(flag==4){
            d.insert(name,mail,pass);
            startActivity(to);
            Toast.makeText(this,"Your Account Has Been Created",Toast.LENGTH_SHORT).show();
        }
    }
    public void terms_condition(View v){
        Toast.makeText(this,"Under Develop",Toast.LENGTH_SHORT).show();
    }
    public void sign_in(View v){
        finish();
    }
}