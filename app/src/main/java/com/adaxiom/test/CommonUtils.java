package com.adaxiom.test;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Hanan Nasir on 15,January,2019.
 * Adsells Publicity (Pvt.) Ltd.
 * hanan.nasir@adsells.biz
 */
public enum CommonUtils {


    INSTANCE;
    private static final int EXCELLENT_LEVEL = 75;
    private static final int GOOD_LEVEL = 50;
    private static final int MODERATE_LEVEL = 25;
    private static final int WEAK_LEVEL = 0;


    /**
     * To get device consuming netowkr type is 2g,3g,4g
     *
     * @param context
     * @return "2g","3g","4g" as a String based on the network type
     */
    public static String getNetworkType(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2g";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                /**
                 From this link https://en.wikipedia.org/wiki/Evolution-Data_Optimized ..NETWORK_TYPE_EVDO_0 & NETWORK_TYPE_EVDO_A
                 EV-DO is an evolution of the CDMA2000 (IS-2000) standard that supports high data rates.

                 Where CDMA2000 https://en.wikipedia.org/wiki/CDMA2000 .CDMA2000 is a family of 3G[1] mobile technology standards for sending voice,
                 data, and signaling data between mobile phones and cell sites.
                 */
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                //Log.d("Type", "3g");
                //For 3g HSDPA , HSPAP(HSPA+) are main  networktype which are under 3g Network
                //But from other constants also it will 3g like HSPA,HSDPA etc which are in 3g case.
                //Some cases are added after  testing(real) in device with 3g enable data
                //and speed also matters to decide 3g network type
                //https://en.wikipedia.org/wiki/4G#Data_rate_comparison
                return "3g";
            case TelephonyManager.NETWORK_TYPE_LTE:
                //No specification for the 4g but from wiki
                //I found(LTE (Long-Term Evolution, commonly marketed as 4G LTE))
                //https://en.wikipedia.org/wiki/LTE_(telecommunication)
                return "4g";
            default:
                return "Notfound";
        }
    }

    /**
     * To check device has internet
     *
     * @param context
     * @return boolean as per status
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }


    public static String getIMEI(Context context) {
        String IMEI = "";
        try {
            IMEI = ((TelephonyManager) context.getSystemService("phone")).getDeviceId().toString().trim();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }

        return IMEI;
    }

    public static String getSimIMSI(Context context) {
        String SimIMSI = "";
        try {
            SimIMSI = ((TelephonyManager) context.getSystemService("phone"))
                    .getSimSerialNumber().toString().trim();

        } catch (Exception e) {
            SimIMSI = "";
        }

        return SimIMSI;
    }


    public static int getApplicationVersionCode(Context context) {
        int version = 0;
        // PackageManager manager = context.getPackageManager();
        // PackageInfo info;
        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
            // version = info.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            version = 1;
        }

        return version;

    }

    public static String getApplicationVersionName(Context context) {

        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName.toString().trim();
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "";
        }

        return versionName;

    }

    public static String getCellSiteId(Context context) {
        int cid = 0;
        try {

            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(TELEPHONY_SERVICE);
            GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();

            cid = cellLocation.getCid();
            // int lac = cellLocation.getLac();
            cellLocation.toString();
        } catch (Exception e) {
            cid = 0;
        }
        return Integer.toString(cid).trim();
    }

    public static String getSerialNumber(Context context) {
        String serial = "";
        try {
            serial = Build.SERIAL;
        } catch (Exception e) {
            serial = "";
        }
        return serial;
    }


    public static String getOSVersion(Context context) {
        String androidOS = "";
        try {
            androidOS = Build.VERSION.RELEASE;
        } catch (Exception e) {
            androidOS = "";
        }
        return androidOS;
    }

    public static String getDeviceUpTime(Context context) {
        long deviceUpTime = 0;
        try {
//            androidOS = System.currentTimeMillis();
            deviceUpTime = android.os.SystemClock.elapsedRealtime();

        } catch (Exception e) {
            deviceUpTime = 0;
        }
        return Float.toString(deviceUpTime).trim();
    }

    public static String getCurrentTime(Context context) {
        String formattedDate = "";
        try {

            Date date = new Date();
            String strDateFormat = "dd-MM-yyyy HH:mm:ss a";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            formattedDate = dateFormat.format(date);


        } catch (Exception e) {
            e.printStackTrace();
            formattedDate = "";
        }


        return formattedDate;
    }


    public static String checkConnnectionType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

//For 3G check
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();

        if (is3g) {
            String conection = getNetworkStatus(context);
            return conection;
        }
//For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();
        if (isWifi) {
            return "Wifi";
        }
        return "Unknwon";
    }


    public static String getNetworkStatus(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            default:
                return "Unknown";
        }
    }

    public static String CheckSignalStrength(Context context, String networkType) {
        final String[] linkSpeed = {"0"};

        if (networkType.equalsIgnoreCase("Wifi")) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            linkSpeed[0] = String.valueOf(wifiManager.getConnectionInfo().getRssi());
        }
            else {
            final TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
            telephonyManager.listen(new PhoneStateListener() {
                @Override
                public void onSignalStrengthsChanged(SignalStrength strength) {
                    super.onSignalStrengthsChanged(strength);
                    if (strength.isGsm()) {
                        String[] parts = strength.toString().split(" ");
//                        String signalStrength = "";
                        int currentStrength = strength.getGsmSignalStrength();
                        if (currentStrength <= 0) {
                            if (currentStrength == 0) {
                                linkSpeed[0] = String.valueOf(Integer.parseInt(parts[3]));
                            } else {
                                linkSpeed[0] = String.valueOf(Integer.parseInt(parts[1]));
                            }
                            linkSpeed[0] += " dBm";
                        } else {
                            if (currentStrength != 99) {
                                linkSpeed[0] = String.valueOf(((2 * currentStrength) - 113));
                                linkSpeed[0] += " dBm";
                            }
                        }
//signal = (2 * signal) - 113;
                        System.out.println("Signal strength is : " + linkSpeed[0]);

                    } else {


                        linkSpeed[0] = "Not a gsm signal.";
                    }
                }
            }, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        }
        return linkSpeed[0];
    }




}

