package ca.ualberta.huco.goqueer_android.uoda.cache_provider;


import org.joda.time.DateTime;

import java.io.Serializable;

import ca.ualberta.huco.goqueer_android.uoda.response_type.DataObject;


/**
 * Created by bamdad on 8/23/16.
 */
public class UArchive implements Serializable {
    private DateTime expiry = new DateTime(0);
    private DataObject[] uObjects = new DataObject[0];

    public UArchive(){}

    public UArchive(DateTime expiry, DataObject[] uObjects) {
        this.expiry = expiry;
        this.uObjects = uObjects;
    }

    public void set(DateTime expiry, DataObject[] uObjects) {
        this.expiry = expiry;
        this.uObjects = uObjects;
    }

    public DateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(DateTime expiry) {
        this.expiry = expiry;
    }

    public DataObject[] getObjects() {
        return uObjects;
    }
}
