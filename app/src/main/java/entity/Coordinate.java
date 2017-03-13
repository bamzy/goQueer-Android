package entity;

/**
 * Created by Circa Lab on 3/12/2017.
 */

public class Coordinate {
    private double lat;
    private double lon;

    public Coordinate(String  input) {
        String temp="";
        String[] coords= null;
//        if (input.length()>3)
//            temp = input.substring(1,input.length()-1);
        if (input != null ) {
            coords = input.split(",");
            if (coords.length == 2) {
                lon = Double.parseDouble(coords[0]);
                lat = Double.parseDouble(coords[1]);
            } else {
                lat = 0;
                lon = 0;
            }

        }
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
