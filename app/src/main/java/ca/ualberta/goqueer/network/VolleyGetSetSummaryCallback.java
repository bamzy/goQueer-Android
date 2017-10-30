package ca.ualberta.goqueer.network;


import com.android.volley.VolleyError;

/**
 * Created by bamdad on 7/21/2016.
 */
public interface VolleyGetSetSummaryCallback {

    void onSuccess(String status);
    void onError(VolleyError result);
}

