package com.goertek.factorytest.microphone;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;
import com.goertek.factorytest.VUMeter;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by vicky.yang on 2017/12/27.
 */

public class headset extends Activity implements View.OnClickListener {
    private String TAG = "headset_Test";
    public Handler h;
    private Button mBtMicFailed;
    private Button mBtMicOk;
    private MediaPlayer mPlayer;
    private Button mRecord;
    private MediaRecorder mRecorder;
    public SharedPreferences mSp;

    VUMeter mVUMeter;
    Runnable ra;
    AudioManager audioManager;
    class headset1 implements Runnable {
        public void run() {
            mVUMeter.invalidate();
            h.postDelayed(this, 100L);
        }
    }

    public headset() {
        this.h = new Handler();
        this.ra = new headset1();

    }
    boolean startRecord=false;
    private void start() {
        Log.d(TAG,"start");
        this.h.post(this.ra);
        if (this.mPlayer != null) {
            this.mPlayer.stop();
            Log.d(TAG,"start--mPlayer.stop");
        }
        if (!Environment.getExternalStorageState().equals("mounted")) {
            this.mRecord.setText(R.string.sdcard_tips_failed);
        }
        try {
            this.mRecorder = new MediaRecorder();
            this.mRecorder.setAudioSource(1);//MediaRecorder.AudioSource.MIC
            this.mRecorder.setOutputFormat(1);
            this.mRecorder.setAudioEncoder(3);
            this.mVUMeter.setRecorder(this.mRecorder);
            StringBuilder localStringBuilder = new StringBuilder();
            String str = null;
            File localFile = Environment.getExternalStorageDirectory();
            localStringBuilder.append(localFile).append(File.separator).append("test.mp3");
            str = localStringBuilder.toString();
            if (!new File(str).exists())
                new File(str).createNewFile();
            this.mRecorder.setOutputFile(str);
            this.mRecorder.prepare();
            this.mRecorder.start();
            this.mRecord.setTag("ing");
            if(audioManager.isWiredHeadsetOn()) {
                this.mRecord.setText(R.string.Mic_stop);
            }
            startRecord=true;
        } catch (Exception localException) {
            localException.printStackTrace();
            String str3 = localException.getMessage();
            Toast.makeText(this, str3, 0);
            this.mRecord.setTag("ing");
        }
    }

    private void stopAndSave() {
        if(!startRecord){
            return;
        }
        this.h.removeCallbacks(this.ra);
        this.mRecorder.stop();
        this.mRecorder.release();
        this.mRecorder = null;
        if(audioManager.isWiredHeadsetOn()) {
            this.mRecord.setText(R.string.Mic_start);
        }
        this.mRecord.setTag("");
        this.mVUMeter.SetCurrentAngle(0);
        try {
            Log.d(TAG,"start to play");
            MediaPlayer localMediaPlayer = new MediaPlayer();
            this.mPlayer = localMediaPlayer;
            this.mPlayer.setDataSource("/sdcard/test.mp3");
            this.mPlayer.prepare();
            Log.d(TAG,"start to play 2");
            this.mPlayer.start();
            startRecord=false;
        } catch (IllegalArgumentException localIllegalArgumentException) {
            Log.d(TAG,"start to play 3");
            localIllegalArgumentException.printStackTrace();
        } catch (IllegalStateException localIllegalStateException) {
            Log.d(TAG,"start to play 4");
            localIllegalStateException.printStackTrace();
        } catch (IOException localIOException) {
            Log.d(TAG,"start to play 5");
            localIOException.printStackTrace();
        }
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // Successfully.
            if(requestCode == 200) {
                // TODO ...
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // Failure.
            if(requestCode == 200) {
                // TODO ...
            }
        }
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_HEADSET_PLUG)){
                if(intent.hasExtra("state")) {
                    if((intent.getIntExtra("state", 0) == 0)&&(audioManager!=null)&&(!audioManager.isWiredHeadsetOn())) {
                        Log.d(TAG,"onReceive: heaset not plug2.");
                        if(mRecord!=null){
                            mRecord.setText(R.string.HeadSet_tips);
                            //stopAndSave();
                        }
                    } else if(intent.getIntExtra("state", 0) == 1) {
                        Log.d(TAG,"onReceive: heaset plug.");
                        mRecord.setText(R.string.Mic_start);
                    }
                }
            }
        }
    };

    //未测试，
    public class MediaButtonReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            // 获得Action
            String intentAction = intent.getAction();
            // 获得KeyEvent对象
            KeyEvent keyEvent = (KeyEvent) intent
                    .getParcelableExtra(Intent.EXTRA_KEY_EVENT);

            Log.i(TAG, "Action ---->" + intentAction + "  KeyEvent----->"
                    + keyEvent.toString());
            // 按下 / 松开 按钮
            int keyAction = keyEvent.getAction();

            if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)
                    && (KeyEvent.ACTION_DOWN == keyAction)) {
                // 获得按键字节码
                int keyCode = keyEvent.getKeyCode();

                // 获得事件的时间
                // long downtime = keyEvent.getEventTime();

                // 获取按键码 keyCode
//          StringBuilder sb = new StringBuilder();
//          // 这些都是可能的按键码 ， 打印出来用户按下的键
//          if (KeyEvent.KEYCODE_MEDIA_NEXT == keyCode) {
//              sb.append("KEYCODE_MEDIA_NEXT");
//          }
                // 说明：当我们按下MEDIA_BUTTON中间按钮时，实际出发的是 KEYCODE_HEADSETHOOK 而不是
                // KEYCODE_MEDIA_PLAY_PAUSE
                if (KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE == keyCode) {
//              sb.append("KEYCODE_MEDIA_PLAY_PAUSE");

                }
                if (KeyEvent.KEYCODE_HEADSETHOOK == keyCode) {
//              sb.append("KEYCODE_HEADSETHOOK");
                }
                if (KeyEvent.KEYCODE_MEDIA_PREVIOUS == keyCode) {
//              sb.append("KEYCODE_MEDIA_PREVIOUS");
                }
                if (KeyEvent.KEYCODE_MEDIA_STOP == keyCode) {
//              sb.append("KEYCODE_MEDIA_STOP");
                }
                // 输出点击的按键码
//          Log.i(TAG, sb.toString());
//          Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
            } else if (KeyEvent.ACTION_UP == keyAction) {
                Log.i("chengjie", "aaa");
            }
        }
    }

