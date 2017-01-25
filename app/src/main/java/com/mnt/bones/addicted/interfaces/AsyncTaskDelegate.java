package com.mnt.bones.addicted.interfaces;

/**
 *  An interface to handle AsyncTask
 */

public interface AsyncTaskDelegate {
    void onPreStart();
    void onFinish(Object output);
}
