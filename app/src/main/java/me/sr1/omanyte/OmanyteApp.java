package me.sr1.omanyte;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author SR1
 */

public class OmanyteApp extends Application {

    public static final String BASE_URL = "http://blah.me/";

    private static OmanyteApp gApp;

    @Override
    public void onCreate() {
        super.onCreate();
        gApp = this;
    }

    public static OmanyteApp getApp() {
        return gApp;
    }

    private static ExecutorService gThreadPool;

    public synchronized static ExecutorService getThreadPool() {
        if (gThreadPool == null) {
            gThreadPool = Executors.newFixedThreadPool(2);
        }
        return gThreadPool;
    }
}
