package com.njara.bounty.listeners;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.njara.bounty.R;
import com.njara.bounty.views.fragments.ProductFragment;

/**
 * Created by nfidiman on 04/02/2018.
 */

public class NavigationListener implements NavigationView.OnNavigationItemSelectedListener {

    private Activity activity;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public NavigationListener(Activity activity ,FragmentManager fragmentManager){

        this.activity=activity;
        this.fragmentManager=fragmentManager;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
         String title;
        switch (id){
            case R.id.nav_product:

                fragmentTransaction = this.fragmentManager.beginTransaction();
                title = this.activity.getResources().getString(R.string.title_product);
                fragmentTransaction.replace(R.id.content_main_frame, new ProductFragment(),title).commit();

                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
