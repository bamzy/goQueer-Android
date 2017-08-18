package ca.ualberta.huco.goqueer_android.network;

import com.android.volley.VolleyError;

import entity.QGallery;


/**
 * Created by bamdad on 7/21/2016.
 */
public interface VolleyMyHintCallback {

    void onSuccess(String hint);
    void onError(VolleyError result);
}

