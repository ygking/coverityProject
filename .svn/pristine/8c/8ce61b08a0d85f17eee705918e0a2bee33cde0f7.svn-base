package com.goertek.factorytest.sensor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;

import static android.hardware.Sensor.TYPE_GYROSCOPE;

public class GyroscopeSensor extends Activity implements SensorEventListener {

    private  String TAG = "GyroscopeSensor_test";
    public View.OnClickListener cl;
    Button mBtFailed;
    Button mBtOk;
    Sensor mGySensor = null;
    SensorManager mSensorManager = null;
    SharedPreferences mSp;
    TextView mValueX = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyroscope_sensor);

        this.mSp = getSharedPreferences("FactoryMode", 0);
        this.mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        this.mGySensor = this.mSensorManager.getDefaultSensor(TYPE_GYROSCOPE);//

        mValueX = (TextView)findViewById(R.id.gyroscope_sensor);

        this.mBtOk = (Button)findViewById(R.id.gysensor_bt_ok);
        this.mBtOk.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Context localContext = GyroscopeSensor.this.getApplicationContext();
                SharedPreferences localSharedPreferences = GyroscopeSensor.this.mSp;
                Utils.SetPreferences(localContext, localSharedPreferences, R.string.gyroscope_name, "success");
                GyroscopeSensor.this.finish();
            }
        });
        this.mBtFailed = (Button)findViewById(R.id.gysensor_bt_failed);
        this.mBtFailed.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Context localContext = GyroscopeSensor.this.getApplicationContext();
                SharedPreferences localSharedPreferences = GyroscopeSensor.this.mSp;
                Utils.SetPreferences(localContext, localSharedPreferences,  R.string.gyroscope_name, "failed");
                GyroscopeSensor.this.finish();
            }
        });
    }

    protected void onPause() {
        super.onPause();
        SensorManager localSensorManager = this.mSensorManager;
        Sensor localSensor = this.mGySensor;
        localSensorManager.unregisterListener(this, localSensor);
    }

    protected void onResume() {
        super.onResume();
        SensorManager localSensorManager = this.mSensorManager;
        Sensor localSensor = this.mGySensor;
        localSensorManager.registerListener(this, localSensor, 0);
    }

    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == TYPE_GYROSCOPE){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            Log.d(TAG,"x :"+x+"--y:"+y+"--z:"+z);

            TextView localTextView = this.mValueX;
            String str_temp = getString(R.string.gyroscope) + " \n x :" + String.valueOf(x) + " \n y :" + String.valueOf(y) + "\n z :" + String.valueOf(z);
            localTextView.setText(str_temp);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

}