// http://blog.csdn.net/shineflowers/article/details/44857549
    public void onClick(View paramView) {
        SharedPreferences localSharedPreferences = this.mSp;
        String str1 = Environment.getExternalStorageState();
        String str2 = "mounted";

        /*if(audioManager.isWiredHeadsetOn()){
            Log.d(TAG,"headset insert.");
        }else {
            this.mRecord.setText(R.string.HeadSet_tips);
            return;
        }*/
        Log.d(TAG,"ID="+paramView.getId());
        if (paramView.getId() == this.mRecord.getId()) {
            Log.d(TAG,"mRecord ID="+this.mRecord.getId());
            if(mRecord.getText().toString().equals(R.string.HeadSet_tips)){
                return;
            }
            if (str1.equals(str2)) {
                if ((this.mRecord.getTag() != null) && (this.mRecord.getTag().equals("ing")))
                    stopAndSave();
                else
                    start();
            } else {
                this.mRecord.setText(R.string.sdcard_tips_failed);
            }
        }

        if (paramView.getId() == this.mBtMicOk.getId()) {
            Log.d(TAG,"mBtMicOk ID="+this.mBtMicOk.getId());
           // this.mBtMicFailed.setBackgroundColor(getResources().getColor(R.color.gray));
            //this.mBtMicOk.setBackgroundColor(getResources().getColor(R.color.Green));
            Utils.SetPreferences(this, localSharedPreferences, R.string.headset_name, "success");
            this.finish();
        }

        if (paramView.getId() == this.mBtMicFailed.getId()) {
            Log.d(TAG,"mBtMicFailed ID="+this.mBtMicFailed.getId());
            //this.mBtMicFailed.setBackgroundColor(getResources().getColor(R.color.Red));
            //this.mBtMicOk.setBackgroundColor(getResources().getColor(R.color.gray));
            Utils.SetPreferences(this, localSharedPreferences, R.string.headset_name, "failed");
            this.finish();
        }
    }
    private ComponentName mComponentName;
    public void onCreate(Bundle paramBundle) {

        super.onCreate(paramBundle);
        setContentView(R.layout.headset);

        this.mSp = getSharedPreferences("FactoryMode", 0);

        this.mRecord = (Button) findViewById(R.id.mic_bt_start);
        this.mRecord.setOnClickListener(this);
        this.mBtMicOk = (Button) findViewById(R.id.bt_ok);
        this.mBtMicOk.setOnClickListener(this);
        this.mBtMicFailed = (Button) findViewById(R.id.bt_failed);
        this.mBtMicFailed.setOnClickListener(this);

        this.mVUMeter = (VUMeter) findViewById(R.id.uvMeter);

        AndPermission.with(this)
                .requestCode(100)
                .permission(Permission.MICROPHONE,Permission.STORAGE)
//				.permission(Permission.STORAGE)
                .callback(listener)
                .start();
        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(mReceiver, filter);
        //mComponentName = new ComponentName(getPackageName(), MediaButtonReceiver.class.getName());

    }
    @Override
    protected void onResume() {
       // audioManager.registerMediaButtonEventReceiver(mComponentName);
        super.onResume();
        if(audioManager.isWiredHeadsetOn()){
            Log.d(TAG,"headset plug");
            mRecord.setText(R.string.Mic_start);
        }else {
            Log.d(TAG,"headset not plug");
            mRecord.setText(R.string.HeadSet_tips);
        }
    }
    protected void onDestroy() {
        audioManager.unregisterMediaButtonEventReceiver(mComponentName);
        Log.d("yxyx","onDestroy-headset");
        super.onDestroy();
        Log.d("yxyx","onDestroy-headset 2");
        new File("/sdcard/test.mp3").delete();
        if (this.mPlayer != null)
            this.mPlayer.stop();
        if (this.mRecorder != null)
            this.mRecorder.stop();
        unregisterReceiver(mReceiver);

    }


}
