package com.njara.bounty.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njara.bounty.R;
import com.njara.bounty.appnjara.Util.Utilitaire;
import com.njara.bounty.helpers.Constants;
import com.njara.bounty.listeners.RecyclerViewListener;
import com.njara.bounty.models.Basket;
import com.njara.bounty.views.holders.BasketHolder;

import java.util.List;

/**
 * Created by njara on 23/05/2018.
 */
public class BasketAdapter extends RecyclerView.Adapter<BasketHolder> {

    private Context mContext;

    private Activity activity;
    private List<Basket> basketList;

    @Override
    public BasketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_basket_layout, parent, false);

        return new BasketHolder(itemView,new RecyclerViewListener(activity, Constants.SCREEN_DELETE_BASKET));
    }

    @Override
    public void onBindViewHolder(BasketHolder holder, int position) {
        Basket basket = this.basketList.get(position);
        holder.Init(basket);
        holder.txt_designation.setText(basket.getProduct().name);
        holder.qt.setText("x"+String.valueOf(basket.getQuantity()));
        holder.sum_amount.setText(Utilitaire.format(basket.getProduct().price));

    }


    @Override
    public int getItemCount() {
        return this.basketList.size();
    }

    public BasketAdapter(Activity mContext, List<Basket> basketList) {
        this.activity=mContext;
        this.basketList = basketList;
    }



}
