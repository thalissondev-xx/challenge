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
        Date createdAt = new Date(Double.valueOf(unixTime).longValue() * 1000);
        long elapsed = System.currentTimeMillis() - createdAt.getTime();

        long diffSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsed);
        long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(elapsed);
        long diffHours = TimeUnit.MILLISECONDS.toHours(elapsed);
        long diffDays = TimeUnit.MILLISECONDS.toDays(elapsed);
        long diffWeeks = diffDays / 7;

        if (diffWeeks > 0) {
            return diffWeeks + " weeks";
        }
        else if (diffDays > 0) {
            return diffDays + " days";
        }
        else if (diffHours > 0) {
            return diffHours + " hours";
        }
        else if (diffMinutes > 0) {
            return diffMinutes + " minutes";
        }
        else {
            return diffSeconds + " seconds";
        }
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
