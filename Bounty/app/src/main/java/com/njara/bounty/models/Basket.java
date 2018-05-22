package com.njara.bounty.models;

/**
 * Created by njara on 22/05/2018.
 */
public class Basket {

    private Product product ;

    private int quantity;

    public Product getProduct(){

        return this.product;
    }
    public void setProduct(Product product){
        this.product=product;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int qt){

        this.quantity=qt;
    }

    public Basket(){}

    public Basket(Product product , int qt){
        this.product=product;
        this.quantity=qt;
    }

}
