package com.example.wifisignin;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class WifiHelper {

    public static void signIn( Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        String ssidStr = sharedPreferences.getString("ssid","");
        String passStr = sharedPreferences.getString("pass","");
        //Toast.makeText(context,ssidStr,Toast.LENGTH_LONG).show();
        //Toast.makeText(context,passStr,Toast.LENGTH_LONG).show();
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + ssidStr + "\"";   // Please note the quotes. String should contain ssid in quotes
        conf.preSharedKey = "\""+ passStr +"\"";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()) {
            Log.d("Wifi State: ","Disable");
            wifiManager.setWifiEnabled(true);
        }

        wifiManager.addNetwork(conf);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + ssidStr + "\"")) {
                wifiManager.disconnect();
                //Log.d("QWERT",i.networkId+"");
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();


                break;
            }
        }
    }

    public static void disConnect( Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        String ssidStr = sharedPreferences.getString("ssid","");
        String passStr = sharedPreferences.getString("pass","");
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + ssidStr + "\"";   // Please note the quotes. String should contain ssid in quotes
        conf.preSharedKey = "\""+ passStr +"\"";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        /*if(!wifiManager.isWifiEnabled()) {
            Log.d("Wifi State: ","Disable");
            wifiManager.setWifiEnabled(true);
        }*/

        wifiManager.addNetwork(conf);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + ssidStr + "\"")) {
                wifiManager.disconnect();
                //Log.d("QWERT",i.networkId+"");
                //wifiManager.enableNetwork(i.networkId, true);
                //wifiManager.reconnect();
                wifiManager.setWifiEnabled(false);

                break;
            }
        }
    }
}
