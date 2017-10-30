package ca.ualberta.goqueer.network;

import com.android.volley.VolleyError;


/**
 * Created by bamdad on 7/21/2016.
 */
public interface VolleyMyHintCallback {

    void onSuccess(String hint);
    void onError(VolleyError result);
}

