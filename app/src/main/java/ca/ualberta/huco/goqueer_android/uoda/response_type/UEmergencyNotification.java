package ca.ualberta.huco.goqueer_android.uoda.response_type;


import com.google.gson.JsonObject;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.io.Serializable;

import ca.ualberta.dev.myualberta.config.Constants;


/**
 * Created by bamdad on 8/2/2016.
 */
public class UEmergencyNotification extends DataObject implements Serializable {
    private String headline;
    private String content;
    private String sentDate;
    private String affectedLocation;

    public UEmergencyNotification(JsonObject jsonObject) {
        if (jsonObject == null)
            return;
        id = optGetString(jsonObject, Constants.UOFA_NOTIFICATION_RESPONSE_ID_KEY);
        headline = optGetString(jsonObject, Constants.UOFA_NOTIFICATION_RESPONSE_HEADLINE_KEY, "");
        content = optGetString(jsonObject, Constants.UOFA_NOTIFICATION_RESPONSE_CONTENT_KEY, "");
        sentDate = optGetString(jsonObject, Constants.UOFA_NOTIFICATION_RESPONSE_SENT_DATE_KEY, "");
        affectedLocation = optGetString(jsonObject, Constants.UOFA_NOTIFICATION_RESPONSE_AFFECTED_LOCATION_KEY, "");

    }

    public String getHeadline() {
        return headline;
    }

    public String getContent() {
        return content;
    }

    public String getSentDate() {
        return sentDate;
    }

    public DateTime getSentDateTime() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.Emergency.NOTIFICATION_DATE_TIME_FORMAT);
        try {
            return formatter.parseDateTime(sentDate);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new DateTime(0);
        }
    }

    public String getAffectedLocation() {
        return affectedLocation;
    }
}
