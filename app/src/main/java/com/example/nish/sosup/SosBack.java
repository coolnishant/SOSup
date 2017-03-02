package com.example.nish.sosup;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.style.BackgroundColorSpan;

/**
 * Created by Nish on 03-11-2016.
 */
public class SosBack extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
