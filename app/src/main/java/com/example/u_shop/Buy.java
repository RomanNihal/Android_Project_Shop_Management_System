package com.example.u_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Buy extends AppCompatActivity {

    GridView gridView;
    ArrayList<product> list;
    product_adapter adapter = null;

    ArrayList<Integer> arrayId = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        gridView = findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new product_adapter(this, R.layout.product_item, list);
        gridView.setAdapter(adapter);

        String s = getIntent().getStringExtra(Buy_Sell.BSName);

        Cursor cursor = Buy_Sell.pD.getData("SELECT * FROM PRODUCT");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(2);
            String price = cursor.getString(3);
            byte[] image = cursor.getBlob(5);

            String match = cursor.getString(1);
            if(!match.equals(s)){
                list.add(new product(id,name,price,image));
                arrayId.add(cursor.getInt(0));
            }
        }
        adapter.notifyDataSetChanged();
        if(arrayId.isEmpty()){
            Toast.makeText(this, "No Product To Buy", Toast.LENGTH_SHORT).show();
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showDetails(Buy.this,arrayId.get(position));
            }
        });
    }
    private void showDetails(Activity activity, final int id) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.details);
        dialog.setTitle("Details");

        ImageView img = (ImageView) dialog.findViewById(R.id.dImage);
        TextView tv = (TextView) dialog.findViewById(R.id.dtv);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.6);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        Cursor cursor = Buy_Sell.pD.getData("SELECT * FROM PRODUCT");
        list.clear();
        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            String pName = cursor.getString(2);
            String pPrice = cursor.getString(3);
            String pDes = cursor.getString(4);
            byte[] image = cursor.getBlob(5);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

            Data_base d = new Data_base(this);
            Cursor c = d.getInfo();
            String mail = null;
            while (c.moveToNext()){
                String n = c.getString(0);
                if(n.equals(name)){
                    mail = c.getString(1);
                }
            }

            int match = cursor.getInt(0);
            if(match==id){
                img.setImageBitmap(bitmap);
                tv.setText("Owner: "+name+"\nProduct: "+pName+"\nPrice: "+pPrice+"\nContact: "+mail+"\nDescription: "+pDes);
            }
            updateProduct();
        }
    }
    private void updateProduct(){
        String s = getIntent().getStringExtra(Buy_Sell.BSName);

        Cursor cursor = Buy_Sell.pD.getData("SELECT * FROM PRODUCT");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(2);
            String price = cursor.getString(3);
            byte[] image = cursor.getBlob(5);

            String match = cursor.getString(1);
            if(!match.equals(s)){
                list.add(new product(id,name,price,image));
                arrayId.add(cursor.getInt(0));
            }
        }
        adapter.notifyDataSetChanged();
    }
}