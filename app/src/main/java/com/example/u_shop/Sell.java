package com.example.u_shop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Sell extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String SName = "name";

    TextView t1;

    EditText et1;
    EditText et2;

    ImageView i1;

    Button b1;
    Button b2;
    Button b3;

    final int code = 999;

    Spinner spinner;
    String[] category = {"Phone","Laptop","PC","Camera","Headphone","Smart Watch"};

    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        t1 = findViewById(R.id.tvpd);
        et1 = findViewById(R.id.etpprice);
        et2 = findViewById(R.id.etpdescription);
        i1 = findViewById(R.id.pimage);
        b1 = findViewById(R.id.choose_button);
        b2 = findViewById(R.id.sell_button);
        b3 = findViewById(R.id.my_list);
        spinner = findViewById(R.id.spinner);
        Intent toMyList = new Intent(this, my_list.class);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Sell.this, android.R.layout.simple_spinner_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        String s = getIntent().getStringExtra(Buy_Sell.BSName);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { ActivityCompat.requestPermissions(Sell.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        code
                );
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Buy_Sell.pD.insert(s,value,et1.getText().toString().trim(),et2.getText().toString().trim(),imageByte(i1));
                    Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                    et1.setText("");
                    et2.setText("");
                    i1.setImageResource(R.drawable.ic_baseline_photo_camera_24);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMyList.putExtra(SName,s);
                startActivity(toMyList);
            }
        });
    }
    public static byte[] imageByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        Bitmap reduceBitmap = ImageResizer.reduceBitmapSize(bitmap, 240000);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        reduceBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == code){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,code);
            }
            else{
                Toast.makeText(getApplicationContext(), "You don't have permission to access files", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == code && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                i1.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        value = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}