package com.example.u_shop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class my_list extends AppCompatActivity {

    GridView gridView;
    ArrayList<product> list;
    product_adapter adapter = null;

    ArrayList<Integer> arrayId = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        gridView = findViewById(R.id.myGridView);
        list = new ArrayList<>();
        adapter = new product_adapter(this, R.layout.product_item, list);
        gridView.setAdapter(adapter);

        String s = getIntent().getStringExtra(Sell.SName);

        Cursor cursor = Buy_Sell.pD.getData("SELECT * FROM PRODUCT");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(2);
            String price = cursor.getString(3);
            byte[] image = cursor.getBlob(5);

            String match = cursor.getString(1);
            if(match.equals(s)){
                list.add(new product(id,name,price,image));
                arrayId.add(cursor.getInt(0));
            }
        }
        adapter.notifyDataSetChanged();
        if(arrayId.isEmpty()){
            Toast.makeText(this, "No Sell Post Yet", Toast.LENGTH_SHORT).show();
        }

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(my_list.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            updateItem(my_list.this,arrayId.get(position));
                        }
                        else{
                            deleteItem(arrayId.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    ImageView uImage;
    private void updateItem(Activity activity, final int id){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update);
        dialog.setTitle("Update");

        uImage = (ImageView) dialog.findViewById(R.id.pUimage);
        final EditText uPrice = (EditText) dialog.findViewById(R.id.etUpprice);
        Button updateB = (Button) dialog.findViewById(R.id.sell_buttonU);
        Button chooseImage = (Button) dialog.findViewById(R.id.choose_buttonU);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width,height);
        dialog.show();

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(my_list.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888);
            }
        });

        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Buy_Sell.pD.updateData(id, uPrice.getText().toString().trim(), Sell.imageByte(uImage));

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception error){
                    Log.e("Update error: ", error.getMessage());
                }
                updateProduct();
            }
        });
    }
    private void deleteItem(final int id){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(my_list.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try{
                    Buy_Sell.pD.deleteData(id);
                    Toast.makeText(getApplicationContext(), "Delete Successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception error){
                    Log.e("error", error.getMessage());
                }
                updateProduct();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }
    private void updateProduct(){
        String s = getIntent().getStringExtra(Sell.SName);
        Cursor cursor = Buy_Sell.pD.getData("SELECT * FROM PRODUCT");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(2);
            String price = cursor.getString(3);
            byte[] image = cursor.getBlob(5);

            String match = cursor.getString(1);
            if(match.equals(s)){
                list.add(new product(id,name,price,image));
                arrayId.add(cursor.getInt(0));
            }
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,888);
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

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                uImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}