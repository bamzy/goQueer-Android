package ca.ualberta.huco.goqueer_android.uoda.call_objects;


import ca.ualberta.huco.goqueer_android.uoda.UODAClient;

/**
 * Created by bamdad on 8/18/16.
 */
public interface CallDelegate {
    public void failedCall();
    public void checkNetworkAndTokenExpiry(BoolCallback callback);
    public UODAClient getUODAClient();
//    public UAEmergencyServiceClient getEmergencyClient();
}
