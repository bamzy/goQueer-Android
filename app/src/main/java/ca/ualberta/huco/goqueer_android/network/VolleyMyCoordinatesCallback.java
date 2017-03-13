package ca.ualberta.huco.goqueer_android.network;

import com.android.volley.VolleyError;

import entity.QLocation;


/**
 * Created by bamdad on 7/21/2016.
 */
public interface VolleyMyCoordinatesCallback {

    void onSuccess(QLocation[] queerQLocations);
    void onError(VolleyError result);
}

