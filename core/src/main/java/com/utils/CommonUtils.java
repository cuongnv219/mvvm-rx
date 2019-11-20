package com.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.provider.Settings;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class CommonUtils {

    private CommonUtils() {
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream inputStream = manager.open(jsonFileName);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        int in = inputStream.read(buffer);
        inputStream.close();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new String(buffer, StandardCharsets.UTF_8);
        } else {
            return new String(buffer, StandardCharsets.UTF_8);
        }
    }
}
