package com.goertek.factorytest.screen;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class ChangeScreen extends Activity {
    private Button btnScreen;
    private Button btnSuccess;
    private Button btnFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen);
        btnScreen = (Button) findViewById(R.id.btn001_change_screen);
        btnSuccess = (Button) findViewById(R.id.btn001_activity_screen);
        btnFailed = (Button) findViewById(R.id.btn002_activity_screen);

        btnScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });
        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
                Utils.SetPreferences(ChangeScreen.this, sharedPreferences, R.string.screen, "success");
                finish();
            }
        });

        btnFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
                Utils.SetPreferences(ChangeScreen.this, sharedPreferences, R.string.screen, "failed");
                finish();
            }
        });
    }
}
