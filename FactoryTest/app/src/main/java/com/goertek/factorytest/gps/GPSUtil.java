package com.goertek.factorytest.gps;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import com.mediatek.utils.Globals;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;

import com.goertek.factorytest.utils.Globals;

public class GPSUtil {
	private Context mContext;
	private int mGpsCurrentStatus;
	private Listener mGpsStatusListener;
	private LocationListener mLocationListener;
	private LocationManager mLocationManager;
	private String mProvider;
	private int mSatelliteNum;

	private List mSatelliteSignal;

	class GpsUtil2 implements Listener {
		public void onGpsStatusChanged(int paramInt) {
			GpsStatus localGpsStatus = mLocationManager.getGpsStatus(null);
			// GPSUtil.access$100(this.this$0, paramInt, localGpsStatus);
			updateGpsStatus(paramInt, localGpsStatus);
		}
	}

	class GpsUtil1 implements LocationListener {
		public void onLocationChanged(Location paramLocation) {
		}

		public void onProviderDisabled(String paramString) {
		}

		public void onProviderEnabled(String paramString) {
		}

		public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) {
		}
	}

	public GPSUtil(Context paramContext) {
		ArrayList localArrayList = new ArrayList();
		this.mSatelliteSignal = localArrayList;
		GpsUtil1 local1 = new GpsUtil1();
		this.mLocationListener = local1;
		GpsUtil2 local2 = new GpsUtil2();
		this.mGpsStatusListener = local2;
		this.mContext = paramContext;
		LocationManager localLocationManager1 = (LocationManager) this.mContext.getSystemService("location");
		this.mLocationManager = localLocationManager1;
		this.mProvider = "gps";
		openGPS();
		LocationManager localLocationManager2 = this.mLocationManager;
		String str1 = this.mProvider;
		localLocationManager2.getLastKnownLocation(str1);
		LocationManager localLocationManager3 = this.mLocationManager;
		String str2 = this.mProvider;
		LocationListener localLocationListener = this.mLocationListener;
		localLocationManager3.requestLocationUpdates(str2, 2000L, 0, localLocationListener);
		LocationManager localLocationManager4 = this.mLocationManager;
		Listener localListener = this.mGpsStatusListener;
		localLocationManager4.addGpsStatusListener(localListener);
		updateGpsStatus(0, null);
	}

	private void openGPS() {
		LocationManager localLocationManager = this.mLocationManager;
		String str = this.mProvider;
		if (localLocationManager.isProviderEnabled(str))
			return;
		openGPSSetting();
	}

	private void openGPSSetting() {
		ContentResolver localContentResolver = this.mContext.getContentResolver();
		String str = this.mProvider;
		// ���GPSû��Ҫ�ȴ�
//		Settings.Secure.setLocationProviderEnabled(localContentResolver, str, true);
		if (!isGpsEnable()) {
//			openGSP();
			Globals.gpsHandler.sendEmptyMessage(0);   // 跳转到设置GPS的界面
		}
	}

	private void updateGpsStatus(int paramInt, GpsStatus paramGpsStatus) {
		if (paramGpsStatus == null) {
			this.mSatelliteNum = 0;
			return;
		}

		if (paramInt == 4)
			this.mGpsCurrentStatus = paramInt;
		else
			return;

		Iterator<GpsSatellite> iterator = paramGpsStatus.getSatellites().iterator();
		float f = 0;
		while (true) {
			if (iterator.hasNext())
				f = ((GpsSatellite) iterator.next()).getSnr();
			if (f < 30)
				return;
			List localList = this.mSatelliteSignal;
			Float localFloat = Float.valueOf(f);
			localList.add(localFloat);
			this.mSatelliteNum++;

			return;
		}
	}

	public void closeLocation() {
		LocationManager localLocationManager1 = this.mLocationManager;
		Listener localListener = this.mGpsStatusListener;
		localLocationManager1.removeGpsStatusListener(localListener);
		LocationManager localLocationManager2 = this.mLocationManager;
		LocationListener localLocationListener = this.mLocationListener;
		localLocationManager2.removeUpdates(localLocationListener);
	}

	public int getGpsCurrentStatus() {
		return this.mGpsCurrentStatus;
	}

	public LocationManager getLocationManager() {
		return this.mLocationManager;
	}

	public int getSatelliteNumber() {
		return this.mSatelliteNum;
	}

	public String getSatelliteSignals() {
		int i = 1;
		int k = 0;
		String str3 = "";
		String str1 = this.mSatelliteSignal.toString();
		int j = this.mSatelliteSignal.size();
		k = str1.length() - i;
		// String str2 = "";
		if ((j > 0) && (this.mSatelliteNum > 0)) {
			str3 = str1.substring(i, k);
			return str3;
		} else
			return str3;

		/*
		 * for (; ; str3 = str1.substring(i, k)) { return str2; k =
		 * str1.length() - i; }
		 */
	}
	
	private void openGSP(){
		Intent GPSIntent = new Intent(); 
        GPSIntent.setClassName("com.android.settings", 
                "com.android.settings.widget.SettingsAppWidgetProvider"); 
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE"); 
        GPSIntent.setData(Uri.parse("custom:3")); 
        try { 
            PendingIntent.getBroadcast(mContext, 0, GPSIntent, 0).send(); 
        } catch (CanceledException e) { 
            e.printStackTrace(); 
        } 
	}
	
	public boolean isGPSEnable() {
        String str = Secure.getString(mContext.getContentResolver(),
                Secure.LOCATION_PROVIDERS_ALLOWED);
        Log.v("GPS", str);
        if (str != null) {
            return str.contains("gps");
        }
        else{
            return false;
        }
    }
	
	public boolean isGpsEnable() {  
        LocationManager locationManager =   
                ((LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE));  
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);  
    }  
}