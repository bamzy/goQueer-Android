package ca.ualberta.huco.goqueer_android.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;


import ca.ualberta.huco.goqueer_android.R;
import ca.ualberta.huco.goqueer_android.config.Constants;
import ca.ualberta.huco.goqueer_android.network.QueerClient;
import ca.ualberta.huco.goqueer_android.network.VolleyGetSetSummaryCallback;
import ca.ualberta.huco.goqueer_android.network.VolleyMyHintCallback;
import entity.QGallery;

/**
 * Created by Circa Lab on 3/18/2017.
 */

public class GalleryActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{
    private ImageView mainMediaImage;
    public static QGallery gallery;
    private QueerClient queerClient;
    private int currentIndex;
    boolean isImageFitToScreen;
    private TextView mediaTitle, mediaDescription,pageNumber;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        mainMediaImage = (ImageView) findViewById(R.id.mainMediaImage);
        mediaTitle = (TextView) findViewById(R.id.mediaTitle);
        mediaDescription = (TextView) findViewById(R.id.mediaDescription);
        pageNumber = (TextView) findViewById(R.id.pageNumber);
        currentIndex = 0;


        Toolbar toolbar = (Toolbar) findViewById(R.id.gallery_toolbar);

        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.gallery_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fabn = (FloatingActionButton) findViewById(R.id.next);
        fabn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex<gallery.getMedias().size()-1) {
                    currentIndex++;
                    setMediaContent(currentIndex);
                }
            }
        });


        FloatingActionButton fabp = (FloatingActionButton) findViewById(R.id.previous);
        fabp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex>0) {
                    currentIndex--;
                    setMediaContent(currentIndex);
                }
            }
        });


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setMediaContent(currentIndex);
                //Do something after 100ms
            }
        }, 1000);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(parent, name, context, attrs);
         queerClient = new QueerClient(getApplicationContext());

        return view;
    }

    public void setMediaContent(long mediaIndex) {
        if (mediaIndex < gallery.getMedias().size() && mediaIndex>=0){
            mediaTitle.setText(gallery.getMedias().get(currentIndex).getName());
            pageNumber.setText("(" +(currentIndex+1) + "/" + gallery.getMedias().size() + ")");
            mediaDescription.setText(gallery.getMedias().get(currentIndex).getDescription() );
            mediaDescription.setMovementMethod(new ScrollingMovementMethod());
            Picasso.with(getApplicationContext()).load(Constants.GO_QUEER_BASE_SERVER_URL + "client/downloadMediaById?media_id="+ gallery.getMedias().get(currentIndex).getId()).into(mainMediaImage);
            mainMediaImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isImageFitToScreen) {
                        isImageFitToScreen=false;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.gravity= Gravity.CENTER;
                        mainMediaImage.setLayoutParams(params);
                        mainMediaImage.setAdjustViewBounds(true);
                    }else{
                        isImageFitToScreen=true;
                        mainMediaImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        mainMediaImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_set) {
            // Handle the camera action
        } else if (id == R.id.nav_discovery_status) {

            queerClient.getDiscoveredSetSummary(new VolleyGetSetSummaryCallback() {
                @Override
                public void onSuccess(String response) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(VolleyError result) {
                    Toast.makeText(getApplicationContext(), "There was an issue retrieving data from server", Toast.LENGTH_SHORT).show();

                }
            },gallery.getId(),MapActivity.getDefinedLocation());




        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.gallery_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
