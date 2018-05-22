package com.njara.bounty.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by nfidiman on 04/02/2018.
 */

@IgnoreExtraProperties
public class Product {

    public int id ;
    public String name;
    public String thumbnailUrl;
    public String price;
    public boolean isPromo;
    public Product(){

    }
    public Product(int id , String name,String price , String thumbnailUrl , boolean isPromo){
            this.name=name;
            this.price=price;
            this.isPromo = isPromo;
            this.thumbnailUrl=thumbnailUrl;

    }

    public Product(String query){
        this.name=query;
    }

    public boolean IsExist(Product criteria){
        if(this.name.toLowerCase().startsWith(criteria.name.toLowerCase())){
           return true;
        }
        return false;
    }
}
