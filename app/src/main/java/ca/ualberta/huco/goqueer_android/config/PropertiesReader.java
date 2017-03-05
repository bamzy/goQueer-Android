package ca.ualberta.huco.goqueer_android.config;

/**
 * Created by bamdad on 7/13/2016.
 */

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesReader {
    private Context context;
    public static final String PROPERTIES_FILE_NAME = Constants.PROPERTIES_FILE_NAME;
    private Properties properties;
    private static PropertiesReader instance;

    public PropertiesReader(Context context) {
        this.context = context;
        PropertiesReader.instance = this;
        //creates a new object ‘Properties’
        properties = new Properties();
        try {
            //access to the folder ‘assets’
            AssetManager am = context.getAssets();
            //opening the file
            InputStream inputStream = am.open(PROPERTIES_FILE_NAME);
            //loading of the properties
            properties.load(inputStream);
        } catch (IOException e) {
            Log.w(Constants.LOG_TAG, e.toString());
        }

    }

    public static PropertiesReader getInstance() {
        return instance;
    }

    public String getMyProperty(String key) {
        return properties.getProperty(key);

    }

    public HashMap getPropertyDictionary() {
        HashMap<String, String> result = new HashMap<>();
        result.put(Constants.CLIENT_ID, getMyProperty(Constants.CLIENT_ID));
        result.put(Constants.CLIENT_SECRET, getMyProperty(Constants.CLIENT_SECRET));
        result.put(Constants.GUEST_ID, getMyProperty(Constants.GUEST_ID));
        result.put(Constants.GUEST_SECRET, getMyProperty(Constants.GUEST_SECRET));
        result.put(Constants.REDIRECT_URL, getMyProperty(Constants.CALLBACK_URL));
        result.put(Constants.OAUTH_TOKEN_SERVER_URL, getMyProperty(Constants.OAUTH_TOKEN_SERVER_URL));
        result.put(Constants.OAUTH_TOKEN_LOGOUT_URL, getMyProperty(Constants.OAUTH_TOKEN_LOGOUT_URL));
        result.put(Constants.OAUTH_LOGIN_SERVER_URL, getMyProperty(Constants.OAUTH_LOGIN_SERVER_URL));
        result.put(Constants.OAUTH_CHECK_SERVER_URL, getMyProperty(Constants.OAUTH_CHECK_SERVER_URL));
        result.put(Constants.SCOPES, getMyProperty(Constants.SCOPES));
        result.put(Constants.GRANT_TYPE, getMyProperty(Constants.GRANT_TYPE));
        result.put(Constants.GUEST_GRANT_TYPE, getMyProperty(Constants.GUEST_GRANT_TYPE));
        result.put(Constants.API_SERVER_URL, getMyProperty(Constants.API_SERVER_URL));
        result.put(Constants.UOFA_GENERAL_EMERGENCY_PHONE_NUMBER, getMyProperty(Constants.UOFA_GENERAL_EMERGENCY_PHONE_NUMBER));
        result.put(Constants.UOFA_SECURITY_ISSUE_PHONE_NUMBER, getMyProperty(Constants.UOFA_SECURITY_ISSUE_PHONE_NUMBER));
        result.put(Constants.UOFA_EMERGENCY_TEST_URL, getMyProperty(Constants.UOFA_EMERGENCY_TEST_URL));
        result.put(Constants.UOFA_EMERGENCY_URL, getMyProperty(Constants.UOFA_EMERGENCY_URL));
        return result;
    }
}
