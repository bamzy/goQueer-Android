package ca.ualberta.huco.goqueer_android.uoda.response_type;



import com.google.gson.JsonObject;


import org.joda.time.DateTime;

import java.io.Serializable;

import ca.ualberta.huco.goqueer_android.config.Constants;
import ca.ualberta.huco.goqueer_android.network.AuthManager;
import ca.ualberta.huco.goqueer_android.storage.StorageManager;

/**
 * Created by bamdad on 7/15/2016.
 */
public class UODAToken extends DataObject implements Serializable {
    private String ccid;
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private String personId;
    private int expiresInt = 0;
    private DateTime expiration;

    public UODAToken(JsonObject attributes) {
        this.ccid = optGetString(attributes, Constants.UODAResponse.Person.CCID_TAG);
        this.accessToken = optGetString(attributes, Constants.UODAResponse.Person.ACCESS_TOKEN);
        this.tokenType = optGetString(attributes, Constants.UODAResponse.Person.TOKEN_TYPE);
        this.refreshToken = optGetString(attributes, Constants.UODAResponse.Person.REFRESH_TOKEN);
        this.personId = optGetString(attributes, Constants.UODAResponse.Person.PERSON_ID_TAG);
        this.expiresInt = optGetInt(attributes, Constants.UODAResponse.Person.EXPIRES_IN);

        /*
        if(HappeningsTime.debugTime != null) {
            this.expiresInt = 120;
        }
        */

        this.expiration = DateTime.now().plusSeconds(this.expiresInt - Constants.Time.TOKEN_EXPIRATION_PADDING_SEC);
    }

    public UODAToken(StorageManager storageManager) {
        this.ccid = storageManager.getString(Constants.StorageKeys.Credential.CCID);
        this.accessToken = storageManager.getString(Constants.StorageKeys.Credential.TOKEN);
        this.tokenType = "";
        this.refreshToken = storageManager.getString(Constants.StorageKeys.Credential.REFRESH);
        this.personId = storageManager.getString(Constants.StorageKeys.Credential.PERSON_ID);
        try {
            this.expiresInt = Integer.parseInt(storageManager.getString(Constants.StorageKeys.Credential.EXPIRES_IN));
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }

        try {
            long expirationMillis = Long.parseLong(storageManager.getString(Constants.StorageKeys.Credential.EXPIRY));
            this.expiration = new DateTime(expirationMillis);
        } catch (NumberFormatException e) {
            this.expiration = null;
        }
    }

    public AuthManager.Status storedAuthStatus() {
        if (credentialsEmpty()) {
            return AuthManager.Status.None;
        }

        if (userIsGuest()) {
            return AuthManager.Status.Guest;
        } else {
            return AuthManager.Status.User;
        }
    }

    /**
     * check to make sure necessary fields were provided
     *
     * @return return true if saved credentials not found
     */
    private boolean credentialsEmpty() {
        return (accessToken == null || expiresInt == 0);
    }

    private boolean userIsGuest() {
        return ccid.equals(Constants.GUEST_ID);
    }

    public String getCcid() {
        return ccid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getPersonId() {
        return personId;
    }

    public int getExpiresInt() {
        return expiresInt;
    }

    public DateTime getExpiration() {return expiration;}

    public void setExpiration(String millis) {
        try {
            this.expiration = new DateTime(Long.parseLong(millis));
        } catch (NumberFormatException e) {
            this.expiration = null;
        }
    }

    public void setCcid(String ccid) {
        this.ccid = ccid;
    }
}
