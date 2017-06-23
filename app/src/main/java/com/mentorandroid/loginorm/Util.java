package com.mentorandroid.loginorm;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by brunodelhferreira on 22/06/17.
 */

public class Util {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conectivtyManager.getActiveNetworkInfo() != null && conectivtyManager.getActiveNetworkInfo().isAvailable();
    }
}
