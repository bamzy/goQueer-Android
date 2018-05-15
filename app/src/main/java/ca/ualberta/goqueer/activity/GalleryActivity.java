package ca.ualberta.goqueer.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.ualberta.goqueer.R;
import ca.ualberta.goqueer.config.Constants;
import ca.ualberta.goqueer.network.QueerClient;
import ca.ualberta.goqueer.network.VolleyGetSetSummaryCallback;
import entity.QGallery;

/**
 * Created by Circa Lab on 3/18/2017.
 */

public class GalleryActivity extends YouTubeBaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,YouTubePlayer.OnInitializedListener{
    private ImageView mainMediaImage;
    private YouTubePlayerView mainMediaVideo;
    public static QGallery gallery;
    private QueerClient queerClient;
    private int currentIndex;
    boolean isImageFitToScreen;
    private TextView mediaTitle, mediaDescription,pageNumber, extraLink1, extraLink2, extraLink3, extraLink4, extraLink5,linkTitle;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        mainMediaImage = (ImageView) findViewById(R.id.mainMediaImage);
        mediaTitle = (TextView) findViewById(R.id.mediaTitle);
        mediaDescription = (TextView) findViewById(R.id.mediaDescription);
        extraLink1 = (TextView) findViewById(R.id.externalLink1);
        extraLink2 = (TextView) findViewById(R.id.externalLink2);
        extraLink3 = (TextView) findViewById(R.id.externalLink3);
        extraLink4 = (TextView) findViewById(R.id.externalLink4);
        extraLink5 = (TextView) findViewById(R.id.externalLink5);
        linkTitle = (TextView) findViewById(R.id.linkTitle);
        pageNumber = (TextView) findViewById(R.id.pageNumber);
        mainMediaVideo = (YouTubePlayerView) findViewById(R.id.mainMediaVideo);

        currentIndex = 0;


        Toolbar toolbar = (Toolbar) findViewById(R.id.gallery_toolbar);

//        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.gallery_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.gallery_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
            if (gallery.getMedias().get(currentIndex).getExtra_links() != null) {
                linkTitle.setVisibility(View.VISIBLE);
                String[] links = gallery.getMedias().get(currentIndex).getExtra_links().split(";");
                switch (links.length) {
                    case 5:
                        extraLink5.setVisibility(View.VISIBLE);
                        extraLink5.setText(links[4]);
                        extraLink5.setMovementMethod(LinkMovementMethod.getInstance());
                    case 4:
                        extraLink4.setVisibility(View.VISIBLE);
                        extraLink4.setText(links[3]);
                        extraLink4.setMovementMethod(LinkMovementMethod.getInstance());
                    case 3:
                        extraLink3.setVisibility(View.VISIBLE);
                        extraLink3.setText(links[2]);
                        extraLink3.setMovementMethod(LinkMovementMethod.getInstance());
                    case 2:
                        extraLink2.setVisibility(View.VISIBLE);
                        extraLink2.setText(links[1]);
                        extraLink2.setMovementMethod(LinkMovementMethod.getInstance());
                    case 1:
                        extraLink1.setVisibility(View.VISIBLE);
                        extraLink1.setText(links[0]);
                        extraLink1.setMovementMethod(LinkMovementMethod.getInstance());
                        break;

                }
            } else {
                extraLink1.setVisibility(View.GONE);
                extraLink2.setVisibility(View.GONE);
                extraLink3.setVisibility(View.GONE);
                extraLink4.setVisibility(View.GONE);
                extraLink5.setVisibility(View.GONE);
                extraLink1.setText("");
                extraLink2.setText("");
                extraLink3.setText("");
                extraLink4.setText("");
                extraLink5.setText("");
                linkTitle.setVisibility(View.GONE);
            }
            if ("4".equalsIgnoreCase(gallery.getMedias().get(currentIndex).getType_id())) {
                mainMediaImage.setVisibility(View.VISIBLE);
                mainMediaVideo.setVisibility(View.GONE);

                Picasso.with(getApplicationContext()).load(Constants.GO_QUEER_BASE_SERVER_URL + "client/downloadMediaById?media_id=" + gallery.getMedias().get(currentIndex).getId()).into(mainMediaImage);
            }
            if ("5".equalsIgnoreCase(gallery.getMedias().get(currentIndex).getType_id())) {
                mainMediaImage.setVisibility(View.VISIBLE);
                mainMediaVideo.setVisibility(View.GONE);
                Glide.with(getApplicationContext())
                        .asGif()
                        .load(Constants.GO_QUEER_BASE_SERVER_URL + "client/downloadMediaById?media_id=" + gallery.getMedias().get(currentIndex).getId())
                        .into((ImageView) findViewById(R.id.mainMediaImage));
            }
            if ("1".equalsIgnoreCase(gallery.getMedias().get(currentIndex).getType_id())){
                mainMediaVideo.setVisibility(View.VISIBLE);
                mainMediaImage.setVisibility(View.GONE);


                mainMediaVideo.initialize("AIzaSyA2ifrG3Xnv_gafk_PCOYSRAxB9sjRyS_Y",new YouTubePlayer.OnInitializedListener() {

                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                        if(!b){
                            String media_url = gallery.getMedias().get(currentIndex).getMedia_url();
                            if (media_url != null && media_url.length()>11) {
                                youTubePlayer.cueVideo(extractVideoIdFromUrl(media_url));
                            }
//                            youTubePlayer.cueVideo("G2W41pvvZs0");
                        }
                        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener(){
                            @Override
                            public void onFullscreen(boolean arg0) {
                            }});

                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        // TODO Auto-generated method stub
                    }


                });
            }


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



    final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
    final String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

    public String extractVideoIdFromUrl(String url) {
        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);

        for(String regex : videoIdRegex) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);

            if(matcher.find()){
                return matcher.group(1);
            }
        }

        return null;
    }
    private String youTubeLinkWithoutProtocolAndDomain(String url) {
        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find()){
            return url.replace(matcher.group(), "");
        }
        return url;
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

        } else if (id == R.id.nav_ack) {
            showAlert("Acknowledgements:", "Maureen Engel and others");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.gallery_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showAlert(String title, String body){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getApplicationContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getApplicationContext());
        }
        builder.setTitle(title)
                .setMessage(body)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

            if (!b) {
                youTubePlayer.cueVideo("fhWaJi1Hsfo"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
            }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            if (youTubeInitializationResult.isUserRecoverableError()) {
                youTubeInitializationResult.getErrorDialog(this, 1).show();
            } else {
                Toast.makeText(this, "Error initializing YouTube player", Toast.LENGTH_LONG).show();
            }
    }

}
