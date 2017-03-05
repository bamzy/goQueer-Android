package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import java.io.Serializable;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class URoom extends DataObject implements Serializable {

    private String roomName = "0";
    private UBuilding building = new UBuilding();

    public URoom() {
    }

    public URoom(JsonObject attributes) {
        super(attributes);

        this.roomName = optGetString(attributes, Constants.UODAResponse.MyCourses.NUMBER);

        this.building = (UBuilding) firstOrDefault(subObjectFromData(attributes, Constants.UODAResponse.MyCourses.BUILDING, UBuilding.class), new UBuilding(null));
    }

    public String getRoomName() {
        return roomName;
    }

    public UBuilding getBuilding() {
        return building;
    }

    public long getRoomId() {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
