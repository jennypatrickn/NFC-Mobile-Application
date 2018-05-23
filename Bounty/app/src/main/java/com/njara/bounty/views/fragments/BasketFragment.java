package com.njara.bounty.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njara.bounty.R;
import com.njara.bounty.services.ProductService;
import com.njara.bounty.views.ProductItemDecoration;

/**
 * Created by nfidiman on 23/05/2018.
 */

public class BasketFragment  extends Fragment {

    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        this.recyclerView=(RecyclerView) view.findViewById(R.id.feed_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

      //  recyclerView.addItemDecoration(new ProductItemDecoration(getActivity(),2, 10, true));
      //  recyclerView.setItemAnimator(new DefaultItemAnimator());

        //this.productService.GetProducts(recyclerView);

        return view;
    }

}
