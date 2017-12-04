package br.ufscar.dc.mds.curumim.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication {
    public static final int RC_SIGN_IN = 123;

    static private FirebaseAuth auth;

    static public Bitmap userPhotoBitmap;

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

    static public String getUserUID(){
        return auth.getUid();
    }

    static public void setUserPhoto(byte [] bytes){
        Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        Bitmap imageRounded = Bitmap.createBitmap(image.getWidth(), image.getHeight(), image.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(image, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, image.getWidth(), image.getHeight())), image.getWidth() / 2, image.getWidth() / 2, paint);

        userPhotoBitmap = imageRounded;
    }

    static public Bitmap getBitmap(){
        return userPhotoBitmap;
    }
}
