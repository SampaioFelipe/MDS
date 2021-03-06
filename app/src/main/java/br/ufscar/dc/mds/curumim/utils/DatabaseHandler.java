package br.ufscar.dc.mds.curumim.utils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHandler {
    static private FirebaseDatabase database;

    static public void setInstance() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }

    }

    static public FirebaseDatabase getDatabase() {
        return database;
    }

    static public DatabaseReference getUserReference() {
        return database.getReference("/users/" + Authentication.getUserUID() + "/");
    }
}
