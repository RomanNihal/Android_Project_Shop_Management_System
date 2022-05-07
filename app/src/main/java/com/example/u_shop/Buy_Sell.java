package com.example.u_shop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Buy_Sell extends AppCompatActivity {

    public static final String BSName = "name";

    TextView t1;
    TextView t2;

    public static productDetailsDataBase pD;

    ArrayList<Integer> arrayId = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell);
        t1 = findViewById(R.id.tvsell);
        t2 = findViewById(R.id.tvbuy);

        pD = new productDetailsDataBase(this, "list", null, 1);
        Buy_Sell.pD.query("create table if not exists product(Id integer primary key autoincrement, Name TEXT, pName TEXT, pPrice TEXT, pDescription TEXT, Image BLOG)");

    }
    public void Buy(View v){
        String s = getIntent().getStringExtra(MainActivity.Name);
        Intent toBuy = new Intent(this, Buy.class);
        toBuy.putExtra(BSName,s);
        startActivity(toBuy);

    }
    public void sell(View v){
        String s = getIntent().getStringExtra(MainActivity.Name);
        Intent toSell = new Intent(this,Sell.class);
        toSell.putExtra(BSName,s);
        startActivity(toSell);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogExit = new AlertDialog.Builder(Buy_Sell.this);

        dialogExit.setTitle("EXIT!!");
        dialogExit.setMessage("Are you sure?");

        dialogExit.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
            }
        });

        dialogExit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogExit.show();
    }
}