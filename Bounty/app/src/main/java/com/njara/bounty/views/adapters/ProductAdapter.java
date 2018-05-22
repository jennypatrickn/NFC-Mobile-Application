package com.njara.bounty.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njara.bounty.R;
import com.njara.bounty.helpers.Constants;
import com.njara.bounty.listeners.RecyclerViewListener;
import com.njara.bounty.models.Product;
import com.njara.bounty.tasks.LoadImageFeedTask;
import com.njara.bounty.views.holders.ProductHolder;

import java.util.List;

/**
 * Created by nfidiman on 05/02/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

    private Context mContext;

    private Activity activity;
    private List<Product> productList;

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card, parent, false);

        return new ProductHolder(itemView,new RecyclerViewListener(activity, Constants.SCREEN_ADD_BASKET));
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
            Product product = this.productList.get(position);
            holder.Init(product);
            holder.title.setText(product.name);
            holder.subTitle.setText(String.valueOf(product.price));
            holder.itemView.setTag(product);
            new LoadImageFeedTask(holder.thumbnail).execute(product.thumbnailUrl);

    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    public ProductAdapter(Activity mContext, List<Product> productList) {
       // this.mContext = mContext;

        this.activity=mContext;
        this.productList = productList;
    }
}
