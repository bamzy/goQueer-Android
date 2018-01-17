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
    private long gallery_id;

    public String getData_display_mode() {
        return data_display_mode;
    }

    public void setData_display_mode(String data_display_mode) {
        this.data_display_mode = data_display_mode;
    }

    private String data_display_mode;
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

    public long getGallery_id() {
        return gallery_id;
    }
}
