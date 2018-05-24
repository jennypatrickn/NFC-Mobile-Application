package com.njara.bounty.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.njara.bounty.R;
import com.njara.bounty.listeners.RecyclerViewListener;
import com.njara.bounty.models.Basket;

/**
 * Created by njara on 23/05/2018.
 */
public class BasketHolder extends RecyclerView.ViewHolder {

    public TextView txt_designation , qt ,sum_amount;

    private Basket basket;

    public void setBasket(Basket basket){

        this.basket=basket;
    }

    public Basket getBasket(){
        return this.basket;
    }
    public BasketHolder(View itemView , RecyclerViewListener recyclerViewListener) {
        super(itemView);

        txt_designation=(TextView) itemView.findViewById(R.id.txt_designation);
        qt=(TextView) itemView.findViewById(R.id.qt);
        sum_amount=(TextView) itemView.findViewById(R.id.sum_amount);

        itemView.setOnClickListener(recyclerViewListener);

    }

    public BasketHolder(View itemView) {
        super(itemView);
        txt_designation=(TextView) itemView.findViewById(R.id.txt_designation);
        qt=(TextView) itemView.findViewById(R.id.qt);
        sum_amount=(TextView) itemView.findViewById(R.id.sum_amount);


    }


    public void Init(Basket basket){

        setBasket(basket);
    }
}
