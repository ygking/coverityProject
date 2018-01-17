package com.goertek.factorytest.sensor;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;

/**
 * Created by sunyibin on 2016/6/20 0020.
 */
public class ProjectionGSensor extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.projection_gsensor);
    }
    public void button001(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.projection_gsensor, "success");
        finish();
    }
    public void button002(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.projection_gsensor, "failed");
        finish();
    }
}
