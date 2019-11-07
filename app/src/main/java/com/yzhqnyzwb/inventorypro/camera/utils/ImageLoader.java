package com.yzhqnyzwb.inventorypro.camera.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private static final Map<String, Bitmap> imagesFlyweight = new HashMap();

    public static void loadImg(String url, ImageView imgView){
        Bitmap bm = getBitmapByUrl(url);
        if(bm != null){//if image already exists
            showImage(bm, imgView);
        }else{//if not exists
            loadInBackground(url, imgView);
        }
    }

    private static synchronized void loadInBackground(final String url, final ImageView imgView){
        new Thread(){
            public void run() {//load in background thread
                try {
                    final Bitmap bm = BitmapFactory.decodeStream(new URL(url).openStream());
                    imagesFlyweight.put(url, bm);//store flyweight object - for future reusage
                    imgView.post(new Runnable() {
                        public void run() {//update in UI main thread
                            showImage(bm, imgView);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static synchronized void showImage(Bitmap bm, ImageView imgView){
        imgView.setImageBitmap(bm);
    }

    private static synchronized Bitmap getBitmapByUrl(String url){
        return imagesFlyweight.get(url);
    }
}
