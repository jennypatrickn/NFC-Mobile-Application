package com.njara.bounty.helpers;

import android.app.Activity;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;

import com.njara.bounty.R;
import com.njara.bounty.listeners.SearchListener;

/**
 * Created by njara on 13/02/2018.
 */
public class UIHelper {

    public static void UpdateSearchViewUI(Activity activity , SearchListener searchListener){

        SearchView searchViewAction = (SearchView) activity.findViewById(R.id.searchView);
        searchViewAction.setIconifiedByDefault(false);
        searchViewAction.onActionViewCollapsed();
        int closeSearchId = android.support.v7.appcompat.R.id.search_close_btn;
        ImageView closeButton = (ImageView) searchViewAction.findViewById(closeSearchId);
       closeButton.setImageResource(R.drawable.search_cancel_button);


        ImageView searchButton = (ImageView) searchViewAction.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchButton.setImageResource(R.drawable.search_button);

        int iconSearchId =  android.support.v7.appcompat.R.id.search_mag_icon;
        ImageView iconButton = (ImageView) searchViewAction.findViewById(iconSearchId);
        iconButton.setImageResource(R.drawable.search_button);

        searchListener.searchView=searchViewAction;
        searchButton.setOnClickListener(searchListener);
    }
}
