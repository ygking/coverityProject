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

import static android.hardware.Sensor.TYPE_AMBIENT_TEMPERATURE;
import static android.hardware.Sensor.TYPE_GYROSCOPE;
import static android.hardware.Sensor.TYPE_TEMPERATURE;

public class TemperatureSensor extends Activity implements SensorEventListener {

    private  String TAG = "TemperatureSensor_test";
    public View.OnClickListener cl;
    Button mBtFailed;
    Button mBtOk;
    Sensor mTempSensor = null;
    SensorManager mSensorManager = null;
    SharedPreferences mSp;
    TextView mValueX = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature_sensor);

        this.mSp = getSharedPreferences("FactoryMode", 0);
        this.mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(TYPE_AMBIENT_TEMPERATURE )!= null) {
            this.mTempSensor = this.mSensorManager.getDefaultSensor(TYPE_AMBIENT_TEMPERATURE);//
        }
        if(mTempSensor == null){
            Log.d(TAG,"temperature is null");
        }

        mValueX = (TextView)findViewById(R.id.temperature_sensor);

        this.mBtOk = (Button)findViewById(R.id.tempsensor_bt_ok);
        this.mBtOk.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Context localContext = TemperatureSensor.this.getApplicationContext();
                SharedPreferences localSharedPreferences = TemperatureSensor.this.mSp;
                Utils.SetPreferences(localContext, localSharedPreferences, R.string.temperature_name, "success");
                TemperatureSensor.this.finish();
            }
        });
        this.mBtFailed = (Button)findViewById(R.id.tempsensor_bt_failed);
        this.mBtFailed.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Context localContext = TemperatureSensor.this.getApplicationContext();
                SharedPreferences localSharedPreferences = TemperatureSensor.this.mSp;
                Utils.SetPreferences(localContext, localSharedPreferences,  R.string.temperature_name, "failed");
                TemperatureSensor.this.finish();
            }
        });
    }

    protected void onPause() {
        super.onPause();
        SensorManager localSensorManager = this.mSensorManager;
        Sensor localSensor = this.mTempSensor;
        localSensorManager.unregisterListener(this, localSensor);
    }

    protected void onResume() {
        super.onResume();
        SensorManager localSensorManager = this.mSensorManager;
        Sensor localSensor = this.mTempSensor;
        localSensorManager.registerListener(this, localSensor, 0);
    }

    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == TYPE_AMBIENT_TEMPERATURE){
            float temperature = event.values[0];
            TextView localTextView = this.mValueX;
            String str_temp = getString(R.string.temperature) + ":" + String.valueOf(temperature);
            localTextView.setText(str_temp);
            Log.d(TAG,"temperature :"+temperature);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

}