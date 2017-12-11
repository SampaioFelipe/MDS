package br.ufscar.dc.mds.curumim.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import br.ufscar.dc.mds.curumim.R;
import br.ufscar.dc.mds.curumim.utils.Authentication;
import br.ufscar.dc.mds.curumim.utils.DatabaseHandler;

public class ProfileActivity extends AppCompatActivity {
    EditText nascimento;
    Spinner sexo, tipoSangue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((ImageView) findViewById(R.id.user_image_profile)).setImageBitmap(Authentication.getBitmap());

        ((TextView) findViewById(R.id.user_name_profile)).setText(Authentication.getUserName());
        ((TextView) findViewById(R.id.user_email_profile)).setText(Authentication.getUserEmail());

        nascimento = findViewById(R.id.data_nascimento_profile);
        nascimento.setEnabled(false);

        tipoSangue = findViewById(R.id.tipo_sanguineo_profile);
        tipoSangue.setEnabled(false);

        sexo = findViewById(R.id.sexo_profile);
        sexo.setEnabled(false);

        DatabaseReference ref = DatabaseHandler.getDatabase().getReference("/users/" + Authentication.getUser().getUid() + "/");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    HashMap<String, String> infos = (HashMap<String, String>) dataSnapshot.getValue();

                    if(infos != null){
                        nascimento.setText(infos.get("data_nascimento"));
                        tipoSangue.setSelection(Integer.decode(infos.get("tipo_sangue")));
                        sexo.setSelection(Integer.decode(infos.get("sexo")));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("teste", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
