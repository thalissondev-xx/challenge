package br.com.challenge.utils;

import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.text.Spanned;
import android.view.Display;
import android.view.WindowManager;

import java.util.Calendar;
import java.util.Date;

import br.com.challenge.App;

/**
 * Created by thalissonestrela on 4/17/17.
 */

public class Global {

    public static int getDeviceWidth() {
        WindowManager wm = (WindowManager) App.getInstance().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }

    public static String timeDiff(long unixTime) {
        Date date = new Date(unixTime * 1000);
        Date currentDate = Calendar.getInstance().getTime();

        long timeDifferenceMilliseconds = (currentDate.getTime() - date.getTime());

        long diffSeconds = timeDifferenceMilliseconds / 1000;
        long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
        long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
        long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
        long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
        long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
        long diffYears = timeDifferenceMilliseconds / ((long)60 * 60 * 1000 * 24 * 365);

        if (diffSeconds < 1) return "less than a second";
        else if (diffMinutes < 1) return diffSeconds + " seconds ago";
        else if (diffHours < 1) return diffMinutes + " minutes ago";
        else if (diffDays < 1) return diffHours + " hours ago";
        else if (diffWeeks < 1) return diffDays + " days ago";
        else if (diffMonths < 1) return diffWeeks + " weeks ago";
        else if (diffYears < 1) return diffMonths + " months ago";
        else return diffYears + " years ago";
    }

    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
