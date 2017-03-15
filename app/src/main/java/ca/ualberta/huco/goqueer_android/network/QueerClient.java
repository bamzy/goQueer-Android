package ca.ualberta.huco.goqueer_android.network;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

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
import entity.QLocation;

/**
 * Created by Circa Lab on 2/14/2017.
 */

public class QueerClient {
    private final String url;
    private final RequestQueue queue;
    private Context context;
    private String device_id;
    public QueerClient(Context context) {
        this.context = context;
        device_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        queue = Volley.newRequestQueue(context);
        Log.w(Constants.LOG_TAG,device_id);
        url =Constants.GO_QUEER_BASE_SERVER_URL ;
    }


    public void getMyLocations(final VolleyMyCoordinatesCallback volleyMyCoordinatesCallback){
        String myLocationsUrl = url + "/client/getMyLocations?device_id=" + device_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, myLocationsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.w(Constants.LOG_TAG,response);
                        Gson gson = new Gson();
                        ArrayList<QCoordinate> qCoordinates = new ArrayList<>();
                        QLocation[] discoveredQLocations = gson.fromJson(response, QLocation[].class);
                        for (QLocation location : discoveredQLocations) {
//                            Log.w(Constants.LOG_TAG,location.getCoordinate());
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


    public void getAllLocations(final VolleyMyCoordinatesCallback volleyMyCoordinatesCallback){
        String allLocationsUrl = url + "/client/getAllLocations?device_id=" + device_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, allLocationsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.w(Constants.LOG_TAG,response);
                        Gson gson = new Gson();
                        ArrayList<QCoordinate> qCoordinates = new ArrayList<>();
                        QLocation[] discoveredQLocations = gson.fromJson(response, QLocation[].class);
                        for (QLocation location : discoveredQLocations) {
//                            Log.w(Constants.LOG_TAG,location.getCoordinate());
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

}
