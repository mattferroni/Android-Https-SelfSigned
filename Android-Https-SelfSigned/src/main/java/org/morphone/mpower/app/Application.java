package org.morphone.mpower.app;

/**
 * Incapsulate the Application configuration
 * Created by Andrea on 13/05/2014.
 */
public class Application extends android.app.Application {
    private static Application instance = null;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
