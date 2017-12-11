package br.ufscar.dc.mds.curumim.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.ufscar.dc.mds.curumim.utils.Authentication;
import br.ufscar.dc.mds.curumim.utils.DatabaseHandler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Authentication.setInstante();
        DatabaseHandler.setInstance();

        if (Authentication.isLogged()) {
            getUserData();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    public void getUserData() {
        DatabaseReference ref = DatabaseHandler.getDatabase().getReference("/users/" + Authentication.getUser().getUid() + "/");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    startActivity(new Intent(SplashScreenActivity.this, SignUpActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
