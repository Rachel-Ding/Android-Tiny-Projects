package com.dingding.jokes.control;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*判断是否联网*/
public class NetworkUtils {
    public static boolean isNetworkConnected(Context context){
        boolean bool =false;
        if(context !=null){
            NetworkInfo localNetworkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if(localNetworkInfo!=null){
                bool = localNetworkInfo.isAvailable();
            }
        }
        return bool;
    }

    public static boolean isWifi(Context context){
        boolean bool =false;
            NetworkInfo localNetworkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if(localNetworkInfo!=null && (localNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)){
                bool = true;
            }
        return bool;
    }
}
