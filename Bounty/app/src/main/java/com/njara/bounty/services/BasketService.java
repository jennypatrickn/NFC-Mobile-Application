package com.njara.bounty.services;

import android.util.Log;

import com.njara.bounty.models.Basket;
import com.njara.bounty.models.Card;
import com.njara.bounty.models.Fidelity;
import com.njara.bounty.models.Product;

import java.util.List;

/**
 * Created by njara on 22/05/2018.
 */
public class BasketService implements IBasketService {

    public static List<Basket> baskets;

    public static double amount;

    public static Card card;

    public static Fidelity fidelity;

    public static double discount=0;

    public static boolean valid=false;


    public static boolean isDuplicate(Product basket,Product product){

        if(basket.name.equalsIgnoreCase(product.name)){

            return true;

        }

        else{
            return false;
        }

    }

    public static double getAmountCash(){
        double sum =0;
        for(Basket b : baskets){
            Double price =new Double(b.getProduct().price);

            sum= sum+b.getQuantity()*price;

        }
        amount=sum;
        return sum;
    }

    public static int getPoints(){

        double amount=getAmountCash();
        Fidelity fidelity =new Fidelity();
        if(amount>=fidelity.Excess){
           int point = (int) (amount/fidelity.WinCash);

            return point;
        }

        return 0;
    }

    public static  void addToBasket(Product product , int qt){
        Log.e("Add product",product.name);
        Log.e("Basket Size" ,""+baskets.size());


      //  baskets.add(new Basket(product, qt));
        boolean duplicate=false;
        if(baskets!=null && baskets.size()>0){
            Log.e("Basket " ,""+baskets.get(0).getProduct().id);
            Log.e("Product" ,""+product.id);
            for(int i=0 ;i<BasketService.baskets.size() ;i++  ){

                Basket basket=BasketService.baskets.get(i);
                if(basket.getProduct().id == product.id){


                    basket.setQuantity(basket.getQuantity()+qt);
                    BasketService.baskets.set(i,basket);
                    duplicate=true;
                    break;

                }


            }
        }

        if(!duplicate)
        {
            baskets.add(new Basket(product, qt));
        }




    }


}
