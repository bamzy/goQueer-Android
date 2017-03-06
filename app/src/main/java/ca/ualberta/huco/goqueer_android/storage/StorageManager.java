package ca.ualberta.huco.goqueer_android.storage;

import android.content.Context;
import android.util.Log;

import ca.ualberta.huco.goqueer_android.config.Constants;
import ca.ualberta.huco.goqueer_android.server.response_type.AuthPersonDetails;
import ca.ualberta.huco.goqueer_android.server.response_type.UODAToken;


/**
 * Created by bamdad on 7/22/2016.
 */
public class StorageManager {
    private static SecurePreferences secureSharedPreferences;

    private AuthPersonDetails personDetails;
    private UODAToken credentials;

    public StorageManager(Context context) {
        secureSharedPreferences = new SecurePreferences(context, "my-preferences", "SometopSecretKey1235", true);
    }

    public void addPair(String key, String value) {
        secureSharedPreferences.put(key, value);
    }

    public String getString(String key) {
        return secureSharedPreferences.getString(key);
    }

    public void clear() {
        this.personDetails = null;
        this.credentials = null;
        secureSharedPreferences.clear();
    }

    public void saveCredentials(UODAToken uodaResponse) {
        clearCredentials();

        this.addPair(Constants.StorageKeys.Credential.CCID, uodaResponse.getCcid());
        this.addPair(Constants.StorageKeys.Credential.TOKEN, uodaResponse.getAccessToken());
        this.addPair(Constants.StorageKeys.Credential.PERSON_ID, uodaResponse.getPersonId());
        this.addPair(Constants.StorageKeys.Credential.REFRESH, uodaResponse.getRefreshToken());
        this.addPair(Constants.StorageKeys.Credential.EXPIRES_IN, Integer.toString(uodaResponse.getExpiresInt()));
//        this.addPair(Constants.StorageKeys.Credential.EXPIRY, Long.toString(uodaResponse.getExpiration().getMillis()));

        UODAToken credentials = loadCredentials();
        Log.w(Constants.LOG_TAG, "Saved new token: " + credentials.getAccessToken());
        Log.w(Constants.LOG_TAG, "Expires: " + credentials.getExpiration());
    }

    private void clearCredentials() {
        this.credentials = null;

        secureSharedPreferences.removeValue(Constants.StorageKeys.Credential.CCID);
        secureSharedPreferences.removeValue(Constants.StorageKeys.Credential.TOKEN);
        secureSharedPreferences.removeValue(Constants.StorageKeys.Credential.PERSON_ID);
        secureSharedPreferences.removeValue(Constants.StorageKeys.Credential.REFRESH);
        secureSharedPreferences.removeValue(Constants.StorageKeys.Credential.EXPIRES_IN);
        secureSharedPreferences.removeValue(Constants.StorageKeys.Credential.EXPIRY);
    }

    public void savePersonalInfo(AuthPersonDetails personDetails) {
        this.personDetails = null;

        addPair(Constants.StorageKeys.Personal.CCID, personDetails.getCcid());
        addPair(Constants.StorageKeys.Personal.FIRST, personDetails.getFirstName());
        addPair(Constants.StorageKeys.Personal.LAST, personDetails.getLastName());
        addPair(Constants.StorageKeys.Personal.DISPLAY, personDetails.getDisplayName());
        addPair(Constants.StorageKeys.Personal.MAIL, personDetails.getMail());
        addPair(Constants.StorageKeys.Personal.PREFIX, personDetails.getPrefix());

    }

    public void saveGuestPersonal() {
        addPair(Constants.StorageKeys.Personal.CCID, Constants.GUEST_ID);
        addPair(Constants.StorageKeys.Personal.FIRST, "");
        addPair(Constants.StorageKeys.Personal.LAST, "");
        addPair(Constants.StorageKeys.Personal.DISPLAY, Constants.GUEST_NAV_MENU_VALUE);
        addPair(Constants.StorageKeys.Personal.MAIL, "");
        addPair(Constants.StorageKeys.Personal.PREFIX, "");
    }

    public void saveDemoPersonal() {
        addPair(Constants.StorageKeys.Personal.CCID, Constants.GUEST_ID);
        addPair(Constants.StorageKeys.Personal.FIRST, "");
        addPair(Constants.StorageKeys.Personal.LAST, "");
        addPair(Constants.StorageKeys.Personal.DISPLAY, Constants.DEMO_NAV_MENU_VALUE);
        addPair(Constants.StorageKeys.Personal.MAIL, "");
        addPair(Constants.StorageKeys.Personal.PREFIX, "");
    }

    /**
     * loads saved credentials into a UserCredential Object
     *
     * @return load credentials from storage into UserCredentials class
     */
    public UODAToken loadCredentials() {
        if (credentials == null) {
            this.credentials = new UODAToken(this);
        }
        return credentials;
    }

    public AuthPersonDetails loadPersonalInfo() {
        if (personDetails == null) {
            this.personDetails = new AuthPersonDetails(this);
        }

        return personDetails;
    }

    public boolean personalInfoValid(AuthPersonDetails personDetails) {
        if (personDetails == null || personDetails.getCcid() == null) {
            return false;
        }

        UODAToken credentials = loadCredentials();

        return credentials.getCcid().equals(personDetails.getCcid()) &&
                personDetails.getCcid().length() > 0;
    }

    public boolean authTokenExpired() {
        UODAToken credentials = loadCredentials();

        if (credentials.getExpiration() == null) {
                return true;
        }

        return credentials.getExpiration().isBeforeNow();
    }
}
