package ca.ualberta.huco.goqueer_android.uoda.response_type;

import java.io.Serializable;

import ca.ualberta.huco.goqueer_android.config.Constants;
import ca.ualberta.huco.goqueer_android.storage.StorageManager;


/**
 * Created by bamdad on 9/21/16.
 */

public class AuthPersonDetails extends DataObject implements Serializable {
    private String ccid;
    private String firstName;
    private String lastName;
    private String displayName;
    private String mail;
    private String prefix;

    public AuthPersonDetails() {

    }

    public AuthPersonDetails(StorageManager storageManager) {
        this.ccid = storageManager.getString(Constants.StorageKeys.Personal.CCID);
        this.firstName = storageManager.getString(Constants.StorageKeys.Personal.FIRST);
        this.lastName = storageManager.getString(Constants.StorageKeys.Personal.LAST);
        this.displayName = storageManager.getString(Constants.StorageKeys.Personal.DISPLAY);
        this.mail = storageManager.getString(Constants.StorageKeys.Personal.MAIL);
        this.prefix = storageManager.getString(Constants.StorageKeys.Personal.PREFIX);
    }

    public String getCcid() {
        return ccid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMail() {
        return mail;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
