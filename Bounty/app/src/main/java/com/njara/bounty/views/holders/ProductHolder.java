package com.njara.bounty.views.holders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njara.bounty.R;
import com.njara.bounty.listeners.RecyclerViewListener;
import com.njara.bounty.models.Product;

import java.io.IOException;
import java.net.URL;

/**
 * Created by nfidiman on 12/05/2018.
 */

public class ProductHolder extends RecyclerView.ViewHolder {

    public TextView title , subTitle;
    public ImageView thumbnail , overflow;

    public Product getFood() {
        return product;
    }

    public void setFood(Product product) {
        this.product = product;
    }

    private Product product;
    public ProductHolder(View itemView , RecyclerViewListener recyclerViewListener) {
        super(itemView);
        title=(TextView) itemView.findViewById(R.id.product_title);
        subTitle=(TextView) itemView.findViewById(R.id.product_subTitle);
        thumbnail=(ImageView) itemView.findViewById(R.id.product_thumbnail);
        overflow=(ImageView) itemView.findViewById(R.id.product_overflow);
        itemView.setOnClickListener(recyclerViewListener);
    }

    public ProductHolder(View itemView) {
        super(itemView);
        title=(TextView) itemView.findViewById(R.id.product_title);
        subTitle=(TextView) itemView.findViewById(R.id.product_subTitle);
        thumbnail=(ImageView) itemView.findViewById(R.id.product_thumbnail);
        overflow=(ImageView) itemView.findViewById(R.id.product_overflow);
        this.itemView.setTag(this.getFood().id);
    }
    public void SetThumbnail(String thumbnailUrl){
        Bitmap bmp = null;
        try {
            URL url = new URL(thumbnailUrl);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            thumbnail.setImageBitmap(bmp);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Init(Product product){

        setFood(product);
    }
}
