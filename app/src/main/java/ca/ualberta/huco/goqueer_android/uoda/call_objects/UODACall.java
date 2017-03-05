package ca.ualberta.huco.goqueer_android.uoda.call_objects;

import android.util.Log;


import com.android.volley.VolleyError;

import java.lang.reflect.Array;
import java.util.HashMap;

import ca.ualberta.huco.goqueer_android.config.Constants;
import ca.ualberta.huco.goqueer_android.network.Singletons;
import ca.ualberta.huco.goqueer_android.network.VolleyArrayCallback;
import ca.ualberta.huco.goqueer_android.uoda.response_type.DataObject;


/**
 * Created by bamdad on 8/18/16.
 */
public class UODACall {
    private CallDelegate callDelegate = Singletons.authManager;
    private UMultiCallback callback;
    private HashMap<String, String> parameters;
    private String endURI;
    private Class<? extends DataObject[]> returnType;
    private boolean bypassChecks = false;

    private VolleyArrayCallback handleCallback = new VolleyArrayCallback() {
        @Override
        public void onSuccess(DataObject[] dataObjects) {
            callback.onGotObjects(dataObjects);
        }

        @Override
        public void onError(VolleyError result) {
            callback.onGotObjects(null);
            callDelegate.failedCall();
        }
    };

    private VolleyArrayCallback handleCallbackNoNull = new VolleyArrayCallback() {
        @Override
        public void onSuccess(DataObject[] dataObjects) {
            if (dataObjects == null) {
                callback.onGotObjects((DataObject[]) Array.newInstance(returnType.getComponentType(), 0));
            } else {
                callback.onGotObjects(dataObjects);
            }
        }

        @Override
        public void onError(VolleyError result) {
            callback.onGotObjects((DataObject[]) Array.newInstance(returnType.getComponentType(), 0));

            if (result != null && result.networkResponse != null) {
                Log.e("Network Error", result.networkResponse.toString());
            }
            callDelegate.failedCall();
        }
    };

    public UODACall(String endURI, HashMap<String, String> parameters, Class<? extends DataObject[]> returnType, UMultiCallback callback) {
        this.endURI = endURI;
        this.parameters = parameters;
        this.endURI = endURI;
        this.returnType = returnType;
        this.callback = callback;
    }

    public UODACall(String endURI, HashMap<String, String> parameters, Class<? extends DataObject[]> returnType, boolean bypassExpiry, UMultiCallback callback) {
        this(endURI, parameters, returnType, callback);
        this.bypassChecks = true;

    }

    public void getUODA() {
        if (bypassChecks) {
            callDelegate.getUODAClient().get(endURI, parameters, (Class<? extends DataObject>) returnType.getComponentType(), handleCallbackNoNull);
            return;
        }

        callDelegate.checkNetworkAndTokenExpiry(new BoolCallback() {
            @Override
            public void onDone(boolean boolFlag) {
                if (boolFlag) {
                    callDelegate.getUODAClient().get(endURI, parameters, (Class<? extends DataObject>) returnType.getComponentType(), handleCallback);
                } else {
                    Log.e(Constants.LOG_TAG, "Aborted call to " + endURI);
                    handleCallback.onError(null);
                }
            }
        });
    }

    /**
     * Like getUODA() but returns an empty array instead of null on fail.
     */
    public void getUODANoNull() {
        if (bypassChecks) {
            callDelegate.getUODAClient().get(endURI, parameters, (Class<? extends DataObject>) returnType.getComponentType(), handleCallbackNoNull);
            return;
        }

        callDelegate.checkNetworkAndTokenExpiry(new BoolCallback() {
            @Override
            public void onDone(boolean boolFlag) {
                if (boolFlag) {
                    callDelegate.getUODAClient().get(endURI, parameters, (Class<? extends DataObject>) returnType.getComponentType(), handleCallbackNoNull);
                } else {
                    Log.e(Constants.LOG_TAG, "Aborted call to " + endURI);
                    handleCallbackNoNull.onError(null);
                }
            }
        });
    }

    public void putUODA() {
        callDelegate.checkNetworkAndTokenExpiry(new BoolCallback() {
            @Override
            public void onDone(boolean boolFlag) {
                if (boolFlag) {
                    callDelegate.getUODAClient().put(endURI, parameters, handleCallback);
                } else {
                    Log.e(Constants.LOG_TAG, "Aborted call to " + endURI);
                    handleCallbackNoNull.onError(null);
                }
            }
        });


    }

    public void postUODA() {
        callDelegate.checkNetworkAndTokenExpiry(new BoolCallback() {
            @Override
            public void onDone(boolean boolFlag) {
                if (boolFlag) {
                    callDelegate.getUODAClient().post(endURI, parameters, handleCallback);
                } else {
                    Log.e(Constants.LOG_TAG, "Aborted call to " + endURI);
                }
            }
        });
    }
}
