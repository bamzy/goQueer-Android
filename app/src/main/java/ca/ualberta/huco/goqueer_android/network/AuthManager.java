package ca.ualberta.huco.goqueer_android.network;

import android.content.Context;
import ca.ualberta.huco.goqueer_android.config.PropertiesReader;
import ca.ualberta.huco.goqueer_android.storage.StorageManager;
import ca.ualberta.huco.goqueer_android.server.UODAClient;
import ca.ualberta.huco.goqueer_android.server.UODAConfiguration;
import ca.ualberta.huco.goqueer_android.server.call_objects.BoolCallback;
import ca.ualberta.huco.goqueer_android.server.call_objects.CallDelegate;
import ca.ualberta.huco.goqueer_android.server.call_objects.CallPauseQueue;


/**
 * Created by bamdad on 8/18/16.
 */
public class AuthManager implements CallDelegate {
    @Override
    public void failedCall() {

    }

    @Override
    public void checkNetworkAndTokenExpiry(BoolCallback callback) {

    }

    @Override
    public UODAClient getUODAClient() {
        return null;
    }


//    @Override
//    public UAEmergencyServiceClient getEmergencyClient() {
//        return null;
//    }

    public enum Status {
        User,
        Guest,
        Demo,
        None
    }
    private UODAConfiguration uodaConfiguration;
    private PropertiesReader propertiesReader;
    private StorageManager storageManager;
    private VolleyQueueManager queueManager;
    private Context context;
    private boolean signingOutUser = false;
    private boolean checkingToken = false;

    private CallPauseQueue callPauseQueue = new CallPauseQueue();

