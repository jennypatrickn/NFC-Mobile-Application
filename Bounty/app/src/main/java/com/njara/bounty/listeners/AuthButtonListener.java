package com.njara.bounty.listeners;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.njara.bounty.R;
import com.njara.bounty.services.BasketService;
import com.njara.bounty.views.fragments.LoginFragment;
import com.njara.bounty.views.fragments.ProductFragment;
import com.njara.bounty.views.fragments.RegisterFragment;

/**
 * Created by nfidiman on 31/01/2018.
 */

public class AuthButtonListener implements View.OnClickListener {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public int frameId=R.id.content_main_frame;
    private Activity activity;
    public FirebaseAuth fireBaseAuth;
    public AuthButtonListener(Activity activity , FragmentManager fragmentManager){
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

            case R.id.btn_register:
                EditText inpass1=(EditText)activity.findViewById(R.id.input_password);
                EditText iemail1=(EditText)activity.findViewById(R.id.input_email);
                String email1=iemail1.getText().toString();
                String password1=inpass1.getText().toString();

                fireBaseAuth.createUserWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(AuthButtonListener.this.activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Test", "createUserWithEmail:success");
                                    FirebaseUser user = fireBaseAuth.getCurrentUser();

                                    BasketService.valid=true;

                                    fragmentTransaction = AuthButtonListener.this.fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.content_main_frame, new ProductFragment(),"Products").commit();
                                    AppCompatButton but= activity.findViewById(R.id.btn_header_login);
                                    but.setVisibility(View.GONE);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());

                                }

                            }
                        });

                break;

            case R.id.btn_login:
                boolean status =true;
                EditText inpass=(EditText)activity.findViewById(R.id.input_password);
                EditText iemail=(EditText)activity.findViewById(R.id.input_email);
                final String email=iemail.getText().toString();
                String password=inpass.getText().toString();

                if(status){
                    fireBaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = fireBaseAuth.getCurrentUser();
                                        BasketService.valid=true;

                                        fragmentTransaction = AuthButtonListener.this.fragmentManager.beginTransaction();
                                       fragmentTransaction.replace(R.id.content_main_frame, new ProductFragment(),"Products").commit();
                                        AppCompatButton but= activity.findViewById(R.id.btn_header_login);
                                        but.setVisibility(View.GONE);

                                        AppCompatButton userBtn= activity.findViewById(R.id.user_log);
                                        userBtn.setVisibility(View.VISIBLE);
                                        userBtn.setText(email);
                                        //updateUI(user);

                                    } else {
                                        Log.w("Test", "signInWithEmail:failure", task.getException());
                                       // updateUI(null);
                                    }
                                }
                            });



                    break;

                }
                break;

            default:
                break;

        }

    }
}
