package com.example.u_shop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class product_adapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<product> productList;

    public product_adapter(Context context, int layout, ArrayList<product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class viewHolder{
        ImageView Image;
        TextView tName,tPrice;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        viewHolder holder = new viewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.tName = (TextView) row.findViewById(R.id.tPName);
            holder.tPrice = (TextView) row.findViewById(R.id.tPPrice);
            holder.Image = (ImageView) row.findViewById(R.id.iPImage);
            row.setTag(holder);
        }

        else{
            holder = (viewHolder) row.getTag();
        }

        product p = productList.get(i);

        holder.tName.setText(p.getName());
        holder.tPrice.setText(p.getPrice());
        byte[] image = p.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.Image.setImageBitmap(bitmap);

        return row;
    }
}
