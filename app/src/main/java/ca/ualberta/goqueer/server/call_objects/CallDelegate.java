package ca.ualberta.goqueer.server.call_objects;


import ca.ualberta.goqueer.server.UODAClient;

/**
 * Created by bamdad on 8/18/16.
 */
public interface CallDelegate {
    public void failedCall();
    public void checkNetworkAndTokenExpiry(BoolCallback callback);
    public UODAClient getUODAClient();
//    public UAEmergencyServiceClient getEmergencyClient();
}
