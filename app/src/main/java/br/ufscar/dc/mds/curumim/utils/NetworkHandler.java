package br.ufscar.dc.mds.curumim.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkHandler {
    public static boolean CONNECTED = false;

    public static URL buildUrl(String path) {

        Uri builtUri = Uri.parse(path).buildUpon().build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static boolean updateConnectionState(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

            //For WiFi Check
            boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .isConnectedOrConnecting();

            if (isWifi) {
                CONNECTED = true;
            } else {
                //For 3G check
                boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                        .isConnectedOrConnecting();

                SharedPreferences preferencias = context.getSharedPreferences("Settings", 0);
                boolean internet = preferencias.getBoolean("internet", false);

                if (internet && is3g) {
                    CONNECTED = true;
                } else {
                    CONNECTED = false;
                }
            }

            return CONNECTED;
        } catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] getImageFromHttpUrl(String url, Context context) throws IOException {
        if (updateConnectionState(context)) {
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) imageUrl.openConnection();

                InputStream in = urlConnection.getInputStream();
                BufferedInputStream input = new BufferedInputStream(in);
                ByteArrayOutputStream output = new ByteArrayOutputStream();

                int current;

                while ((current = input.read()) != -1) {
                    output.write((byte) current);
                }
                return output.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
