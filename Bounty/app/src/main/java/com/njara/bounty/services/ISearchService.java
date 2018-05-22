package com.njara.bounty.services;

import android.support.v7.widget.RecyclerView;

import com.njara.bounty.models.Product;

import java.util.List;

/**
 * Created by njara on 16/05/2018.
 */
public interface ISearchService {

    public List<Product> Find(final RecyclerView recyclerView , final Product foodCriteria);
}
