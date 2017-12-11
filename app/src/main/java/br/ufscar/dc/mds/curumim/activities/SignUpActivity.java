package br.ufscar.dc.mds.curumim.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;
import java.util.HashMap;

import br.ufscar.dc.mds.curumim.R;
import br.ufscar.dc.mds.curumim.utils.Authentication;
import br.ufscar.dc.mds.curumim.utils.DatabaseHandler;
import br.ufscar.dc.mds.curumim.utils.NetworkHandler;

public class SignUpActivity extends AppCompatActivity {

    ImageView userImage;
    byte[] photo;

    EditText nascimento_field;
    Spinner tipoSanguineo_field, sexo_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userImage = findViewById(R.id.user_image_signup);
        new GetUserImage().execute();

        ((TextView) findViewById(R.id.user_name_signup)).setText(Authentication.getUserName());
        ((TextView) findViewById(R.id.user_email_signup)).setText(Authentication.getUserEmail());

        nascimento_field = findViewById(R.id.data_nascimento_signup);

        nascimento_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Calendar calendar = Calendar.getInstance();


                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String value = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
                        nascimento_field.setText(value);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        tipoSanguineo_field = findViewById(R.id.tipo_sanguineo_signup);
        sexo_field = findViewById(R.id.sexo_signup);

        // Botão de signup
        (findViewById(R.id.sign_up_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Validar os dados dos campos
                String dataNascimento = nascimento_field.getText().toString();
                int sangue = tipoSanguineo_field.getSelectedItemPosition();
                int sexo = sexo_field.getSelectedItemPosition();

                if (dataNascimento.length() > 0 && sangue > 0 && sexo > 0) {
                    HashMap<String, String> dict = new HashMap<>();

                    dict.put("data_nascimento", dataNascimento);
                    dict.put("tipo_sangue", String.valueOf(sangue));
                    dict.put("sexo", String.valueOf(sexo));

                    if (photo != null) {
                        dict.put("user_photo", Base64.encodeToString(photo, 0));
                    }

                    // TODO: verificar se os dados foram realmente salvos
                    DatabaseHandler.getDatabase().getReference().child("users").child(Authentication.getUserUID()).setValue(dict);

                    startActivity(new Intent(SignUpActivity.this, TutorialActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        logout();
    }

    public void logout() {

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }

    private class GetUserImage extends AsyncTask<Void, Void, byte[]> {

        @Override
        protected byte[] doInBackground(Void... params) {
            try {

                photo = NetworkHandler.getImageFromHttpUrl(Authentication.getUser().getPhotoUrl().toString(), SignUpActivity.this);

                if (photo != null) {
                    return photo;
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(byte[] foto) {

            if (foto != null) {
                photo = foto;
                Authentication.setUserPhoto(foto);
                userImage.setImageBitmap(Authentication.getBitmap());
            }

        }
    }

}
