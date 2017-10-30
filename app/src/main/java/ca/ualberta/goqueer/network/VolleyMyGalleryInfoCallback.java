package ca.ualberta.goqueer.network;

import com.android.volley.VolleyError;

import entity.QGallery;


/**
 * Created by bamdad on 7/21/2016.
 */
public interface VolleyMyGalleryInfoCallback {

    void onSuccess(QGallery gallery);
    void onError(VolleyError result);
}

