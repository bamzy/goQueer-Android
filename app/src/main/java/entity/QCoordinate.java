package entity;

import java.util.ArrayList;

/**
 * Created by Circa Lab on 3/12/2017.
 */

public class QCoordinate {
    public enum CoordinateType  {POLYGON, POINT}
    private CoordinateType type;
    private ArrayList<Coordinate> coordinates = new ArrayList();
    private String coordinatesAsStr;

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public QCoordinate(String coordinate) {
        String typeString = "\"type\":";
        int index = coordinate.lastIndexOf(typeString);
        String tempType = coordinate.substring(index+typeString.length());
        if (tempType.contains("Polygon"))
            this.type = CoordinateType.POLYGON;
        if (tempType.contains("Point"))
            this.type = CoordinateType.POINT;
        String coordinatesString = "\"coordinates\":";
        index = coordinate.lastIndexOf(coordinatesString);
        String actualCoordinates;
        if (type == CoordinateType.POLYGON){
            actualCoordinates = coordinate.substring(index+coordinatesString.length()+3);
            coordinatesAsStr= actualCoordinates.substring(0,actualCoordinates.length()-5);

        }
        if (type == CoordinateType.POINT){
            actualCoordinates = coordinate.substring(index+coordinatesString.length()+1);
            coordinatesAsStr = actualCoordinates.substring(0,actualCoordinates.length()-3);
        }

        prepareCoordinateList(coordinatesAsStr);




    }

    private void prepareCoordinateList(String coordinatesAsStr) {
        String[] temp = coordinatesAsStr.split("],");
        for (String s : temp) {
            coordinates.add(new Coordinate(s.replace('[',' ')));
        }


    }

    public CoordinateType getType() {
        return type;
    }

    public String getCoordinatesAsStr() {
        return coordinatesAsStr;
    }


}
