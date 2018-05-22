package com.njara.bounty.services;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by njara on 14/05/2018.
 */
public interface IAccountService {

    public void addSession();

    public void UpdateUserProfile(FirebaseUser firebaseUser, String name , String Photo);
}