package ca.ualberta.huco.goqueer_android.activity;

/**
 * Created by Circa Lab on 2/5/2017.
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ca.ualberta.huco.goqueer_android.R;
import ca.ualberta.huco.goqueer_android.location.MyLocation;
import ca.ualberta.huco.goqueer_android.network.QueerClient;
import ca.ualberta.huco.goqueer_android.network.VolleyMyCoordinatesCallback;
import entity.Coordinate;
import entity.QLocation;
import entity.QCoordinate;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private QLocation[] qLocations;
    private TextView coordinate;
    private QueerClient queerClient;


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


    }
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
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

//        CircleOptions circleOptions = new CircleOptions()
//                .center(new LatLng(53.44, -113.30))
//                .radius(1000); // In meters
//        mMap.addCircle(circleOptions);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        else prepareLocationManager();
        
        prepareNetworkServices();





    }

    private void prepareNetworkServices() {
        int delay = 2000; // delay for 5 sec.
        int period = 30000; // repeat every 10 secs.

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                queerClient.getMyLocations(new VolleyMyCoordinatesCallback() {
                    @Override
                    public void onSuccess(QLocation[] queerLocations) {
                        if (qLocations == null || qLocations.length == 0)
                            qLocations = queerLocations;

                        PolygonOptions polygonOptions = new PolygonOptions();
                        for (QLocation queerLocation : qLocations) {
                            if (queerLocation.getQCoordinates().getType() == QCoordinate.CoordinateType.POINT) {
                                Coordinate latlog = queerLocation.getQCoordinates().getCoordinates().get(0);
                                LatLng testLocation4 =  new LatLng(latlog.getLat(), latlog.getLon());
                                mMap.addMarker(new MarkerOptions()
                                        .position(testLocation4)
                                        .title(queerLocation.getName())
                                        .snippet(queerLocation.getDescription() + "\n" + queerLocation.getAddress())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin5)));
                            } else if (queerLocation.getQCoordinates().getType() == QCoordinate.CoordinateType.POLYGON){
                                for (Coordinate coordinate1 : queerLocation.getQCoordinates().getCoordinates()) {
                                    polygonOptions.add(new LatLng(coordinate1.getLat(),coordinate1.getLon()));
                                }
                                mMap.addPolygon(polygonOptions.fillColor(Color.GREEN));

                            }
                        }

                        System.out.println("repeated");
                    }

                    @Override
                    public void onError(VolleyError result) {

                    }
                });


            }

        }, delay, period);
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


}