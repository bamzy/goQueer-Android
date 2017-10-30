package ca.ualberta.goqueer.server.call_objects;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

import ca.ualberta.goqueer.config.Constants;


/**
 * Created by bamdad on 9/21/16.
 */

public class CallPauseQueue {
    private ArrayList<BoolCallback> callbacks = new ArrayList<>();
    private Handler handler;
    private boolean active = false;

    public CallPauseQueue() {
    }

    public void start(Context context, BoolCallback callback) {
        this.active = true;

        handler = new Handler(context.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(Constants.LOG_TAG, "CallPauseQueue timed out.");
                execute(false);
            }
        }, Constants.SERVER_TIMEOUT_MILLIS);

        callbacks.add(callback);
    }

    public void add(BoolCallback callback) {
        callbacks.add(callback);
    }

    public void execute(boolean boolFlag) {
        for (BoolCallback callback: callbacks) {
            callback.onDone(boolFlag);
        }

        handler.removeCallbacksAndMessages(null);
        callbacks.clear();
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }
}


