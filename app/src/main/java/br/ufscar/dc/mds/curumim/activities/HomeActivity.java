package br.ufscar.dc.mds.curumim.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.ufscar.dc.mds.curumim.R;
import br.ufscar.dc.mds.curumim.activities.homeFragments.CalendarioFragment;
import br.ufscar.dc.mds.curumim.activities.homeFragments.CriancaFragment;
import br.ufscar.dc.mds.curumim.activities.homeFragments.InitialFragment;
import br.ufscar.dc.mds.curumim.activities.homeFragments.RegistroFragment;
import br.ufscar.dc.mds.curumim.utils.Authentication;
import br.ufscar.dc.mds.curumim.utils.DatabaseHandler;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        InitialFragment.OnFragmentInteractionListener,
        CalendarioFragment.OnFragmentInteractionListener,
        CriancaFragment.OnListFragmentInteractionListener,
        RegistroFragment.OnFragmentInteractionListener {

    ImageView userPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        userPhoto = header.findViewById(R.id.image_user_drawer);

        if (Authentication.getBitmap() == null) {
            getUserPhoto();
        } else {
            userPhoto.setImageBitmap(Authentication.getBitmap());
        }

        ((TextView) header.findViewById(R.id.user_name_drawer)).setText(Authentication.getUser().getDisplayName());

        ((TextView) header.findViewById(R.id.user_email_drawer)).setText(Authentication.getUser().getEmail());

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = InitialFragment.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = CalendarioFragment.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = CriancaFragment.newInstance();
                                break;
                            case R.id.action_item4:
                                selectedFragment = RegistroFragment.newInstance();
                                break;
                        }

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, InitialFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the InitialFragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout() {

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }

    public void getUserPhoto() {
        DatabaseReference ref = DatabaseHandler.getDatabase().getReference("/users/" + Authentication.getUser().getUid() + "/user_photo");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String photo = (String) dataSnapshot.getValue();
                if (photo != null) {
                    Authentication.setUserPhoto(Base64.decode(photo, 0));
                    userPhoto.setImageBitmap(Authentication.getBitmap());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("teste", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode,resultCode,data);

    }
}
