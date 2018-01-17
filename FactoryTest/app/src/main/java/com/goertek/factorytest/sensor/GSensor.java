package com.goertek.factorytest.sensor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;
import android.util.Log;

public class GSensor extends Activity implements View.OnClickListener
{
	private static final int OFFSET = 2;
	private ImageView ivimg;
	private SensorManager mManager = null;
	private Sensor mSensor = null;
	private SensorEventListener mListener = null;
	SharedPreferences mSp;
	private Button mBtFailed;
	private Button mBtOk;
	private TextView tv_result;
	String str = "";
	
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(tv_result != null) {
				tv_result.setText(str);
			}
		};
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gsensor);
		
		tv_result = (TextView) findViewById(R.id.tv_result);
		
	    mManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	    mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    mListener = new SensorEventListener(){

			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub

			}

			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				float x = event.values[SensorManager.DATA_X];
				float y = event.values[SensorManager.DATA_Y];
				float z = event.values[SensorManager.DATA_Z];
				
//				Log.i("Gsensor", "x = " + x + "y = " + y + "z = " + z);
				str = "x = " + x + "\ny = " + y + "\nz = " + z;
				
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						mHandler.sendEmptyMessage(0);
					}
				}, 400);

				if(x > -4 && x < 4 && y < 4 && z > 7){
            				GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_x);
        			}
        			else if(x > -4 && x < 4 && y > 4 && z < 7){
            				GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_x_2);
				}
				else if (x < -4 && y > -1 && y < 4 && z < 7){
				    GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_z);
				}
				else if(x > 4 && y > -1 && y < 4 && z < 7){
				    GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_y);
				}
			}
	    };
	    
	    mSp = getSharedPreferences("FactoryMode", 0);
	    ivimg = (ImageView)findViewById(R.id.gsensor_iv_img);
	    mBtOk = (Button)findViewById(R.id.gsensor_bt_ok);
	    mBtOk.setOnClickListener(this);
	    mBtFailed = (Button)findViewById(R.id.gsensor_bt_failed);
	    mBtFailed.setOnClickListener(this);
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mManager.unregisterListener(mListener);
		super.onPause();
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mManager.registerListener(mListener, mSensor, SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
	}



	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	    if(arg0.getId() == mBtOk.getId()){
	        Utils.SetPreferences(this, mSp, R.string.gsensor_name, "success");
	        finish();
	    }
	    else{
	        Utils.SetPreferences(this, mSp, R.string.gsensor_name, "failed");
	        finish();
	    }
	}
	
	
}
