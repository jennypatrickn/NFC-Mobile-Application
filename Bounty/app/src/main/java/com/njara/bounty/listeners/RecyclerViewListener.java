package com.njara.bounty.listeners;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import static com.njara.bounty.helpers.Constants.SCREEN_DETAIL_PAGE;

/**
 * Created by njara on 10/03/2018.
 */
public  class RecyclerViewListener implements OnClickListener {

    private Context activity;
    private String screen;

    public RecyclerViewListener(Context activity , String screen){
        this.activity=activity;
        this.screen=screen;
    }
    @Override
    public void onClick(View v) {

            switch ( this.screen) {

                case SCREEN_DETAIL_PAGE:

                   /* FoodHolder food=new FoodHolder(v);

                    String id=food.itemView.getTag().toString();

                    Intent data=new Intent(this.activity, CatalogDetailActivity.class);
                    data.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    data.putExtra("idItem",id);
                    this.activity.startActivity(data);*/

                    break;
            }
        }


}

