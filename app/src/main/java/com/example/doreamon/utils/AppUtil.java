package com.example.doreamon.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;


import com.example.doreamon.base.AppI;

import java.security.MessageDigest;

public class AppUtil {

    public static String MD5Encode32(String str) {
        String md5Str = null;
        try {
            StringBuffer buf = new StringBuffer();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            // 32位的加密
            md5Str = buf.toString();
            // 16位的加密
//            md5Str = buf.toString().substring(8, 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }

    public static String getVersionCode() {
        PackageInfo info = null;
        try {
            info = AppI.getInstance().getPackageManager().getPackageInfo(AppI.getInstance().getPackageName(), 0);
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
