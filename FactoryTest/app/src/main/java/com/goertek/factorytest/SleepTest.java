package com.goertek.factorytest;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by vicky.yang on 2017/12/27.
 */

public class SleepTest extends Activity implements View.OnClickListener {
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;
    static  String TAG="yyy_sleepTest";
    private Button mRecord;
    public static final String INTENT_ALARM_WAKEUP = "intent_alarm_wakeup";
    AlarmManager am;
    PendingIntent pi;
    private Button mBtMicFailed;
    private Button mBtMicOk;
    public SharedPreferences mSp;

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.sleep);
        this.mRecord = (Button) findViewById(R.id.button001_activity_version);
        this.mRecord.setOnClickListener(this);
        this.mBtMicOk = (Button) findViewById(R.id.bt_ok);
        this.mBtMicOk.setOnClickListener(this);
        this.mBtMicFailed = (Button) findViewById(R.id.bt_failed);
        this.mBtMicFailed.setOnClickListener(this);
        Intent intent = new Intent(INTENT_ALARM_WAKEUP);
        pi = PendingIntent.getBroadcast(SleepTest.this, 0, intent, 0);
        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        powerManager=(PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mSp = getSharedPreferences("FactoryMode", 0);
    }
    private final BroadcastReceiver AlarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive enter, "+action);
            if (action == INTENT_ALARM_WAKEUP) {
                Log.d(TAG, "receive alarm_wakeup.");
                wakeupDevice();
            }
        }
    };

    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        //filter.addAction("android.intent.action.intent_alarm_wakeup");
        filter.addAction("intent_alarm_wakeup");
        registerReceiver(AlarmReceiver, filter);
    }
    public void onClick(View paramView) {
        Log.d(TAG,"onClick enter paramView_id="+paramView.getId()+" mRecord_id="+this.mRecord.getId());

        SharedPreferences localSharedPreferences = this.mSp;
        if (paramView.getId() == this.mRecord.getId())
        {
            Log.d(TAG,"sleep enter");
            long current=System.currentTimeMillis();
            am.set(AlarmManager.RTC_WAKEUP, current+10000, pi); //10s
            sleepDevice();
        }
        if (paramView.getId() == this.mBtMicOk.getId()) {
            Log.d(TAG,"mBtMicOk enter");
            //this.mBtMicFailed.setBackgroundColor(getResources().getColor(R.color.gray));
            //this.mBtMicOk.setBackgroundColor(getResources().getColor(R.color.Green));
            Utils.SetPreferences(this, localSharedPreferences, R.string.sleep_name, "success");
            this.finish();
        }

        if (paramView.getId() == this.mBtMicFailed.getId()) {
            //this.mBtMicFailed.setBackgroundColor(getResources().getColor(R.color.Red));
           // this.mBtMicOk.setBackgroundColor(getResources().getColor(R.color.gray));
            Utils.SetPreferences(this, localSharedPreferences, R.string.sleep_name, "failed");
            this.finish();
        }

    }
    private void sleepDevice() {
        Log.d(TAG,"sleepDevice enter.");
        try {
            Method method = powerManager.getClass().getMethod("goToSleep", Long.TYPE);
            method.invoke(powerManager, SystemClock.uptimeMillis());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void sleepDevice_1(){
        Log.d(TAG,"sleepDevice enter.");
        try {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "sleepTest");
            //wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "sleepTest");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        if (wakeLock != null) {
            wakeLock.acquire();
            Log.d(TAG,"sleep...");
            /*try {
                Thread.sleep(10000);
            }catch (InterruptedException e){

            }*/
            wakeLock.release();
            Log.d(TAG,"release...");
            wakeLock = null;
        }
    }

    private void wakeupDevice(){
        Log.d(TAG,"wakeupDevice enter.");
        try {
            wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, "sleepTest");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        if (wakeLock != null) {
            wakeLock.acquire();
            wakeLock.release();
            wakeLock = null;
        }
        unregisterReceiver(AlarmReceiver);
    }
}
