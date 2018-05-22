package com.njara.bounty.listeners;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.njara.bounty.R;
import com.njara.bounty.views.fragments.SearchFragment;

/**
 * Created by njara on 13/02/2018.
 */
public class SearchListener  implements View.OnClickListener {


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public int frameId= R.id.content_main_frame;
    public SearchView searchView;
    private Activity activity;
    public SearchListener(Activity activity , FragmentManager fragmentManager){
        this.activity=activity;
        this.fragmentManager=fragmentManager;

    }


    @Override
    public void onClick(View view) {


        String title="";
        switch ( view.getId()) {

            case android.support.v7.appcompat.R.id.search_button:

                this.searchView.onActionViewExpanded();
                fragmentTransaction = this.fragmentManager.beginTransaction();

                title = this.activity.getResources().getString(R.string.ac_register);
                fragmentTransaction.replace(R.id.content_main_frame, new SearchFragment(), title).commit();
                break;
        }
    }
}
