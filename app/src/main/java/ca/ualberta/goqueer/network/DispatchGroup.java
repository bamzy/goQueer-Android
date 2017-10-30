package ca.ualberta.goqueer.network;

import ca.ualberta.goqueer.server.call_objects.BoolCallback;


/**
 * Created by bamdad on 8/25/16.
 */
public class DispatchGroup {
    BoolCallback completionCallback;
    private boolean canFinish = false;
    private int callCount = 0;
    private boolean boolFlag = false;

    public DispatchGroup(BoolCallback completionCallback) {
        this.completionCallback = completionCallback;
    }

    public void addCall() {
        callCount++;
        checkDone();
    }

    public void doneCall(boolean boolFlag) {
        // Only turns bool flag on to prevent overwriting
        if (boolFlag) {
            this.boolFlag = true;
        }

        doneCall();
    }

    public void doneCall() {
        callCount--;
        checkDone();
    }

    public void setCanFinish(boolean canFinish) {
        this.canFinish = canFinish;

        checkDone();
    }

    private void checkDone() {
        if (canFinish && callCount < 1) {
            completionCallback.onDone(boolFlag);
        }
    }



}