    private UODAClient uodaClient = null;
//
//
    public AuthManager(Context context) {
        this.propertiesReader = new PropertiesReader(context);
        this.uodaConfiguration = new UODAConfiguration(propertiesReader.getPropertyDictionary());
        this.storageManager = new StorageManager(context);
        this.queueManager = new VolleyQueueManager(context);
        this.context = context;
    }
//
//    public VolleyQueueManager getQueueManager() {
//        return queueManager;
//    }
//
//    /**
//    * Checks network availability.
//    * @return true if network connected.
//    */
//    public boolean isOnline() {
//        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
//
//        return networkInfo != null && networkInfo.isConnected();
//    }
//
//    /**
//    * Checks stored credentials to determine User Type.
//    * @return Either User, Guest, Demo, or None Authorization Status
//    */
//    public Status getUserType() {
//        UODAToken credentials = storageManager.loadCredentials();
//
//        Status credentialStatus = credentials.storedAuthStatus();
//        if (credentialStatus == Status.Guest) {
//            return storageManager.loadPersonalInfo().getDisplayName().equals(Constants.DEMO_NAV_MENU_VALUE) ? Status.Demo : Status.Guest;
//        } else {
//
//            return credentialStatus;
//        }
//    }
//
//    public String getCcid(){
//        return this.storageManager.loadCredentials().getCcid();
//    }
//
//    public UAEmergencyServiceClient getEmergencyClient() {
//        return new UAEmergencyServiceClient(propertiesReader,queueManager);
//    }
//
//    public PropertiesReader getPropertiesReader() {
//        return propertiesReader;
//    }
//
//    /**
//     * Verifies valid login credentials are stored and returns person details on success
//     */
//    public void checkAlreadyLoggedIn(final UObjectCallback callback) {
//        if (getUserType() == Status.None) {
//            callback.onGotObject(null);
//            return;
//        }
//
//        checkAccessToken(callback);
//    }
//
//    /**
//     * Attempt to get a limited access (Guest) token from UODA using credentials.
//     * @param demoMode if true, on success set personal info to demo user.
//     * @param callback AuthPersonDetails on success, null on failure.
//     */
//    public void loginAsGuest(final boolean demoMode, final UObjectCallback callback) {
//        signingOutUser = false;
//        uodaClient = null;
//
//        UODAAuthClient authClient = new UODAAuthClient(uodaConfiguration, queueManager);
//        authClient.getGuestCredentials(new VolleyMyCoordinatesCallback() {
//
//            @Override
//            public void onSuccess(DataObject dataObject) {
//                UODAToken token = (UODAToken) dataObject;
//                Status tokenStatus = token.storedAuthStatus();
//                if (token != null && tokenStatus == Status.Guest) {
//                    saveCredentials(token, demoMode, callback);
//                } else {
//                    Log.e(Constants.LOG_TAG, "Expected Guest token status.");
//                    callback.onGotObject(null);
//                }
//            }
//
//            @Override
//            public void onError(VolleyError result) {
//                callback.onGotObject(null);
//            }
//        });
//    }
//
//    /**
//     * Clear data and return user to login screen.
//     * @param showError true if an error toast should be displayed at the login screen.
//     */
//    public void signOutUser(boolean showError) {
//        // Prevent multiple signouts from multiple failed calls.
//        if (signingOutUser) {
//            Log.w("Signout", "Already signing out...");
//            return;
//        }
//
//        signingOutUser = true;
//
//
//        logoutToken();
//        storageManager.clear();
//        Singletons.cacheProvider.clearCaches();
//        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();
//        this.uodaClient = null;
//        HappeningsTime.debugDate = null;
//
//        returnToLoginView(showError ? "Login Session Invalid: Please sign in again." : null);
//
//        // This works because LoginActivity is declared as a 'singleTop' in the manifest
//
//    }
//
//    private void returnToLoginView(String errorMessage) {
//        Intent loginIntent = new Intent(context, LoginActivity.class);
//        if (errorMessage != null) {
//            loginIntent.putExtra(LoginActivity.TOAST_TAG, errorMessage);
//        }
//
//        // Will finish all activities on top of login.
//        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        Log.w(Constants.LOG_TAG, "Signed out user...");
//        context.startActivity(loginIntent);
//    }
//
//    //region User Login
//    /**
//     * Creates an intent to open first step login in a WebView
//     * @return WebAuthActivity start intent
//     */
//    public Intent makeLoginIntent() {
//        signingOutUser = false;
//        uodaClient = null;
//
//
//        UODAAuthClient authClient = new UODAAuthClient(uodaConfiguration, queueManager);
//        Intent webViewIntent = new Intent(context, WebAuthActivity.class);
//
//        webViewIntent.putExtra(WebAuthFragment.URL_TAG, authClient.getLoginGetRequest());
//
//        return webViewIntent;
//    }
//
//    /**
//     * Passes the Intent launched by WebAuthFragment on successful SHIB login.
//     * @param intent The intent launched by back from WebAuthFragment
//     * @param callback Will callback a UPerson on success or null on failed UODA Authorization Token fetch.
//     */
//    public void processOAUTHResponse(Intent intent, UObjectCallback callback) {
//        Bundle extras = intent.getExtras();
//        if (extras != null && extras.containsKey(WebAuthFragment.AUTH_CODE_TAG)) {
//            getTokenFromOAUTH(extras.getString(WebAuthFragment.AUTH_CODE_TAG), callback);
//        } else {
//            Log.e(Constants.LOG_TAG, "Failed to find bundled auth code from first response.");
//            callback.onGotObject(null);
//        }
//    }
//
//    /**
//     * Attempt to retrieve an access token from UODA using the OAUTH code received from SHIB.
//     * @param authCode the OAUTH authorization code returned after first step of SHIB authentication
//     */
//    private void getTokenFromOAUTH(final String authCode, final UObjectCallback callback) {
//        UODAAuthClient authClient = new UODAAuthClient(uodaConfiguration, queueManager);
//        authClient.getTokenFromOAUTH(authCode, new VolleyMyCoordinatesCallback() {
//            @Override
//            public void onSuccess(DataObject dataObject) {
//                UODAToken token = (UODAToken) dataObject;
//
//                if (token != null && token.storedAuthStatus() == Status.User) {
//                    saveCredentials(token, callback);
//                } else {
//                    Log.w(Constants.LOG_TAG, "Wrong token format");
//                }
//            }
//
//            @Override
//            public void onError(VolleyError result) {
//                Log.w(Constants.LOG_TAG, result);
//                callback.onGotObject(null);
//            }
//        });
//    }
//    //endregion
//
//    //region Token Utilities
//
//
//    /**
//     * Checks the access token via UODA server, attempts refresh on fail
//     * @param callback calls onGotObject as null on error
//     */
//    private void checkAccessToken(final UObjectCallback callback) {
//        uodaClient = null;
//
//        UODAAuthClient authClient = new UODAAuthClient(uodaConfiguration, queueManager);
//
//        authClient.checkAccessToken(storageManager.loadCredentials().getAccessToken(), new VolleyMyCoordinatesCallback() {
//            @Override
//            public void onSuccess(DataObject result) {
//                UODACheck uodaCheck = (UODACheck) result;
//                AuthPersonDetails personDetails = storageManager.loadPersonalInfo();
//                if (uodaCheck.isValid() && storageManager.personalInfoValid(personDetails)) {
//                    callback.onGotObject(personDetails);
//                } else {
//                    Log.w(Constants.LOG_TAG, "Token Invalid, attempting refresh.");
//                    refreshAccessToken(callback);
//                }
//            }
//
//            @Override
//            public void onError(VolleyError result) {
//                Log.w(Constants.LOG_TAG, "Token check indeterminate: " + result);
//                AuthPersonDetails personInfo = storageManager.loadPersonalInfo();
//                if (storageManager.personalInfoValid(personInfo)) {
//                    callback.onGotObject(personInfo);
//                } else {
//                    callback.onGotObject(null);
//                }
//
//            }
//        });
//    }
//
//    /**
//     * Attempt to get a new access token from UODA using a refresh token.
//     * @param callback true on success.
//     */
//    private void refreshAccessToken(final BoolCallback callback) {
//        refreshAccessToken(new UObjectCallback() {
//            @Override
//            public void onGotObject(DataObject uObject) {
//                callback.onDone(uObject != null);
//            }
//        });
//    }
//
//    /**
//     * Attempt to get a new access token from UODA using a refresh token.
//     * @param callback Return UPerson containing personal info on success.
//     */
//    private void refreshAccessToken(final UObjectCallback callback) {
//        String refreshToken = storageManager.loadCredentials().getRefreshToken();
//
//        if (refreshToken == null) {
//            callback.onGotObject(null);
//            signOutUser(true);
//            return;
//        }
//
//        UODAAuthClient authClient = new UODAAuthClient(uodaConfiguration, queueManager);
//
//        authClient.refreshAccessToken(refreshToken, new VolleyMyCoordinatesCallback() {
//            @Override
//            public void onSuccess(DataObject result) {
//                UODAToken uodaToken = (UODAToken) result;
//                Log.w(Constants.LOG_TAG, "Successfully refreshed token.");
//                saveCredentials(uodaToken, callback);
//            }
//
//            @Override
//            public void onError(VolleyError result) {
//                signOutUser(true);
//                callback.onGotObject(null);
//            }
//        });
//    }
//    /**
//     * Invalidate UODA access token.
//     */
//    private void logoutToken() {
//        UODAAuthClient authClient = new UODAAuthClient(uodaConfiguration, queueManager);
//
//        authClient.logoutAuthorizationToken(new VolleyMyCoordinatesCallback() {
//            @Override
//            public void onSuccess(DataObject dataObject) {
//                Log.i(Constants.LOG_TAG, "Successfully logged out token.");
//            }
//
//            @Override
//            public void onError(VolleyError result) {
//                Log.e(Constants.LOG_TAG, "Failed to logout token.");
//                result.printStackTrace();
//            }
//        });
//    }
//
//    //endregion
//
//    //region Credential Management
//    /**
//     * Attempts to fetch personal info based on personID in token and saves to storageManager.
//     *
//     * @param token a UODAToken that has been null checked.
//     * @param callback Return route for UPerson (personal info) on successful get.
//     */
//    private void saveCredentials(final UODAToken token, final UObjectCallback callback) {
//        this.uodaClient = null;
//
//        saveCredentials(token, false, callback);
//    }
//
//    /**
//     * Attempts to fetch personal info based on personID in token and saves to storageManager.
//     * If local personal info is invalid, fetch from UODA.
//     * @param token a UODAToken that has been null checked.
//     * @param callback Return route for UPerson (personal info) on successful get.
//     * @param demoMode true if personal info should be demo user.
//     */
//    private void saveCredentials(final UODAToken token, final boolean demoMode, final UObjectCallback callback) {
//        storageManager.saveCredentials(token);
//        if(token.storedAuthStatus() == Status.Guest) {
//            if (demoMode) {
//                storageManager.saveDemoPersonal();
//            } else {
//                storageManager.saveGuestPersonal();
//            }
//
//            callback.onGotObject(storageManager.loadPersonalInfo());
//            return;
//        }
//
//        fetchPersonalInfo(token.getPersonId(), callback);
//    }
//
//    /**
//     * Fetch information (name, etc) from personId and save to storage manager on success.
//     * @param personId CCID of person.
//     * @param callback AuthPersonDetails on success, null on fail.
//     */
//    private void fetchPersonalInfo(String personId, final UObjectCallback callback) {
//        AuthPersonDetails personDetails = storageManager.loadPersonalInfo();
//
//        if (storageManager.personalInfoValid(personDetails)) {
//            callback.onGotObject(personDetails);
//        } else {
//            DataProvider.Person.getDetails(personId, new UObjectCallback() {
//                @Override
//                public void onGotObject(DataObject uObject) {
//                    if (uObject != null) {
//                        AuthPersonDetails newPersonDetails = new AuthPersonDetails((UPerson) uObject);
//                        storageManager.savePersonalInfo(newPersonDetails);
//                        callback.onGotObject(newPersonDetails);
//                    } else {
//                        callback.onGotObject(null);
//                        Log.e(Constants.LOG_TAG, "Failed to get uPerson");
//                    }
//                }
//            });
//        }
//
//    }
//    //endregion
//
//    //region CallDelegate
//    /**
//     * Sent via CallDelegate.  Check token status --(fail)-> refresh token --(fail)-> remove user
//     */
//    @Override
//    public void failedCall() {
//        Log.w("AuthManager", "Failed call received.");
//
//        if (checkingToken) {
//            Log.w("AuthManager", "Already checking token... bypassing.");
//            return;
//        }
//
//        checkingToken = true;
//        checkAccessToken(new UObjectCallback() {
//            @Override
//            public void onGotObject(DataObject uObject) {
//                checkingToken = false;
//            }
//        });
//    }
//
//    /**
//     * Called via CallDelegate.  Checks for expiry of token and network connectivity.
//     * If token expired, the first call starts the refresh process and initializes callPauseQueue to capture future calls.
//     * On successful refresh, callPauseQueue any calls that have queued.
//     * @param callback true if token valid.
//     */
//    @Override
//    public void checkNetworkAndTokenExpiry(final BoolCallback callback) {
//        if (!isOnline()) {
//            callback.onDone(false);
//            return;
//        }
//
//        if (callPauseQueue.isActive()) {
//            callPauseQueue.add(callback);
//            return;
//        }
//
//
//        if (!storageManager.authTokenExpired()) {
//            callback.onDone(true);
//            return;
//        }
//
//        callPauseQueue.start(context, callback);
//
//        refreshAccessToken(new BoolCallback() {
//            @Override
//            public void onDone(boolean boolFlag) {
//                Log.w(Constants.LOG_TAG, "Executing CallPauseQueue");
//
//                callPauseQueue.execute(boolFlag);
//            }
//        });
//    }
//
//    /**
//     * Called via CallDelegate.  Client instance should be nulled on token change to update client config when it is instantiated again.
//     * @return Last created UODAClient instance or instantiates new one.
//     */
//    @Override
//    public UODAClient getUODAClient() {
//        if (uodaClient == null) {
//            Log.w(Constants.LOG_TAG,"New Client with token " + storageManager.loadCredentials().getAccessToken());
//            this.uodaClient = new UODAClient(uodaConfiguration, queueManager, storageManager.loadCredentials().getAccessToken());
//        }
//
//        return uodaClient;
//    }
//    //endregion
}
