package ca.ualberta.huco.goqueer_android.network;

import com.android.volley.VolleyError;

import ca.ualberta.huco.goqueer_android.server.response_type.DataObject;


/**
 * Created by bamdad on 7/21/2016.
 */
public interface VolleySingleCallback {

    void onSuccess(DataObject dataObject);
    void onError(VolleyError result);
}

