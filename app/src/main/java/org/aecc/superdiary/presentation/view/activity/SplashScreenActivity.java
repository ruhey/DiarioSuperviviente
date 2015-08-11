package org.aecc.superdiary.presentation.view.activity;

import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Window;

import org.aecc.superdiary.R;

public class SplashScreenActivity extends DiaryBaseActivity {

        private static final long SPLASH_SCREEN_DELAY = 3000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Set portrait orientation
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            // Hide title bar
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.activity_splash_screen);

            TimerTask task = new TimerTask() {
                @Override
                public void run() {

                    Intent mainIntent = new Intent().setClass(
                            SplashScreenActivity.this, BienvenidaActivity.class);
                    startActivity(mainIntent);

                    finish();
                }
            };

            Timer timer = new Timer();
            timer.schedule(task, SPLASH_SCREEN_DELAY);
        }

    }