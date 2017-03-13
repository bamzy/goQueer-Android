package entity;

/**
 * Created by Circa Lab on 3/12/2017.
 */

public class QLocation {
    private long id;
    private String created_at;
    private String updated_at;
    private String coordinate;
    private String name;
    private String description;
    private String address;
    private QCoordinate coordinates;
    public long getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }
    public void setCoordinates(QCoordinate qCoordinate){
        coordinates = qCoordinate;
    }
    public QCoordinate getQCoordinates(){
        return coordinates;
    }




}
