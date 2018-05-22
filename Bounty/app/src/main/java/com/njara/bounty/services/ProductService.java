package com.njara.bounty.services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.njara.bounty.models.Product;
import com.njara.bounty.providers.RealTimeDatabaseProvider;
import com.njara.bounty.providers.SearchProvider;
import com.njara.bounty.views.adapters.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nfidiman on 16/05/2018.
 */

public class ProductService  extends  RealTimeDatabaseProvider implements IProductService {

    private RealTimeDatabaseProvider realTimeDatabaseProvider;;
    private List<Product> productList;
    private Context context;
    public ProductService(){

        this.realTimeDatabaseProvider=new RealTimeDatabaseProvider();
    }

    public ProductService(Context context){

        this.realTimeDatabaseProvider=new RealTimeDatabaseProvider();
        this.context=context;
    }
    @Override
    public List<Product> GetProducts(final RecyclerView recyclerView) {
        productList = new ArrayList<>();

        DatabaseReference dataBaseRef = this.database.getReference("bounty").child("contents");
       // Query searchQuery = dataBaseRef.orderByChild("name").startAt(foodCriteria.name,"name");

        dataBaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("RealTimeDatabase", "Value is 1: " +  dataSnapshot.getValue());
                productList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);

                    productList.add(product);

                }

                ProductAdapter productAdapter = new ProductAdapter(context, productList);
                recyclerView.setAdapter(productAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("RealTimeDatabase", "Failed to read value.", error.toException());
            }

        });
        return null;

    }
    public void GetFoodDetailsById(final View view , String id){


        DatabaseReference dataBaseRef = this.database.getReference("bounty").child("contents");
        Query searchQuery = dataBaseRef.child(id);

        searchQuery.addValueEventListener(new ValueEventListener() {
            Product food=null;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                food = dataSnapshot.getValue(Product.class);
                if(food!=null){

                //    FoodDetailHolder foodDetailHolder=new FoodDetailHolder(view);
                  //  foodDetailHolder.LoadContent(food);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("RealTimeDatabase", "Failed to read value.", error.toException());
            }

        });
    }

    public List<Product> Find(final RecyclerView recyclerView , final Product foodCriteria){
        productList = new ArrayList<>();
        Log.d("value",""+productList.size());
        DatabaseReference dataBaseRef = this.database.getReference("bounty").child("contents");
        Query searchQuery = dataBaseRef.orderByChild("name");

        SearchProvider searchProvider=new SearchProvider(recyclerView,productList,context,foodCriteria);


        searchQuery.addChildEventListener(searchProvider);
        return null;
    }
}
