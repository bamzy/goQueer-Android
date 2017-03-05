package ca.ualberta.huco.goqueer_android.uoda.response_type;

import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.io.Serializable;

import ca.ualberta.dev.myualberta.config.Constants;

/**
 * Created by bamdad on 8/14/2016.
 */
public class UTerm extends DataObject implements Serializable {

    private String title = "";
    private DateTime startDate = new DateTime(0);
    private DateTime endDate = new DateTime(0);

    public UTerm() {
    }

    public UTerm(JsonObject jsonObject) {
        super(jsonObject);
        this.title = optGetString(jsonObject, Constants.UODAResponse.MyCourses.TITLE);
        this.endDate = optGetDateString(jsonObject, Constants.UODAResponse.END_DATE);
        this.startDate = optGetDateString(jsonObject, Constants.UODAResponse.START_DATE);
    }

    public String getTitle() {
        return title;
    }

    public UTerm(String title) {
        super(null);
        this.title = title;
    }
    public UTerm( String title,String id ) {
        super.id = id;
        this.title = title;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    /**
     * Checks if term is current to today.
     *
     * @return True is term ends in the future and begins within a year.
     */
    public boolean isCurrent() {
        if (endDate == null || startDate == null) {
            return false;
        }

        return endDate.isAfterNow() && startDate.minusMonths(12).isBeforeNow();
    }

    public boolean classesAvailableForTerm() {

        DateTime today = new DateTime();

        Integer todayMonth = today.getMonthOfYear();
        Integer termMonth = startDate.getMonthOfYear();

        Integer todayYear = today.getYear();
        Integer termYear = startDate.getYear();

        if ((termYear.equals(todayYear)) && (termMonth > todayMonth)) {

            if ((termMonth >= 9 && todayMonth <= 2) || (termMonth >= 5 && todayMonth <= 1)) {
                return false;
            }

        } else if (termYear > todayYear) {

            if ((termMonth <= 4 && todayMonth <= 3) || (termMonth > 4)) {
                return false;
            }
        }

        return true;
    }
}
