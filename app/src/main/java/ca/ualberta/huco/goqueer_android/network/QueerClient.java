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

import ca.ualberta.huco.goqueer_android.config.Constants;
import entity.QueerCoordinate;
import entity.Location;

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
        url =Constants.GO_QUEER_BASE_SERVER_URL + "/client/getMyLocations?device_id=" + device_id;
    }


    public void getMyLocations(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w(Constants.LOG_TAG,response);
                        Gson gson = new Gson();
                        Location[] discoveredLocations = gson.fromJson(response, Location[].class);
                        for (Location location : discoveredLocations) {
                            //Gson gson = new Gson();
//                            String test = "[{\"type\":Feature,\"properties\":{},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[-113.6036539077759,53.515741119665684],[-113.6036539077759,53.515741119665684],[-113.60373973846437,53.51681284304222],[-113.60373973846437,53.51681284304222],[-113.60721588134767,53.51660870734615],[-113.60721588134767,53.51660870734615],[-113.60760211944581,53.51571560211204],[-113.60760211944581,53.51571560211204],[-113.60367536544801,53.515750688744355],[-113.60365927219392,53.515741119665684],[-113.60365927219392,53.515741119665684],[-113.60613226890564,53.51622913992179],[-113.6036539077759,53.515741119665684]]]}}]";
                            Log.w(Constants.LOG_TAG,location.getCoordinate());


                            QueerCoordinate queerCoordinate = new QueerCoordinate(location.getCoordinate());
                            queerCoordinate.getType();
                            queerCoordinate.getCoordinatesAsStr();

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

}
