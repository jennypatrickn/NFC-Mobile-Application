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
import com.njara.bounty.models.Product;
import com.njara.bounty.services.IProductService;
import com.njara.bounty.services.ProductService;
import com.njara.bounty.views.ProductItemDecoration;
import com.njara.bounty.views.adapters.ProductAdapter;

import java.util.List;

/**
 * Created by nfidiman on 15/05/2018.
 */

public class ProductFragment extends Fragment {

    private IProductService productService;
    private RecyclerView recyclerView;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.productService =new ProductService(getActivity().getApplicationContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_product, container, false);
        this.recyclerView=(RecyclerView) view.findViewById(R.id.feed_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ProductItemDecoration(getActivity(),2, 10, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.productService.GetProducts(recyclerView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }
}
