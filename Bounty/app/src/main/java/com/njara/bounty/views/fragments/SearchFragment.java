package com.njara.bounty.views.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njara.bounty.services.IProductService;
import com.njara.bounty.services.ProductService;
import com.njara.bounty.views.ProductItemDecoration;
import com.njara.bounty.R;
import com.njara.bounty.models.Product;

/**
 * Created by njara on 13/02/2018.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        SearchView.OnQueryTextListener {

    private String query;
    private IProductService productService;
    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.productService =new ProductService(getActivity().getApplicationContext());

        SearchView searchEdit=(SearchView)getActivity().findViewById(R.id.searchView);
		searchEdit.setOnQueryTextListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_search, container, false);

        this.recyclerView=(RecyclerView) view.findViewById(R.id.search_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ProductItemDecoration(getActivity(),2, 10, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        query = !TextUtils.isEmpty(newText) ? newText : null;

        if(query!=null && !query.contentEquals("\"")){
          //  getLoaderManager().restartLoader(0, null,this );
            this.LoadResult(query);
        }
        return true;
    }

    public void LoadResult( String query){

        Product product=new Product(query);


        this.productService.Find(recyclerView,product);

    }
}
