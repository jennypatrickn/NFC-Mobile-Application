package com.njara.bounty.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.njara.bounty.R;
import com.njara.bounty.listeners.AuthButtonListener;
import com.njara.bounty.services.AccountService;
import com.njara.bounty.services.IAccountService;

/**
 * Created by njara on 14/05/2018.
 */
public class LoginFragment extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private FirebaseAuth fireBaseAuth;
    private Activity parentActivity;
    private AuthButtonListener textViewListener;
    private IAccountService accountService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fireBaseAuth = FirebaseAuth.getInstance();



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_login, container, false);
        textViewListener=new AuthButtonListener(getActivity(),getActivity().getSupportFragmentManager());
        textViewListener.fireBaseAuth=fireBaseAuth;
        TextView textViewSignUp = (TextView) view.findViewById(R.id.link_signup);
        textViewSignUp.setOnClickListener(textViewListener);

        TextView btnButton = (TextView) view.findViewById(R.id.btn_login);
        btnButton.setOnClickListener(textViewListener);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.accountService=new AccountService();
        FirebaseUser currentUser = fireBaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        parentActivity =  (Activity)activity;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void updateUI(FirebaseUser currentUser){

    }

    public void signInAction(View view){
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

    }

}
