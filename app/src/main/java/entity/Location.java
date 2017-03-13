package entity;

/**
 * Created by Circa Lab on 3/12/2017.
 */

public class Location {
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

        long id;
        String created_at;
        String updated_at;
        String coordinate;
        String name;
        String description;
        String address;


}
