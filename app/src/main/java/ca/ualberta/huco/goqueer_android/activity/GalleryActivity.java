package ca.ualberta.huco.goqueer_android.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

import ca.ualberta.huco.goqueer_android.R;
import ca.ualberta.huco.goqueer_android.adapter.GridViewAdapter;
import ca.ualberta.huco.goqueer_android.config.Constants;

/**
 * Created by Circa Lab on 3/18/2017.
 */

public class GalleryActivity extends AppCompatActivity {
   private ImageView mainMediaImage;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_main);
        mainMediaImage = (ImageView) findViewById(R.id.mainMediaImage);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.next);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(getApplicationContext()).load(Constants.GO_QUEER_BASE_SERVER_URL + "client/downloadMediaById?media_id="+"3").into(mainMediaImage);
            }
        });
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(parent, name, context, attrs);
//        Picasso.with(getApplicationContext()).load(Constants.GO_QUEER_BASE_SERVER_URL + "client/downloadMediaById?media_id="+"4").into(mainMediaImage);
        return view;
    }
}
