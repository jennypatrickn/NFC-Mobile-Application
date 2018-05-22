package com.njara.bounty.services;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.njara.bounty.models.Product;

import java.util.List;

/**
 * Created by nfidiman on 15/05/2018.
 */

public interface IProductService {

    public List<Product> GetProducts(RecyclerView recyclerView);

    public List<Product> Find(final RecyclerView recyclerView , final Product productCriteria);

    public void GetFoodDetailsById(final View view , String id);
}
