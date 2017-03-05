package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class UExam extends DataObject implements Serializable {

    private int roomID = 0;
    private DateTime startDateTime;
    private DateTime endDateTime ;
    private boolean isCombined = false;
    private boolean isTentative = false;

    public UExam(JsonObject attributes) {
        String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

        this.roomID = optGetInt(attributes, Constants.UODAResponse.MyCourses.ROOM_ID);

        this.startDateTime = optGetDateWithFormat(attributes, Constants.UODAResponse.Exams.EXAM_START, dateTimeFormat);
        this.endDateTime = optGetDateWithFormat(attributes, Constants.UODAResponse.Exams.EXAM_END, dateTimeFormat);

        this.isCombined = optGetBool(attributes, Constants.UODAResponse.Exams.IS_COMBINED);
        this.isTentative = optGetBool(attributes, Constants.UODAResponse.Exams.IS_TENTATIVE);


    }

    public int getRoomID() {
        return roomID;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public boolean isCombined() {
        return isCombined;
    }

    public boolean isTentative() {
        return isTentative;
    }
}
