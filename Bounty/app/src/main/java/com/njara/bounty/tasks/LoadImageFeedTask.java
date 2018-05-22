package com.njara.bounty.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by njara on 06/02/2018.
 */
public class LoadImageFeedTask extends AsyncTask<String, Void, Bitmap> {

    ImageView bmImage;

    public LoadImageFeedTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }
    @Override
    protected Bitmap doInBackground(String... params) {

        String urldisplay = params[0];
        Bitmap bmp = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bmp;

    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
