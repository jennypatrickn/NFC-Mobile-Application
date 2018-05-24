package com.njara.bounty.listeners;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.njara.bounty.R;
import com.njara.bounty.helpers.Constants;
import com.njara.bounty.models.Basket;
import com.njara.bounty.services.BasketService;
import com.njara.bounty.views.BoiteDialog;
import com.njara.bounty.views.fragments.BasketFragment;
import com.njara.bounty.views.fragments.LoginFragment;
import com.njara.bounty.views.fragments.ProductFragment;
import com.njara.bounty.views.fragments.RegisterFragment;

import java.util.ArrayList;

/**
 * Created by nfidiman on 31/01/2018.
 */

public class ButtonActionListener implements View.OnClickListener {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public int frameId=R.id.content_main_frame;
    private Activity activity;
    public ButtonActionListener(Activity activity , FragmentManager fragmentManager){
        this.activity=activity;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public void onClick(View view) {

        String title="";
        switch ( view.getId()){

            case R.id.link_signup:
                fragmentTransaction = this.fragmentManager.beginTransaction();

                title=this.activity.getResources().getString(R.string.ac_register);
                fragmentTransaction.replace(R.id.content_main_frame, new RegisterFragment(),title).commit();
                break;

            case R.id.link_signin:
                fragmentTransaction = this.fragmentManager.beginTransaction();
                title = this.activity.getResources().getString(R.string.ac_login);
                fragmentTransaction.replace(R.id.content_main_frame, new LoginFragment(),title).commit();
                break;

            case R.id.btn_login:
                boolean status =true;
                if(status){

                    fragmentTransaction = this.fragmentManager.beginTransaction();
                    title = this.activity.getResources().getString(R.string.ac_login);
                    fragmentTransaction.replace(R.id.content_main_frame, new ProductFragment(),title).commit();
                    break;

                }
                break;

            case R.id.btn_header_login:

                fragmentTransaction = this.fragmentManager.beginTransaction();
                title = this.activity.getResources().getString(R.string.ac_login);
                fragmentTransaction.replace(R.id.content_main_frame, new LoginFragment(),title).commit();

                DrawerLayout drawer = (DrawerLayout) this.activity.findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                break;

            case R.id.basketflot:
                if(BasketService.valid==true){
                    fragmentTransaction = this.fragmentManager.beginTransaction();
                    title = this.activity.getResources().getString(R.string.ac_login);
                    fragmentTransaction.replace(R.id.content_main_frame, new BasketFragment(),title).commit();
                }
                else{
                    fragmentTransaction = this.fragmentManager.beginTransaction();
                    title = this.activity.getResources().getString(R.string.ac_login);
                    fragmentTransaction.replace(R.id.content_main_frame, new LoginFragment(),title).commit();

                }

                break;

            case R.id.btn_winpoints:
                BoiteDialog boiteDialog =new BoiteDialog(this.activity);
                boiteDialog.showDialogCard("Test");
                break;

            case R.id.btn_reward:

                BoiteDialog boite =new BoiteDialog(this.activity);
                boite.showDialogCard(Constants.GIFT);
                break;

            case R.id.btn_order:
                BasketService.baskets=new ArrayList<Basket>();
                fragmentTransaction = this.fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main_frame, new ProductFragment(),"Product").commit();
                break;

            default:
                break;

        }

    }
}
