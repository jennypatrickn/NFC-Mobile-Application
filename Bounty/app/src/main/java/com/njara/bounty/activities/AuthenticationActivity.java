package com.njara.bounty.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.njara.bounty.R;
import com.njara.bounty.views.fragments.LoginFragment;

/**
 * Created by njara on 14/05/2018.
 */

public class AuthenticationActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.authentication_main);

        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        String title = getResources().getString(R.string.ac_authentication);
        fragmentTransaction.replace(R.id.authentication_frame, new LoginFragment(),title).commit();
    }
}
