package com.njara.bounty.models;

import java.util.List;

/**
 * Created by njara on 24/05/2018.
 */
public class Card {

    public int points;

    public String surname;

    public String name;

    public String id;

    public Card(){

    }

    public Card(String surname, String name , int points){
        this.name=name;
        this.surname=surname;
        this.points=points;
    }

    public Card(List<String> informations){
        this.surname=informations.get(0);
        this.name=informations.get(1);
        this.id =informations.get(2);
        this.points=new Integer(informations.get(3));
    }

}
