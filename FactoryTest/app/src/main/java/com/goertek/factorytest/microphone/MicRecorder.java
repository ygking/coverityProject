package com.goertek.factorytest.microphone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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

public class MicRecorder extends Activity implements OnClickListener {
    private String TAG = "MicRecorder_Test";
	public Handler h;
	private Button mBtMicFailed;
	private Button mBtMicOk;
	private MediaPlayer mPlayer;
	private Button mRecord;
	private MediaRecorder mRecorder;
	public SharedPreferences mSp;

	VUMeter mVUMeter;
	Runnable ra;

	class MicRecorder1 implements Runnable {
		public void run() {
			mVUMeter.invalidate();
			h.postDelayed(this, 100L);
		}
	}

	public MicRecorder() {
		this.h = new Handler();
		this.ra = new MicRecorder1();
	}

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
			/*
			http://blog.csdn.net/m0_37039192/article/details/77776844
			* */
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
			this.mRecord.setText(R.string.Mic_stop);
		} catch (Exception localException) {
			localException.printStackTrace();
			String str3 = localException.getMessage();
			Toast.makeText(this, str3, 0);
			this.mRecord.setTag("ing");
		}
	}

	private void stopAndSave() {
		this.h.removeCallbacks(this.ra);
		this.mRecorder.stop();
		this.mRecorder.release();
		this.mRecorder = null;
		this.mRecord.setText(R.string.Mic_start);
		this.mRecord.setTag("");
		this.mVUMeter.SetCurrentAngle(0);
		try {
			MediaPlayer localMediaPlayer = new MediaPlayer();
			this.mPlayer = localMediaPlayer;
			this.mPlayer.setDataSource("/sdcard/test.mp3");
			this.mPlayer.prepare();
			this.mPlayer.start();
		} catch (IllegalArgumentException localIllegalArgumentException) {
			localIllegalArgumentException.printStackTrace();
		} catch (IllegalStateException localIllegalStateException) {
			localIllegalStateException.printStackTrace();
		} catch (IOException localIOException) {
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


	public void onClick(View paramView) {
		SharedPreferences localSharedPreferences = this.mSp;
		String str1 = Environment.getExternalStorageState();
		String str2 = "mounted";

		if (paramView.getId() == this.mRecord.getId()) {
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
//			this.mBtMicFailed.setBackgroundColor(getResources().getColor(R.color.gray));
//			this.mBtMicOk.setBackgroundColor(getResources().getColor(R.color.Green));
			Utils.SetPreferences(this, localSharedPreferences, R.string.microphone_name, "success");
			finish();
		}

		if (paramView.getId() == this.mBtMicFailed.getId()) {
//			this.mBtMicFailed.setBackgroundColor(getResources().getColor(R.color.Red));
//			this.mBtMicOk.setBackgroundColor(getResources().getColor(R.color.gray));
			Utils.SetPreferences(this, localSharedPreferences, R.string.microphone_name, "failed");
			finish();
		}
	}

	public void onCreate(Bundle paramBundle) {

		super.onCreate(paramBundle);
		setContentView(R.layout.micrecorder);

		this.mSp = getSharedPreferences("FactoryMode", 0);

		this.mRecord = (Button) findViewById(R.id.mic_bt_start);
		this.mRecord.setOnClickListener(this);
		this.mBtMicOk = (Button) findViewById(R.id.mic_bt_ok);
		this.mBtMicOk.setOnClickListener(this);
		this.mBtMicFailed = (Button) findViewById(R.id.mic_bt_failed);
		this.mBtMicFailed.setOnClickListener(this);

		this.mVUMeter = (VUMeter) findViewById(R.id.uvMeter);

		AndPermission.with(this)
				.requestCode(100)
				.permission(Permission.MICROPHONE,Permission.STORAGE)
//				.permission(Permission.STORAGE)
				.callback(listener)
				.start();

	}

	protected void onDestroy() {
		super.onDestroy();
		new File("/sdcard/test.mp3").delete();
		if (this.mPlayer != null)
			this.mPlayer.stop();
		if (this.mRecorder != null)
			this.mRecorder.stop();
	}
}