package com.mala.digital_joper_mala.Utils;

import java.io.File;

public class Security_utils {

    public static boolean isDeviceRooted(){

        String[] paths = {
                "/system/app/Superuser.apk", "/system/xbin/su", "/system/bin/su"};

        for (String path : paths){
            if (new File(path).exists()) return true; //Rooted device
        }
        return false; //safe device
    }
}
