package ca.ualberta.goqueer.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by bamdad on 7/26/2016.
 */
public class VolleyQueueManager {
    private RequestQueue requestQueue;

    private static VolleyQueueManager instance;
    public VolleyQueueManager(Context context) {
        this.requestQueue =  Volley.newRequestQueue(context);;
    }
    public static VolleyQueueManager getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyQueueManager(context);
        }
        return instance;
    }
    public void addToRequestQueue(StringRequest request){
        requestQueue.add(request);
    }
    public void cancelAllRequest(){
        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }



}
