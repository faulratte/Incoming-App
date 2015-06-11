package fhws.marcelgross.incoming.Adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;


/**
 * Created by Marcel on 08.06.2015.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    public boolean isConnected;

    private static NetworkChangeReceiver instance;
    public static NetworkChangeReceiver getInstance(){
        return instance == null ? instance = new NetworkChangeReceiver() : instance;
    }

    private NetworkChangeReceiver(){

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        isNetworkAvailable(context);
//        connection = isConnected;
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        if (!isConnected) {
                            Toast.makeText(context, "Internet availablle", Toast.LENGTH_SHORT).show();
                            isConnected = true;
                        }
                        return true;
                    }
                }
            }
        }
        Toast.makeText(context, "Internet NOT availablle", Toast.LENGTH_SHORT).show();
        isConnected = false;
        return false;
    }

    /*
    @Override
    public void onReceive(Context context, Intent intent) {
        final int state = intent.getIntExtra(WifiManager.WIFI_STATE_CHANGED_ACTION, WifiManager.WIFI_STATE_UNKNOWN);
        final int umtsState = intent.getIntExtra(ConnectivityManager.CONNECTIVITY_ACTION, ConnectivityManager.TYPE_DUMMY);

        switch (state){
            case WifiManager.WIFI_STATE_DISABLED:
                break;
            case WifiManager.WIFI_STATE_DISABLING:
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                break;
            case WifiManager.WIFI_STATE_ENABLING:
                break;
            case WifiManager.WIFI_STATE_UNKNOWN:
                break;
        }
    }*/
}
