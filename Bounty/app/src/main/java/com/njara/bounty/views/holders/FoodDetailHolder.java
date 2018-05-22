package com.njara.bounty.views.holders;

import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njara.bounty.R;
import com.njara.bounty.models.Product;
import com.njara.bounty.tasks.LoadImageFeedTask;

/**
 * Created by njara on 12/03/2018.
 */
public class FoodDetailHolder {

    public TextView title ;

    public ImageView topImage;

    public CollapsingToolbarLayout collapsingToolbar;

    public FoodDetailHolder(View itemView ) {

        //title=(TextView) itemView.findViewById(R.id.food_title);

        topImage=(ImageView) itemView.findViewById(R.id.backdrop);

        collapsingToolbar=(CollapsingToolbarLayout)itemView.findViewById(R.id.collapsing_toolbar);

    }
    public void LoadContent(Product food){

        this.collapsingToolbar.setTitle(food.name);
        if(this.topImage!=null && food.thumbnailUrl!=null && !food.thumbnailUrl.isEmpty()){
            new LoadImageFeedTask(this.topImage).execute(food.thumbnailUrl);
        }


    }
}
