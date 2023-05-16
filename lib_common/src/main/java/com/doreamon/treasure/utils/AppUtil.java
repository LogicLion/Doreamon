package com.doreamon.treasure.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

import com.doreamon.treasure.base.App;

public class AppUtil {


    public static String getVersionCode() {
        PackageInfo info = null;
        try {
            info = App.getInstance().getPackageManager().getPackageInfo(App.getInstance().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info != null ? info.versionName : "";
    }


    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
