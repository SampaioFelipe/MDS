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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufscar.dc.mds.curumim.utils.Authentication;
import br.ufscar.dc.mds.curumim.utils.NetworkHandler;

public class SignUpActivity extends AppCompatActivity {

    ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userImage = (ImageView) findViewById(R.id.user_image_signup);
        new GetUserImage().execute();

        ((TextView) findViewById(R.id.user_name_signup)).setText(Authentication.getUserName());
        ((TextView) findViewById(R.id.user_email_signup)).setText(Authentication.getUserEmail());

        // Botão de signup
        (findViewById(R.id.sign_up_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Validar os dados dos campos
                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    private class GetUserImage extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {

                byte[] b = NetworkHandler.getImageFromHttpUrl(Authentication.getUser().getPhotoUrl().toString(), SignUpActivity.this);

                if(b != null) {
                    return BitmapFactory.decodeByteArray(b, 0, b.length);
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap image) {

            if (image != null) {

                Bitmap imageRounded = Bitmap.createBitmap(image.getWidth(), image.getHeight(), image.getConfig());
                Canvas canvas = new Canvas(imageRounded);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setShader(new BitmapShader(image, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                canvas.drawRoundRect((new RectF(0, 0, image.getWidth(), image.getHeight())), image.getWidth() / 2, image.getWidth() / 2, paint);

                userImage.setImageBitmap(imageRounded);
            }

        }
    }

}
