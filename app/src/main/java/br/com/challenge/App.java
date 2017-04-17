package br.com.challenge;

import android.app.Application;
import android.content.Context;

/**
 * Created by thalissonestrela on 4/14/17.
 */

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getInstance() {
        return instance;
    }

}
