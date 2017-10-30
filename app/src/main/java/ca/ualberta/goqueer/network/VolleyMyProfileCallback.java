package ca.ualberta.goqueer.network;

import com.android.volley.VolleyError;

import entity.QProfile;


/**
 * Created by bamdad on 7/21/2016.
 */
public interface VolleyMyProfileCallback {

    void onSuccess(QProfile[] profile);
    void onError(VolleyError result);
}

