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
    private int passwordProtected;
    private String password;

    public long getId() {
        return id;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public void setTilt(float tilt) {
        this.tilt = tilt;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getShow() {
        if (show!= null)
        return show;
        else return "";
    }

    public boolean isPasswordProtected() {
        if (passwordProtected == 1)
            return true;
        else
            return false;
    }

    public String getPassword() {
        return password;
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
