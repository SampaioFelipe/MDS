package br.ufscar.dc.mds.curumim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import br.ufscar.dc.mds.curumim.utils.Authentication;
import br.ufscar.dc.mds.curumim.utils.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Authentication.isLogged()) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        } else {
            Button sign_button = (Button) findViewById(R.id.sign_in_button);

            sign_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(
                            // Get an instance of AuthUI based on the default app
                            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())).build(),
                            Authentication.RC_SIGN_IN);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == Authentication.RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                getUserData();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Log.d("teste", "back");
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Log.d("teste", "erro de rede");
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Log.d("teste", "erro desconhecido");
                }
            }
        }
    }

    public void getUserData() {
        DatabaseReference ref = DatabaseHandler.getDatabase().getReference("/users/" + Authentication.getUser().getUid() + "/");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("teste", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }
}
