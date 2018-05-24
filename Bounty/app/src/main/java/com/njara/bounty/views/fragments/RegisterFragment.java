package com.njara.bounty.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.njara.bounty.R;
import com.njara.bounty.listeners.AuthButtonListener;
import com.njara.bounty.services.AccountService;
import com.njara.bounty.services.IAccountService;

/**
 * Created by nfidiman on 29/01/2018.
 */

public class RegisterFragment extends Fragment {
    private Activity parentActivity;
    private AuthButtonListener textViewListener;
    private IAccountService accountService;
    private FirebaseAuth fireBaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireBaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        parentActivity =  activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        textViewListener=new AuthButtonListener(getActivity(),getActivity().getSupportFragmentManager());
        TextView textViewSignIn = (TextView) view.findViewById(R.id.link_signin);
        textViewListener.fireBaseAuth=fireBaseAuth;
        textViewSignIn.setOnClickListener(textViewListener);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.accountService=new AccountService();

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

}
