package com.njara.bounty.listeners;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.njara.bounty.models.Product;
import com.njara.bounty.views.BoiteDialog;
import com.njara.bounty.views.holders.ProductHolder;

import static com.njara.bounty.helpers.Constants.SCREEN_ADD_BASKET;

/**
 * Created by njara on 10/03/2018.
 */
public  class RecyclerViewListener implements OnClickListener {

    private Activity activity;
    private String screen;

    public RecyclerViewListener(Activity activity , String screen){
        this.activity=activity;
        this.screen=screen;
    }
    @Override
    public void onClick(View v) {

            switch ( this.screen) {

                case SCREEN_ADD_BASKET:

                    ProductHolder productHolder=new ProductHolder(v);
                    Product product=(Product)productHolder.itemView.getTag();

                    BoiteDialog boiteDialog =new BoiteDialog(this.activity);

                    boiteDialog.showUpdateQt(product,20);

                    //LinearLayout fragmentBasket = (LinearLayout) this.activity.getLayoutInflater().inflate(R.layout.fragment_basket, null);

                   // TextView winpointText =(TextView) fragmentBasket.findViewById(R.id.win);



                    break;
            }
        }


}

