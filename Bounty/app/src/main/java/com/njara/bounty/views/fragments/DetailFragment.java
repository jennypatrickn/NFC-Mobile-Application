package com.njara.bounty.views.fragments;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.njara.bounty.R;
import com.njara.bounty.services.ProductService;
import com.njara.bounty.services.IProductService;
import com.njara.bounty.tasks.LoadImageFeedTask;

/**
 * Created by njara on 10/03/2018.
 */
public class DetailFragment extends Fragment {

    CollapsingToolbarLayout collapsingToolbar;
    private static final String ARG_ITEM_ID="ARG_ITEM_ID";

    private IProductService feedService;

    private String idItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        feedService=new ProductService();
        setHasOptionsMenu(true);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            idItem=getArguments().getString(ARG_ITEM_ID);
        }
        idItem="1";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_detail, container, false);

       // if (!((CatalogDetailActivity) getActivity()).providesActivityToolbar()) {

       //     ((CatalogDetailActivity) getActivity()).setToolbar((Toolbar) view.findViewById(R.id.toolbar));
       // }

     //   collapsingToolbar=(CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar);

       // collapsingToolbar.setTitle("Buns Chicken");

        feedService.GetFoodDetailsById(view,this.idItem);

        //this.LoadContent(view);

        return view;

    }

    public void LoadContent(View view){

        ImageView topImage=(ImageView)view.findViewById(R.id.backdrop);
        String urlImage="https://firebasestorage.googleapis.com/v0/b/kaly-a9c3a.appspot.com/o/1bun_chicken.jpg?alt=media&token=69cd873d-6885-459b-aea8-36f8dfb55faa";
        new LoadImageFeedTask(topImage).execute(urlImage);
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static DetailFragment newInstance(String itemID) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(DetailFragment.ARG_ITEM_ID, itemID);
        fragment.setArguments(args);
        return fragment;
    }

}
