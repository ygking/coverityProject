package com.goertek.factorytest.sensor;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class PSensor extends Activity
  implements SensorEventListener
{
  public OnClickListener cl;
 // TextView mAccuracyView = null;
    private  String TAG = "PSensor_test";
  Button mBtFailed;
  Button mBtOk;
  Sensor mPSensor = null;
  SensorManager mSensorManager = null;
  SharedPreferences mSp;
  TextView mValueX = null;
  

  public void onAccuracyChanged(Sensor paramSensor, int paramInt)
  {
   /* if (paramSensor.getType() != 8)
      return;
    TextView localTextView = this.mAccuracyView;
    StringBuilder localStringBuilder = new StringBuilder().append(getString(R.string.LSensor_accuracy)).append(paramInt);
    localTextView.setText(localStringBuilder.toString());
    */
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.psensor);
    this.mSp = getSharedPreferences("FactoryMode", 0);
    this.mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    this.mPSensor = this.mSensorManager.getDefaultSensor(8);//TYPE_PROXIMITY
    //this.mAccuracyView = (TextView)findViewById(R.id.lsensor_accuracy);
    this.mValueX = (TextView)findViewById(R.id.proximity);
    this.mBtOk = (Button)findViewById(R.id.psensor_bt_ok);
    this.mBtOk.setOnClickListener(new OnClickListener(){
 
        public void onClick(View v) {
            Context localContext = PSensor.this.getApplicationContext();
            SharedPreferences localSharedPreferences = PSensor.this.mSp;
            Utils.SetPreferences(localContext, localSharedPreferences, R.string.psensor_name, "success");
            PSensor.this.finish();
        }
    });
    this.mBtFailed = (Button)findViewById(R.id.psensor_bt_failed);
    this.mBtFailed.setOnClickListener(new OnClickListener(){
        public void onClick(View v) {
            Context localContext = PSensor.this.getApplicationContext();
            SharedPreferences localSharedPreferences = PSensor.this.mSp;
            Utils.SetPreferences(localContext, localSharedPreferences,  R.string.psensor_name, "failed");
            PSensor.this.finish();
        }
    });
  }

  protected void onPause()
  {
    super.onPause();
    SensorManager localSensorManager = this.mSensorManager;
    Sensor localSensor = this.mPSensor;
    localSensorManager.unregisterListener(this, localSensor);
  }

  protected void onResume()
  {
    super.onResume();
    SensorManager localSensorManager = this.mSensorManager;
    Sensor localSensor = this.mPSensor;
    localSensorManager.registerListener(this, localSensor, 0);
  }

  public void onSensorChanged(SensorEvent paramSensorEvent)
  {
      Log.d(TAG,"onSensorChanged --");
    if (paramSensorEvent.sensor.getType() != 8)
      return;
    float[] arrayOfFloat = paramSensorEvent.values;
    /*
      http://www.android-doc.com/reference/android/hardware/SensorEvent.html#values
      Sensor.TYPE_PROXIMITY:
      values[0]: Proximity sensor distance measured in centimeters
      Note: Some proximity sensors only support a binary near or far measurement.
          In this case, the sensor should report its maximum range value in the far state and a lesser value in the near state.*/
    for(int i=0;i<arrayOfFloat.length;i++){
        Log.d("yxyx","i="+i+" value="+arrayOfFloat[i]);
    }

    TextView localTextView = this.mValueX;
    float ranage = this.mPSensor.getMaximumRange();

      Log.d(TAG,"onSensorChanged --ranage :"+ranage);
    StringBuilder localStringBuilder1 = new StringBuilder().append(getString(R.string.proximity)).append(arrayOfFloat[0]).append(" -").append(ranage);
    localTextView.setText(localStringBuilder1.toString());

  }
}
  