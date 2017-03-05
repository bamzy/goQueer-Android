package ca.ualberta.huco.goqueer_android.uoda.call_objects;

import ca.ualberta.dev.myualberta.uoda.response_type.DataObject;

/**
 * Created by bamdad on 8/19/16.
 */

// Returns null object on fails to avoid having to handle fail call.
// uObject will contain have uArray set if returning multiple values.
public interface UMultiCallback {
    void onGotObjects(DataObject[] uObjects);
}