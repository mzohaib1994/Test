package com.adaxiom.test;

import android.Manifest;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends RunTimePermissions {

    String networkType="";
    public static final int REQUEST_PERMISSION = 10;


    private WebView wv1;
    Button b1;
    EditText ed1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestAppPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_WIFI_STATE
                },
                R.string.msg,REQUEST_PERMISSION);

//        getNetworkType();
//        getImei();
//        getSimIMSI();
//        getApplicationVersionCode();
//        getApplicationVersionName();
//        getCellSiteId();
//        getSerialNumber();
//        getOSVersion();
        b1=(Button)findViewById(R.id.button);
        ed1=(EditText)findViewById(R.id.editText);

        wv1=(WebView)findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());

        String abc = "http://192.168.0.113/nestle/login.php";


        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(abc);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ed1.getText().toString();

//                String abc = "http://192.168.0.113/nestle/login.php";


                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);
            }
        });





    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void onPermissionsCancelled(int requestCode) {

    }

    public void getNetworkType()
    {
        Toast.makeText(this, "Network Type "+CommonUtils.getNetworkType(this), Toast.LENGTH_SHORT).show();
    }

    public void getImei()
    {
        Toast.makeText(this, "Imei is "+CommonUtils.getIMEI(this), Toast.LENGTH_SHORT).show();
    }

    public void getSimIMSI()
    {
        Toast.makeText(this, "Sim IMSI is :"+CommonUtils.getSimIMSI(this), Toast.LENGTH_SHORT).show();
    }

    private void getApplicationVersionCode()
    {
        Toast.makeText(this, "Application version code is "+CommonUtils.getApplicationVersionCode(this), Toast.LENGTH_SHORT).show();
    }

    private void getApplicationVersionName()
    {
        Toast.makeText(this, "Application version name is "+CommonUtils.getApplicationVersionName(this), Toast.LENGTH_SHORT).show();
    }

    private void getCellSiteId()
    {
        Toast.makeText(this, "Cell site Id is "+CommonUtils.getCellSiteId(this), Toast.LENGTH_SHORT).show();
    }

    private void getSerialNumber()
    {
        Toast.makeText(this, "Serial Number is "+CommonUtils.getSerialNumber(this), Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getOSVersion()
    {
//        Toast.makeText(this, "Network status  "+getSignalStrength(this), Toast.LENGTH_SHORT).show();

        Log.e("Imei is ",CommonUtils.getIMEI(this));
        Log.e("Network Type ",CommonUtils.getNetworkType(this));
        Log.e("Sim IMSI is :",CommonUtils.getSimIMSI(this));
        Log.e("Application version ", String.valueOf(CommonUtils.getApplicationVersionCode(this)));
        Log.e("Cell site Id is ",CommonUtils.getCellSiteId(this));
        Log.e("Serial Number is ",CommonUtils.getSerialNumber(this));
        Log.e("Serial Number is ",CommonUtils.getSerialNumber(this));
        Log.e("Os version is ",CommonUtils.getOSVersion(this));
        Log.e("Device up time ",CommonUtils.getDeviceUpTime(this));
        Log.e("current time is ",CommonUtils.getCurrentTime(this));

        String checkConnnectionType = CommonUtils.checkConnnectionType(this);
        Toast.makeText(this, "Signal Strneth "+CommonUtils.CheckSignalStrength(this,checkConnnectionType), Toast.LENGTH_SHORT).show();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}

