package com.goertek.factorytest.volume;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;

public class VolumeActivity extends Activity {

	public MediaPlayer myMediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volume);
		copy();
	}

	@Override
	protected void onDestroy() {
		if (myMediaPlayer != null) {
			myMediaPlayer.stop();
			myMediaPlayer.release();
			myMediaPlayer = null;
		}
		super.onDestroy();
	}

	public void copy() {
		File clatter = new File(getFilesDir(), "clatter.mp3");

		if (clatter.exists()) {
			Log.d("CameraActivity", "clatter.mp3 exist");
			return;
		}

		try {
			InputStream inputStream = getAssets().open("clatter.mp3");
			FileOutputStream fileOutputStream = new FileOutputStream(clatter);

			int len = 0;
			byte[] bytes = new byte[1024];
			while ((len = inputStream.read(bytes)) != -1) {
				fileOutputStream.write(bytes, 0, len);
			}

			inputStream.close();
			fileOutputStream.flush();
			fileOutputStream.close();

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void button001(View view) {
		AudioManager mAudioManager = (AudioManager) getSystemService(VolumeActivity.this.AUDIO_SERVICE);
		mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FX_FOCUS_NAVIGATION_UP);
		if (myMediaPlayer == null) {
			myMediaPlayer = new MediaPlayer();

		}
		try {
			Thread.sleep(200);
			myMediaPlayer.reset();
			myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			String absolutePath = new File(getFilesDir(), "clatter.mp3").getAbsolutePath();
			myMediaPlayer.setDataSource(absolutePath);
			myMediaPlayer.prepare();
			myMediaPlayer.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void button002(View view) {
		AudioManager mAudioManager = (AudioManager) getSystemService(VolumeActivity.this.AUDIO_SERVICE);
		mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
		if (myMediaPlayer == null) {
			myMediaPlayer = new MediaPlayer();
		}
		try {
			Thread.sleep(200);
			myMediaPlayer.reset();
			myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			String absolutePath = new File(getFilesDir(), "clatter.mp3").getAbsolutePath();
			myMediaPlayer.setDataSource(absolutePath);
			myMediaPlayer.prepare();
			myMediaPlayer.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void button003(View view) {
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
		Utils.SetPreferences(this, sharedPreferences, R.string.modify_volume, "success");
		finish();
	}

	public void button004(View view) {
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
		Utils.SetPreferences(this, sharedPreferences, R.string.modify_volume, "failed");
		finish();
	}
}
