package ca.ualberta.huco.goqueer_android.activity;

/**
 * Created by Circa Lab on 2/5/2017.
 */

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import ca.ualberta.huco.goqueer_android.R;
import ca.ualberta.huco.goqueer_android.config.Constants;
import ca.ualberta.huco.goqueer_android.location.MyLocation;
import ca.ualberta.huco.goqueer_android.network.DownloadMedia;
import ca.ualberta.huco.goqueer_android.network.QueerClient;
import ca.ualberta.huco.goqueer_android.network.VolleyMyCoordinatesCallback;
import ca.ualberta.huco.goqueer_android.network.VolleyMyGalleriesCallback;
import ca.ualberta.huco.goqueer_android.network.VolleyMyGalleryInfoCallback;
import entity.Coordinate;
import entity.QGallery;
import entity.QLocation;
import entity.QCoordinate;
import entity.QMedia;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ImageView galleryThumbnail;
    private QLocation[] discoveredLocations;
    private final DownloadMedia downloadMedia = new DownloadMedia(MapActivity.this);
    //private QGallery[] myGalleries;
    private List<QGallery> myGalleries = new CopyOnWriteArrayList<QGallery>();
    private ArrayList<QLocation> allLocations;
    private TextView coordinate;
    private QueerClient queerClient;
    private int mStyleIds[] = {
            R.string.style_label_retro,
            R.string.style_label_night,
            R.string.style_label_grayscale,
            R.string.style_label_no_pois_no_transit,
            R.string.style_label_default,
    };
    private static final String SELECTED_STYLE = "selected_style";
    private int mSelectedStyleId = R.string.style_label_default;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinate = (TextView) findViewById(R.id.coordinates);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        queerClient = new QueerClient(getApplicationContext());
        galleryThumbnail = (ImageView) findViewById(R.id.galleryThumbnail);




    }
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }


    private void showStylesDialog() {
        // mStyleIds stores each style's resource ID, and we extract the names here, rather
        // than using an XML array resource which AlertDialog.Builder.setItems() can also
        // accept. We do this since using an array resource would mean we would not have
        // constant values we can switch/case on, when choosing which style to apply.
        List<String> styleNames = new ArrayList<>();
        for (int style : mStyleIds) {
            styleNames.add(getString(style));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.style_choose));
        builder.setItems(styleNames.toArray(new CharSequence[styleNames.size()]),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectedStyleId = mStyleIds[which];
                        String msg = getString(R.string.style_set_to, getString(mSelectedStyleId));
                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d(Constants.LOG_TAG, msg);
                        setSelectedStyle();
                    }
                });
        builder.show();
    }

    private void setSelectedStyle() {
        MapStyleOptions style;
        switch (mSelectedStyleId) {
            case R.string.style_label_retro:
                // Sets the retro style via raw resource JSON.
                style = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_retro);
                break;
            case R.string.style_label_night:
                // Sets the night style via raw resource JSON.
                style = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_night);
                break;
            case R.string.style_label_grayscale:
                // Sets the grayscale style via raw resource JSON.
                style = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_grayscale);
                break;
            case R.string.style_label_no_pois_no_transit:
                // Sets the no POIs or transit style via JSON string.
                style = new MapStyleOptions("[" +
                        "  {" +
                        "    \"featureType\":\"poi.business\"," +
                        "    \"elementType\":\"all\"," +
                        "    \"stylers\":[" +
                        "      {" +
                        "        \"visibility\":\"off\"" +
                        "      }" +
                        "    ]" +
                        "  }," +
                        "  {" +
                        "    \"featureType\":\"transit\"," +
                        "    \"elementType\":\"all\"," +
                        "    \"stylers\":[" +
                        "      {" +
                        "        \"visibility\":\"off\"" +
                        "      }" +
                        "    ]" +
                        "  }" +
                        "]");
                break;
            case R.string.style_label_default:
                // Removes previously set style, by setting it to null.
                style = null;
                break;
            default:
                return;
        }
        mMap.setMapStyle(style);
    }






    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng testLocation =  new LatLng(53.550247, -113.498094);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(testLocation, 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(testLocation)      // Sets the center of the map to location user
                .zoom(14)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        else prepareLocationManager();
        if (android.os.Build.VERSION.SDK_INT < 23)
            prepareLocationManager();
        

        MapStyleOptions style;
        style = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_grayscale);
        mMap.setMapStyle(style);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnInfoWindowClickListener(this);
        initiateMyLocationPolling();

    }



    private void initiateMyLocationPolling() {
        int delay = 2000; // delay for 5 sec.
        int period = 15000; // repeat every 10 secs.

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                queerClient.getMyLocations(new VolleyMyCoordinatesCallback() {
                    @Override
                    public void onSuccess(QLocation[] queerLocations) {
                        mMap.clear();

//                        if (discoveredLocations == null || discoveredLocations.length == 0)
                        discoveredLocations= null;
                        discoveredLocations = queerLocations;

                        for (QLocation queerLocation : discoveredLocations) {
                            if (queerLocation.getQCoordinates().getType() == QCoordinate.CoordinateType.POINT) {
                                Coordinate latlog = queerLocation.getQCoordinates().getCoordinates().get(0);
                                LatLng testLocation4 =  new LatLng(latlog.getLat(), latlog.getLon());
                                mMap.addMarker(new MarkerOptions()
                                        .position(testLocation4)
                                        .title(queerLocation.getName())
                                        .snippet(queerLocation.getDescription() + "\n" + queerLocation.getAddress())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin5)));
                            } else if (queerLocation.getQCoordinates().getType() == QCoordinate.CoordinateType.POLYGON){
                                PolygonOptions polygonOptions = new PolygonOptions();
                                for (Coordinate coordinate1 : queerLocation.getQCoordinates().getCoordinates()) {
                                    polygonOptions.add(new LatLng(coordinate1.getLat(),coordinate1.getLon()));
                                }
                                mMap.addPolygon(polygonOptions.fillColor(Color.GREEN));
                            }
                        }

                        Log.w(Constants.LOG_TAG,"Getting My Location Repeated");
                        pullMyGalleries(discoveredLocations);
                        pullAllLocations();
                    }

                    @Override
                    public void onError(VolleyError result) {

                    }
                });


            }

        }, delay, period);
    }

    private void pullMyGalleries(final QLocation[] locations) {

        for (QLocation location : locations) {
            queerClient.getMyGalleryInfo(new VolleyMyGalleryInfoCallback() {
                @Override
                public void onSuccess(QGallery gallery) {
                    if (gallery != null && !existIn(myGalleries,gallery)) {

                        myGalleries.add(gallery);
                        getGallery(gallery.getId());
                    }
                }
                @Override
                public void onError(VolleyError result) {

                }
            },location.getGallery_id());
            getGallery(location.getGallery_id());
        }
    }

    private boolean existIn(List<QGallery> myGalleries, QGallery gallery) {
        for (QGallery myGallery : myGalleries) {
            if (myGallery.getId()== gallery.getId())
                return true;
        }
        return false;
    }

    private void getGallery(final long gallery_id) {
        queerClient.getMyGallery(new VolleyMyGalleriesCallback() {
            @Override
            public void onSuccess(QMedia[] medias) {
                for (QGallery myGallery : myGalleries) {
                    if (myGallery.getId() == gallery_id)
                        myGallery.setMedias(new ArrayList<>(Arrays.asList(medias)));
//                        downloadMediaContent(medias);
                }

            }
            @Override
            public void onError(VolleyError result) {

            }
        },gallery_id);
    }


    private void pullAllLocations() {
        queerClient.getAllLocations(new VolleyMyCoordinatesCallback() {
            @Override
            public void onSuccess(QLocation[] queerLocations) {
                if (allLocations != null   )
                    allLocations.clear();
                allLocations=new ArrayList<QLocation>(Arrays.asList(queerLocations));
                Log.w(Constants.LOG_TAG,"Getting All Location Repeated" + allLocations.size());

            }

            @Override
            public void onError(VolleyError result) {

            }
        });
    }


    private void prepareLocationManager() {
        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
            @Override
            public void gotLocation(final Location location){

                MapActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String value = "(" + location.getLongitude() + " , " + location.getLatitude() + ")";
                        coordinate.setText(value);

                    }
                });
            }
        };
        MyLocation myLocation = new MyLocation(this);
        myLocation.getLocation(this, locationResult);
    }




    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        prepareLocationManager();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == R.id.menu_style_choose) {
            showStylesDialog();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_set) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {



        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Store the selected map style, so we can assign it when the activity resumes.
        outState.putInt(SELECTED_STYLE, mSelectedStyleId);
        super.onSaveInstanceState(outState);
    }


    private void navigateToGalleryActivity(QGallery qGallery) {
        GalleryActivity.gallery = qGallery;
        Intent map = new Intent(MapActivity.this, GalleryActivity.class);
        startActivity(map);
        finish();
    }



    @Override
    public void onInfoWindowClick(Marker marker) {
        if(marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }
        final QGallery qGallery = findAssociatedGallery(marker);
        if (qGallery != null && qGallery.getMedias().size()>0)
            Picasso.with(getApplicationContext()).load(Constants.GO_QUEER_BASE_SERVER_URL + "client/downloadMediaById?media_id=" + qGallery.getMedias().get(0).getId()).into(galleryThumbnail);
        galleryThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGalleryActivity(qGallery);
            }
        });

    }

    private QGallery findAssociatedGallery(Marker marker) {
        for (QLocation discoveredLocation : discoveredLocations) {
            if (marker.getTitle().equals(discoveredLocation.getName())) {
                for (QGallery myGallery : myGalleries) {
                    if (discoveredLocation.getGallery_id() == myGallery.getId()){
                        return myGallery;
                    }

                }
            }
        }
        return null;

    }
}