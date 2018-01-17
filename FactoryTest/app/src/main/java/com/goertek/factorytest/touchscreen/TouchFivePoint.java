package com.goertek.factorytest.touchscreen;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class TouchFivePoint extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_five_point);
        /**
         * TP5点触摸
         */
    }
    public void button001(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.touch_five_point, "success");
        finish();
    }

    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.touch_five_point, "failed");
        finish();
    }
}
