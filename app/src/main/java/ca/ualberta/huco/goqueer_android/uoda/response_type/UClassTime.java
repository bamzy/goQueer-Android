package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class UClassTime extends DataObject implements Serializable {

    private long classId = 0;

    private long roomId = 0;
    private DateTime startTime;
    private DateTime endTime;
    private DateTime startDate;
    private DateTime endDate;

    private boolean  sunday = false;
    private boolean monday = false;
    private boolean tuesday = false;
    private boolean  wednesday = false;
    private boolean  thursday = false;
    private boolean  friday = false;
    private boolean  saturday = false;
    private boolean[] allWeekdays;

    private URoom room;

    public UClassTime(JsonObject attributes) {
        super(attributes);

        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        this.timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);

        this.classId = optGetLong(attributes, Constants.UODAResponse.MyCourses.CLASS_ID);
        this.roomId  = optGetLong(attributes, Constants.UODAResponse.MyCourses.ROOM_ID);
        this.startDate = new DateTime(optGetDateString(attributes, Constants.UODAResponse.START_DATE));
        this.endDate = new DateTime(optGetDateString(attributes, Constants.UODAResponse.END_DATE));
        this.startTime = new DateTime(optGetTimeString(attributes, Constants.UODAResponse.MyCourses.START_TIME));
        this.endTime = new DateTime(optGetTimeString(attributes, Constants.UODAResponse.MyCourses.END_TIME));
        this.monday = optGetBool(attributes, Constants.UODAResponse.MyCourses.MONDAY);
        this.tuesday= optGetBool(attributes, Constants.UODAResponse.MyCourses.TUESDAY);
        this.wednesday= optGetBool(attributes, Constants.UODAResponse.MyCourses.WEDNESDAY);
        this.thursday = optGetBool(attributes, Constants.UODAResponse.MyCourses.THURSDAY);
        this.friday= optGetBool(attributes, Constants.UODAResponse.MyCourses.FRIDAY);
        this.saturday = optGetBool(attributes, Constants.UODAResponse.MyCourses.SATURDAY);
        this.sunday= optGetBool(attributes, Constants.UODAResponse.MyCourses.SUNDAY);

        this.allWeekdays = new boolean[]{sunday, monday, tuesday, wednesday, thursday, friday, saturday};

        this.room = (URoom) firstOrDefault(subObjectFromData(attributes, Constants.UODAResponse.MyCourses.ROOM_TAG, URoom.class), null);
    }

    public long getClassId() {
        return classId;
    }

    public boolean hasWeekdays(){
        for (boolean weekday: allWeekdays) {
            if (weekday) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Constants.Weekday> getWeekdays() {
        ArrayList<Constants.Weekday>weekdayList = new ArrayList<>(7);

        for (int i=0; i<allWeekdays.length; i++) {
            if (allWeekdays[i]) {
                weekdayList.add(Constants.Weekday.values()[i]);
            }
        }

        return weekdayList;
    }

    public long getRoomId() {
        return roomId;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }
}
