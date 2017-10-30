package ca.ualberta.goqueer.network;

import com.android.volley.VolleyError;

import entity.QMedia;


/**
 * Created by bamdad on 7/21/2016.
 */
public interface VolleyMyGalleriesCallback {

    void onSuccess(QMedia[] medias);
    void onError(VolleyError result);
}

