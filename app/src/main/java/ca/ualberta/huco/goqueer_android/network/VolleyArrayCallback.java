package ca.ualberta.huco.goqueer_android.network;

import com.android.volley.VolleyError;

import ca.ualberta.huco.goqueer_android.server.response_type.DataObject;


/**
 * Created by bamdad on 8/23/16.
 */
public interface VolleyArrayCallback {
    void onSuccess(DataObject[] dataObjects);
    void onError(VolleyError result);
}
