package entity;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Circa Lab on 3/12/2017.
 */

public class QProfile {
    private long id;
    private String name;
    private String lat;
    private String lng;

    public long getId() {
        return id;
    }


    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public LatLng getCoordinateLatLng() {

        if (lat != null && lng != null)
            return new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
        else return null;

    }

    public String getName() {
        return name;
    }



}
