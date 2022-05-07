package com.example.u_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String Name = "name";

    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    TextView t6;

    EditText et1;
    EditText et2;

    ImageView i1;
    ImageView i2;

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.tvlogin);
        t2 = findViewById(R.id.tvforget);
        t3 = findViewById(R.id.tvclick);
        t4 = findViewById(R.id.tvor);
        t5 = findViewById(R.id.tvnoaccount);
        t6 = findViewById(R.id.tvsignup);

        et1 = findViewById(R.id.etname);
        et2 = findViewById(R.id.etpass);

        i1 = findViewById(R.id.igoogle);
        i2 = findViewById(R.id.ifacebook);

        b1 = findViewById(R.id.bsignin);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        finish();
    }

    public void logIn(View v){
        Intent toSellBuy = new Intent(this,Buy_Sell.class);

        Data_base d = new Data_base(this);
        Cursor c = d.getInfo();

        String name = et1.getText().toString().trim();
        String pass = et2.getText().toString().trim();

        int flag=1;
        if(name.isEmpty()){
            et1.setError("Can't be empty");
            flag=0;
        }
        if(pass.isEmpty()){
            et2.setError("Can't be empty");
            flag=0;
        }
        if(flag==1){
            if(c.getCount()==0){
                Toast.makeText(this, "No Account Has Been Created Yet", Toast.LENGTH_LONG).show();
            }
            else{
                int n=0,p=0;
                while(c.moveToNext()){
                    if(name.equals(c.getString(0))){
                        et1.setError(null);
                        if(pass.equals(c.getString(2))){
                            et2.setError(null);
                            toSellBuy.putExtra(Name,name);
                            startActivity(toSellBuy);
                            p=1;
                        }
                        n=1;
                    }
                }
                if(n==0){
                    et1.setError("Invalid User name");
                }
                else if(p==0){
                    et2.setError("Wrong password");
                }
            }
        }
    }
    public void forgetPass(View v){
        Intent to = new Intent(this,Recover_Account.class);
        startActivity(to);

    }
    public void signUp(View v){
        Intent to = new Intent(this,Sign_Up.class);
        startActivity(to);
    }
}