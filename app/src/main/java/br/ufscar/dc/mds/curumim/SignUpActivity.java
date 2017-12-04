package br.ufscar.dc.mds.curumim;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

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

        userImage = (ImageView) findViewById(R.id.user_image_signup);
        new GetUserImage().execute();

        ((TextView) findViewById(R.id.user_name_signup)).setText(Authentication.getUserName());
        ((TextView) findViewById(R.id.user_email_signup)).setText(Authentication.getUserEmail());

        nascimento_field = findViewById(R.id.data_nascimento_signup);
        tipoSanguineo_field = findViewById(R.id.tipo_sanguineo_signup);
        sexo_field = findViewById(R.id.sexo_signup);

        // Botão de signup
        (findViewById(R.id.sign_up_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Validar os dados dos campos
                String dataNascimento = nascimento_field.getText().toString();
                String sangue = tipoSanguineo_field.getSelectedItem().toString();
                String sexo = sexo_field.getSelectedItem().toString();

                HashMap<String, String> dict = new HashMap<>();

                dict.put("data_nascimento", dataNascimento);
                dict.put("tipo_sanguineo", sangue);
                dict.put("sexo", sexo);

                if(photo != null) {
                    dict.put("user_photo", Base64.encodeToString(photo, 0));
                }

                // TODO: verificar se os dados foram realmente salvos
                DatabaseHandler.getDatabase().getReference().child("users").child(Authentication.getUserUID()).setValue(dict);

                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));

                finish();
            }
        });
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
