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
    private String show;
    private float zoom;
    private float tilt;
    private float bearing;

    public long getId() {
        return id;
    }


    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getShow() {
        return show;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public LatLng getCoordinateLatLng() {

        if (lat != null && lng != null)
            return new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
        else return null;

    }

    public String getName() {
        return name;
    }

    public float getZoom() {
        return zoom;
    }

    public float getTilt() {
        return tilt;
    }

    public float getBearing() {
        return bearing;
    }
}
