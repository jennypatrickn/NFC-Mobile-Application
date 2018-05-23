package com.njara.bounty.services;

import com.njara.bounty.models.Basket;
import com.njara.bounty.models.Product;

import java.util.List;

/**
 * Created by njara on 22/05/2018.
 */
public class BasketService implements IBasketService {

    public static List<Basket> baskets;

    public static double amount;


    public static boolean isDuplicate(Basket basket,Product panier2){

        if(basket.getProduct().name.equalsIgnoreCase(panier2.name))
            return true;
        else
            return false;
    }

    public static  void addToBasket(Product product , int qt){

        for(int i=0 ;i<baskets.size() ;i++  ){
            if(isDuplicate(baskets.get(i),product)){
                Basket basket=baskets.get(i);
                basket.setQuantity(basket.getQuantity()+1);
                baskets.set(i,basket);
            }
            else{

                baskets.add(new Basket(product, qt));
            }

        }



    }


}
