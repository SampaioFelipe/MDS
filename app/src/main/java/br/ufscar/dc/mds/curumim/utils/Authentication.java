package br.ufscar.dc.mds.curumim.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication {
    public static final int RC_SIGN_IN = 123;

    static private FirebaseAuth auth;

    static public void setInstante(){
        auth = FirebaseAuth.getInstance();
    }

    static public FirebaseUser getUser(){
        return auth.getCurrentUser();
    }

    static public boolean isLogged(){
        return auth.getCurrentUser() != null;
    }

    static public String getUserName(){
        return auth.getCurrentUser().getDisplayName();
    }

    static public String getUserEmail(){
        return auth.getCurrentUser().getEmail();
    }
}
