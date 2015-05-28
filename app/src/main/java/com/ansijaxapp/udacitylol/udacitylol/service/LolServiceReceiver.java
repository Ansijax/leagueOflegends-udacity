package com.ansijaxapp.udacitylol.udacitylol.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Massimo on 24/05/15.
 */
public class LolServiceReceiver extends ResultReceiver {
    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public static final int START_SERVICE=0;
    public static final int END_SERVICE=1;
    public static final int NO_MATCH=2;
    public static final int ERROR=3;

    private Receiver mReceiver;
    public LolServiceReceiver(Handler handler) {
        super(handler);
    }
    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);

    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
