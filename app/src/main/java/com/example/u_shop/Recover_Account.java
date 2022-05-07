package com.example.u_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Recover_Account extends AppCompatActivity {

    TextView t1;
    EditText et1;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_account);
        t1 = findViewById(R.id.tvinfo);
        et1 = findViewById(R.id.etmail);
        b1 = findViewById(R.id.bsend);
    }
}