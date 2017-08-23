package ca.ualberta.huco.goqueer_android.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

import ca.ualberta.huco.goqueer_android.config.Constants;
import entity.QCoordinate;
import entity.QGallery;
import entity.QLocation;
import entity.QMedia;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Circa Lab on 2/14/2017.
 */

public class QueerClient {
    private final String url;
    private final RequestQueue queue;
    private Context context;
    private String device_id;
    private long discoveryStatus;

    public QueerClient(Context context) {
        this.context = context;
        device_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        queue = Volley.newRequestQueue(context);
        Log.w(Constants.LOG_TAG,device_id);
        url =Constants.GO_QUEER_BASE_SERVER_URL ;
    }


    public void getMyLocations(final VolleyMyCoordinatesCallback volleyMyCoordinatesCallback, String profileName){
        String myLocationsUrl = url + "/client/getMyLocations?device_id=" + device_id + "&profile_name=" + profileName  ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, myLocationsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        if (response.length() !=0) {
                            QLocation[] discoveredQLocations = gson.fromJson(response, QLocation[].class);
                            for (QLocation location : discoveredQLocations) {
                                location.setCoordinates(new QCoordinate(location.getCoordinate()));

                            }
                            volleyMyCoordinatesCallback.onSuccess(discoveredQLocations);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(Constants.LOG_TAG,error.getCause());
            }
        });
        queue.add(stringRequest);
    }


    public void getAllLocations(final VolleyMyCoordinatesCallback volleyMyCoordinatesCallback,String profileName){
        String allLocationsUrl = url + "/client/getAllLocations?device_id=" + device_id+ "&profile_name=" + profileName  ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, allLocationsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();

                        QLocation[] discoveredQLocations = gson.fromJson(response, QLocation[].class);
                        for (QLocation location : discoveredQLocations) {
                            location.setCoordinates(new QCoordinate(location.getCoordinate()));
                        }
                        volleyMyCoordinatesCallback.onSuccess(discoveredQLocations);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(Constants.LOG_TAG,error.getCause());
            }
        });
        queue.add(stringRequest);
    }


    public void getMyGallery(final VolleyMyGalleriesCallback volleyMyGalleriesCallback, long gallery_id){
        String allLocationsUrl = url + "/client/getGalleryMediaById?gallery_id=" + gallery_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, allLocationsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();

                        QMedia[] myMedias = gson.fromJson(response, QMedia[].class);
//                        for (QLocation location : discoveredQLocations) {
//                            location.setCoordinates(new QCoordinate(location.getCoordinate()));
//                        }
                        volleyMyGalleriesCallback.onSuccess(myMedias);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(Constants.LOG_TAG,error.getCause());
            }
        });
        queue.add(stringRequest);
    }


    public void getMyGalleryInfo(final VolleyMyGalleryInfoCallback volleyMyGalleryInfoCallback, long id){
        String allLocationsUrl = url + "/client/getGalleryById?gallery_id=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, allLocationsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        QGallery[] gallery = gson.fromJson(response, QGallery[].class);
                        if (gallery.length>0)
                            volleyMyGalleryInfoCallback.onSuccess(gallery[0]);
                        else volleyMyGalleryInfoCallback.onSuccess(null);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(Constants.LOG_TAG,error.getCause());
            }
        });
        queue.add(stringRequest);
    }


    public void getHint(final VolleyMyHintCallback volleyMyHintCallback){
        String allLocationsUrl = url + "/client/getHint?device_id=" + device_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, allLocationsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        volleyMyHintCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(Constants.LOG_TAG,error.getCause());
            }
        });
        queue.add(stringRequest);
    }

    public void setDiscoveryStatus(final VolleySetDiscoveryCallback volleySetDiscoveryCallback ,long locationId,String profileName) {
        String newurl = url + "/client/setDiscoveryStatus?location_id=" + locationId +"&device_id=" + device_id+ "&profile_name=" + profileName  ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, newurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        volleySetDiscoveryCallback.onSuccess(true);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(Constants.LOG_TAG,error.getCause());
            }
        });
        queue.add(stringRequest);
    }
}
