package com.example.proiectechiparacheta.Utils;
import com.example.proiectechiparacheta.Async.AsyncTaskRunner;
import com.example.proiectechiparacheta.Async.Callback;
import com.example.proiectechiparacheta.Async.HttpManager;
import com.example.proiectechiparacheta.models.FidelityCard;
import com.example.proiectechiparacheta.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<FidelityCard> arr;
    Animation scaleUp;

    public CustomAdapter(Context context, ArrayList<FidelityCard> arr) {
        this.context = context;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.rowdesign,parent,false);
        TextView tvId = convertView.findViewById(R.id.idCard);
        TextView tvName = convertView.findViewById(R.id.cardName);
        TextView tvHolder = convertView.findViewById(R.id.cardHolderName);
        TextView tvBarCode = convertView.findViewById(R.id.barCode);
//        ImageView iv1 = convertView.findViewById(R.id.iv1);
//        ImageView iv2 = convertView.findViewById(R.id.iv2);
        ImageView ivFav = convertView.findViewById(R.id.ivFav);

//        ObjectAnimator animation = ObjectAnimator.ofFloat(iv1, "rotationY", 0.0f, 360f);
//        animation.setDuration(10000);
//        animation.setRepeatCount(ObjectAnimator.INFINITE);
//        animation.setInterpolator(new LinearInterpolator());
//
//
//        ObjectAnimator animation2 = ObjectAnimator.ofFloat(iv2, "rotationY", 0.0f, 360f);
//        animation2.setDuration(10000);
//        animation2.setRepeatCount(ObjectAnimator.INFINITE);
//        animation2.setInterpolator(new LinearInterpolator());
//
//        animation.start();
//        animation2.start();
        try{
            ImageView ivLogo;
            String url = "https://logo.clearbit.com/";
            url+=arr.get(position).getName();
            ivLogo = convertView.findViewById(R.id.ivLogo);
            Callable<Bitmap> asyncOperation = new HttpManager(url);
            Callback<Bitmap> mainThreadOperation = backOnThread(ivLogo);
            AsyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);

            tvId.setText(String.valueOf(arr.get(position).getId()));
            tvName.setText(arr.get(position).getName());
            tvHolder.setText(arr.get(position).getCardHolderName());
            tvBarCode.setText(arr.get(position).getBarCode());
            if(arr.get(position).isFav){
                ivFav.setImageResource(android.R.drawable.btn_star_big_on);
            }
            else{
                ivFav.setImageResource(android.R.drawable.btn_star_big_off);
            }


        }
        catch(Exception e){
            System.out.println(e.toString());
        }

        return convertView;
    }

    Callback<Bitmap> backOnThread(ImageView ivLogo){
        return new Callback<Bitmap>() {
            @Override
            public void runResultOnUiThread(Bitmap result) {
                ivLogo.setImageBitmap(result);
            }
        };
    }
}
