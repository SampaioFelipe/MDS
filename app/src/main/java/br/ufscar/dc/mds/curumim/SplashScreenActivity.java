package br.ufscar.dc.mds.curumim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.ufscar.dc.mds.curumim.utils.Authentication;
import br.ufscar.dc.mds.curumim.utils.DatabaseHandler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Authentication.setInstante();
        DatabaseHandler.setInstance();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
