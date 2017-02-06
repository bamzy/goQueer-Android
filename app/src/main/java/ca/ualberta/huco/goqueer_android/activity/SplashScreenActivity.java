package ca.ualberta.huco.goqueer_android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import ca.ualberta.huco.goqueer_android.R;
import ca.ualberta.huco.goqueer_android.config.Constants;


/**
 * The  Splash Screen Activity.
 */
public class SplashScreenActivity extends Activity {
    private Context context;

//    private class AuthCallback implements UObjectCallback {
//        @Override
//        public void onGotObject(DataObject uObject) {
//            AuthPersonDetails personDetails = (AuthPersonDetails) uObject;
//            if (personDetails == null) {
//                navigateToLoginActivity();
//            } else {
//                navigateToUserActivity(personDetails);
//            }
//        }
//    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        int SPLASH_DISPLAY_LENGTH = 1000;
        setContentView(R.layout.activity_splash);
        context = this.getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.goqueer_primary_theme_color));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToMapActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);


    }


    private void navigateToMainActivity() {

        Intent userMainMenu = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(userMainMenu);
        finish();
    }
    private void navigateToMapActivity() {

        Intent map = new Intent(SplashScreenActivity.this, MapActivity.class);
        startActivity(map);
        finish();
    }


}
