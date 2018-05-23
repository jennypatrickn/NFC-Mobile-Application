package com.njara.bounty.providers;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.njara.bounty.models.Product;
import com.njara.bounty.views.adapters.ProductAdapter;

import java.util.List;

/**
 * Created by njara on 16/05/2018.
 */
public class SearchProvider implements ChildEventListener {
    private List<Product> productList;
    private Context context;
    private Activity activity;
    private Product criteria;
    private RecyclerView recyclerView;
    public SearchProvider(){}

    public SearchProvider(RecyclerView recyclerView,List<Product> productList, Activity context, Product foodCriteria){
            this.recyclerView=recyclerView;
            this.productList=productList;
            this.context=context.getApplicationContext();
            this.activity=context;
            this.criteria=foodCriteria;

    }
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

        Product product = dataSnapshot.getValue(Product.class);

        //productList.clear();

        if(product.IsExist(criteria) &&  !productList.contains(product)){

            productList.add(product);
        }

        ProductAdapter foodAdapter = new ProductAdapter(this.activity, productList);
        recyclerView.setAdapter(foodAdapter);


    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

       /* Log.d("RealTimeDatabase", "On child changed " +  dataSnapshot.getValue());
        foodList.clear();
        FoodAdapter foodAdapter = new FoodAdapter(context, foodList);
        recyclerView.setAdapter(foodAdapter);*/

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w("RealTimeDatabase", "Failed to read value.", databaseError.toException());

    }
}
