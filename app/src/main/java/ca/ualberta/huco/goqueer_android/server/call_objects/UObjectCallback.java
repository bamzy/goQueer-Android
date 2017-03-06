package ca.ualberta.huco.goqueer_android.server.call_objects;


import ca.ualberta.huco.goqueer_android.server.response_type.DataObject;

/**
 * Created by bamdad on 8/24/16.
 */
public interface UObjectCallback {
    void onGotObject(DataObject uObject);
}
