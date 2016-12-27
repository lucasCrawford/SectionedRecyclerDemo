package com.thorrism.sectionedrecyclerdemo.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;

/**
 * Simple utility class to facilitate checking for proper permissions.
 *
 * Created by lcrawford on 7/3/16.
 */
public class PermissionUtils {

    /**
     * Check if a particular permission is enabled.
     *
     * @param activity   - Activity that requires the particular permission
     * @param permission - Value of the permission in question
     * @return true if the device version below API 23 or it was enabled
     */
    public static boolean checkPermission(Activity activity, String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void updateAskedPermission(Context context, String permission, boolean status) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit()
                .putBoolean(permission, status)
                .apply();
    }

    public static boolean hasAskedPermission(Context context, String permission) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(permission, false);
    }

}
