package com.goertek.factorytest.led;

import android.app.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.goertek.factorytest.CommandExecution;
import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;

import java.io.FileOutputStream;
import java.io.IOException;

public class Led extends Activity implements View.OnClickListener, Runnable {
    private String TAG = "Led_test";
    private Button mBtFailed;
    private Button mBtOk;
    private SharedPreferences mSp;
    private NotificationManager nM;
    private Notification n;
    private int ledColor = 0x00ff0000;//start red;
    private boolean bExit = false;

    private static final String POWER_RED = "/sys/class/leds/red/brightness";
    private static final String POWER_RED_BLINK = "/sys/class/leds/red/red_blink";
    private static final String POWER_GREEN = "/sys/class/leds//green/brightness";
    private static final String POWER_GREEN_BLINK = "/sys/class/leds/green/green_blink";
    private static final String POWER_BLUE = "/sys/class/leds/blue/brightness";
    private static final String POWER_BLUE_BLINK = "/sys/class/leds/blue/blue_blink";

    private static final String LED_ON = "255";
    private static final String LED_OFF = "0";
    private static final String LED_BLINK = "300001";


    public void writeFile(String file, String content) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onLed() {
        Log.d(TAG,"onLed");
        writeFile(POWER_RED, LED_ON);
//        writeFile(POWER_RED_BLINK, LED_OFF);
        writeFile(POWER_GREEN, LED_ON);
//        writeFile(POWER_GREEN_BLINK, LED_OFF);
        writeFile(POWER_BLUE, LED_ON);
//        writeFile(POWER_BLUE_BLINK, LED_OFF);
    }

    public void offLed() {
        Log.d(TAG,"offLed");
        writeFile(POWER_RED, LED_OFF);
//        writeFile(POWER_RED_BLINK, LED_OFF);
        writeFile(POWER_GREEN, LED_OFF);
//        writeFile(POWER_GREEN_BLINK, LED_OFF);
        writeFile(POWER_BLUE, LED_OFF);
//        writeFile(POWER_BLUE_BLINK, LED_OFF);
    }

    public void onClick(View paramView) {
        SharedPreferences localSharedPreferences = this.mSp;
        if (paramView.getId() == this.mBtOk.getId()) {
            Utils.SetPreferences(this, localSharedPreferences, R.string.led_name, "success");
            bExit = true;
            cancellLedShow();
            finish();
        } else {
            Utils.SetPreferences(this, localSharedPreferences, R.string.led_name, "failed");
            bExit = true;
            cancellLedShow();
            finish();

        }
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.vibrator);

//      setContentTitle（）;
//      setSmallIcon（）
        this.mSp = getSharedPreferences("FactoryMode", 0);
        this.mBtOk = (Button) findViewById(R.id.vibrator_bt_ok);
        this.mBtOk.setOnClickListener(this);
        this.mBtFailed = (Button) findViewById(R.id.vibrator_bt_failed);
        this.mBtFailed.setOnClickListener(this);
        nM = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        n = new Notification();
        n.ledARGB = 0xffffffff;
        n.defaults = Notification.DEFAULT_LIGHTS;
        n.flags |= Notification.FLAG_SHOW_LIGHTS;
        n.ledOnMS = 1000;
        n.ledOffMS = 0;
        Log.d(TAG, "onCreate --n" + n);
        exeCmd();

//        onLed();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        offLed();
//      nM.notify(1, n);
//      nM.cancel(1);
//      Log.d(TAG,"onCreate --notify");
        new Thread(this).start();

    }

    private void exeCmd(){
        String cmd = "setenforce 0";
        CommandExecution.execCommand(cmd,false);
    }

    public void onDestroy() {
        super.onDestroy();
        bExit = true;
        cancellLedShow();
    }

    public void updateLedState() {
        n.ledARGB = 0xff000000 | ledColor;
        Log.i("LED", "argb=" + n.ledARGB);
        nM.notify(1, n);
        Log.d(TAG, "updateLedState");
        ledColor = ledColor >> 8;
        if (ledColor == 0) ledColor = 0x00ff0000;
    }

    private void cancellLedShow() {
        Log.d(TAG, "cancellLedShow");
        nM.cancel(1);
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted() && (!bExit)) {
            onLed();
            //change led
//            updateLedState();
            //sleep 1 s
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            //cancell
//            cancellLedShow();
            offLed();

        }


    }
}