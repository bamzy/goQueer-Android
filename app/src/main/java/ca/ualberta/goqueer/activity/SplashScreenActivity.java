package ca.ualberta.goqueer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import ca.ualberta.goqueer.R;


/**
 * The  Splash Screen Activity.
 */
public class SplashScreenActivity extends Activity {
    private Context context;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        int SPLASH_DISPLAY_LENGTH = 2000;
        setContentView(R.layout.activity_splash);
        context = this.getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.goqueer_primary_theme_color));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToMapActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void navigateToMapActivity() {
        Intent map = new Intent(SplashScreenActivity.this, MapActivity.class);
        startActivity(map);
        finish();
    }


}
