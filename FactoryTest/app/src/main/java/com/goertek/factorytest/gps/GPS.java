package com.goertek.factorytest.gps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;
import com.goertek.factorytest.utils.Globals;

import android.os.Message;
import android.provider.Settings;
import android.provider.Settings.Global;

public class GPS extends Activity implements OnClickListener {
	private Button mBtFailed;
	private Button mBtOk;
	private GPSUtil mGpsUtil;
	private Handler mHandler;
	private TextView mResultView;
	private TextView mSatelliteNumView;
	private TextView mSignalView;
	private SharedPreferences mSp;
	private TextView mStView;
	private Chronometer mTimeView;

	class GPS1 extends Handler {
		public void handleMessage(Message paramMessage) {
			switch (paramMessage.what) {
			default:
			case 0:
			}
			getSatelliteInfo();

		}
	}
	
	public GPS() {
		GPS1 local1 = new GPS1();
		this.mHandler = local1;
	}

	public void getSatelliteInfo() {
		int i = 0;
		int j = this.mGpsUtil.getSatelliteNumber();
		if (j > 4) {
			this.mHandler.removeMessages(i);
			this.mTimeView.stop();
			this.mResultView.setText(R.string.GPS_Success);
		} else {
			TextView localTextView1 = this.mSatelliteNumView;
			StringBuilder localStringBuilder1 = new StringBuilder();
			String str1 = getString(R.string.GPS_satelliteNum);
			String str2 = str1 + j;
			localTextView1.setText(str2);
			TextView localTextView2 = this.mSignalView;
			StringBuilder localStringBuilder2 = new StringBuilder();
			String str3 = getString(R.string.GPS_Signal);
			StringBuilder localStringBuilder3 = localStringBuilder2.append(str3);
			String str4 = this.mGpsUtil.getSatelliteSignals();
			String str5 = str3 + str4;
			localTextView2.setText(str5);
			// return;
			this.mHandler.sendEmptyMessageDelayed(i, 2000L);
		}
	}

	public void onClick(View paramView) {
		SharedPreferences localSharedPreferences = this.mSp;
		int i = R.string.gps_name;
		int j = paramView.getId();
		int k = this.mBtOk.getId();
		int kk = this.mBtFailed.getId();
		if (j == k) {
			Utils.SetPreferences(this, localSharedPreferences, i, "success");
			finish();

		}
		if (j == kk) {
			Utils.SetPreferences(this, localSharedPreferences, i, "failed");
			finish();

		}

	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.gps);
		
		TextView localTextView1 = (TextView) findViewById(R.id.gps_state_id);
		this.mStView = localTextView1;
		
		Globals.gpsHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				
				new AlertDialog.Builder(GPS.this).setTitle("提示")
					.setMessage("GPS测试需要打开GPS功能")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivityForResult(intent, 0); 
							
							if(GPS.this.mGpsUtil.isGpsEnable()){
								GPS.this.mStView.setText(R.string.GPS_connect);
								GPS.this.mStView.setTextColor(Color.WHITE);
							} else {
								GPS.this.mStView.setText("GPS 打开失败");
								GPS.this.mStView.setTextColor(Color.RED);
							}
						}
					}).create().show();
				
				super.handleMessage(msg);
			}
		};
		
		GPSUtil localGpsUtil = new GPSUtil(this);
		this.mGpsUtil = localGpsUtil;
		SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
		this.mSp = localSharedPreferences;
		
		TextView localTextView2 = (TextView) findViewById(R.id.gps_satellite_id);
		this.mSatelliteNumView = localTextView2;
		
		TextView localTextView3 = (TextView) findViewById(R.id.gps_signal_id);
		this.mSignalView = localTextView3;
		
		TextView localTextView4 = (TextView) findViewById(R.id.gps_result_id);
		this.mResultView = localTextView4;
		
		Chronometer localChronometer1 = (Chronometer) findViewById(R.id.gps_time_id);
		this.mTimeView = localChronometer1;
		
		Button localButton1 = (Button) findViewById(R.id.gps_bt_ok);
		this.mBtOk = localButton1;
		
		Button localButton2 = (Button) findViewById(R.id.gps_bt_failed);
		this.mBtFailed = localButton2;
		
		this.mBtOk.setOnClickListener(this);
		this.mBtFailed.setOnClickListener(this);
		
		Chronometer localChronometer2 = this.mTimeView;
		String str = getResources().getString(R.string.GPS_time);
		localChronometer2.setFormat(str);
		
		if(this.mGpsUtil.isGpsEnable()){
			this.mStView.setText(R.string.GPS_connect);
			this.mStView.setTextColor(Color.WHITE);
		} else {
			this.mStView.setText("GPS 打开失败");
			this.mStView.setTextColor(Color.RED);
		}
		
		this.mTimeView.start();
		getSatelliteInfo();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(this.mGpsUtil.isGpsEnable()){
			this.mStView.setText(R.string.GPS_connect);
			this.mStView.setTextColor(Color.WHITE);
		} else {
			this.mStView.setText("GPS 打开失败");
			this.mStView.setTextColor(Color.RED);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected void onDestroy() {
		this.mGpsUtil.closeLocation();
		super.onDestroy();
	}
}