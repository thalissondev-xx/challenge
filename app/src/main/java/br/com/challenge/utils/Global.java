package br.com.challenge.utils;

import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.WindowManager;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.com.challenge.App;

/**
 * Created by thalissonestrela on 4/17/17.
 */

public class Global {

    public int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }

    public String timeDiff(long unixTime) {

        // Set entry date in format like the official reddit client
        long elapsed = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - unixTime;
        if (TimeUnit.SECONDS.toMinutes(elapsed) < 2) {
            return "now";
        } else if (TimeUnit.SECONDS.toDays(elapsed) >= 1) {
            return TimeUnit.SECONDS.toDays(elapsed) + "d";
        } else if (TimeUnit.SECONDS.toHours(elapsed) >= 1) {
            return TimeUnit.SECONDS.toHours(elapsed) + "h";
        } else if (TimeUnit.SECONDS.toMinutes(elapsed) >= 1) {
            return TimeUnit.SECONDS.toMinutes(elapsed) + "m";
        } else {
            return "now";
        }
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
