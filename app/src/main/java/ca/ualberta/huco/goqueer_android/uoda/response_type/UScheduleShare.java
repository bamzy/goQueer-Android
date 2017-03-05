package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ca.ualberta.dev.myualberta.config.Constants;

import static ca.ualberta.dev.myualberta.uoda.response_type.UScheduleShare.ScheduleShareTypes.Incoming;
import static ca.ualberta.dev.myualberta.uoda.response_type.UScheduleShare.ScheduleShareTypes.Outgoing;

/**
 * Created by msivia on 2016-08-04.
 */
public class UScheduleShare extends DataObject implements Serializable , Comparable{
    public enum ScheduleShareStatus {
        Requested("Requested"), Approved("Approved"), Rejected("Rejected"),Pending("Pending"), Retracted("Retracted"),Undetermined("Undetermined");

        private final String status;

        ScheduleShareStatus(String s) {
            status = s;
        }
    }

    public enum ScheduleShareTypes {
        Incoming("incoming"), Outgoing("outgoing");

        public final String key;

        ScheduleShareTypes(String s) {
            key = s;
        }
    }

    public int id;
    public String source;
    public UTerm uTerm;
    public UClass[] classes;
    public ScheduleShareTypes requestType;
    public ScheduleShareStatus shareStatus;
    public DateTime createdAt;

    public UScheduleShare(JsonObject attributes) {

        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);

        this.id = optGetInt(attributes, Constants.UODAResponse.ID);
        this.source = optGetString(attributes, Constants.UODAResponse.ScheduleShare.SOURCE);
        String termId = optGetString(attributes, Constants.UODAResponse.ScheduleShare.TERM_ID);
        String termTitle = optGetString(attributes, Constants.UODAResponse.ScheduleShare.TERM_TITLE);
        this.uTerm = new UTerm(termTitle,termId);
        String type = optGetString(attributes, Constants.UODAResponse.ScheduleShare.US_TYPE);

        this.createdAt = optGetDateString(attributes, Constants.UODAResponse.ScheduleShare.CREATED_AT);

        this.requestType = type.equals(ScheduleShareTypes.Incoming.key) ? ScheduleShareTypes.Incoming : ScheduleShareTypes.Outgoing;

        String approval_from = optGetString(attributes, Constants.UODAResponse.ScheduleShare.APPROVAL_FROM);
        String approval_to = optGetString(attributes, Constants.UODAResponse.ScheduleShare.APPROVAL_TO);
        switch (ScheduleShareStatus.valueOf(approval_from)){
            case Approved:
                switch (ScheduleShareStatus.valueOf(approval_to)){
                    case Requested:
                        shareStatus = ScheduleShareStatus.Pending;
                        break;
                    case Rejected:
                        shareStatus = ScheduleShareStatus.Rejected;
                        break;
                    case Approved:
                        shareStatus = ScheduleShareStatus.Approved;
                        break;
                }
            break;
            case Requested:
                switch (ScheduleShareStatus.valueOf(approval_to)){
                    case Rejected:
                        shareStatus = ScheduleShareStatus.Retracted;
                        break;
                    case Approved:
                        shareStatus = ScheduleShareStatus.Requested;
                        break;
                    case Requested:
                        shareStatus = ScheduleShareStatus.Retracted;
                        break;
                }
            break;
            case Rejected:
                shareStatus = ScheduleShareStatus.Retracted;
                break;
            default:
                shareStatus = ScheduleShareStatus.Undetermined;
        }
    }
    @Override
    public int compareTo(Object o) {
        return Integer.parseInt(((UScheduleShare)o).uTerm.getId()) - Integer.parseInt(this.uTerm.getId());

    }
}
