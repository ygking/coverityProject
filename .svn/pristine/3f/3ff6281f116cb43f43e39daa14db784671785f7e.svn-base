package com.goertek.factorytest.backlight;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.WindowManager;

import com.goertek.factorytest.R;
import com.goertek.factorytest.ShellExe;
import com.goertek.factorytest.Utils;
import java.io.IOException;
import java.io.PrintStream;

public class BackLight extends Activity implements OnClickListener {
	private final int ERR_ERR = 1;
	private final int ERR_OK = 0;
	private String lcdCmdOFF = "echo 50 > /sys/class/leds/lcd-backlight/brightness";
	private String lcdCmdON = "echo 250 > /sys/class/leds/lcd-backlight/brightness";
	private Button mBtFailed;
	private Button mBtOk;
	private Button mBtnLcdOFF;
	private Button mBtnLcdON;
	private SharedPreferences mSp;

	public void onClick(View paramView) {

		SharedPreferences localSharedPreferences = this.mSp;

		if (isAutoBrightness(getContentResolver())) {
			stopAutoBrightness(this);
		}

		if (paramView.getId() == this.mBtnLcdON.getId() || paramView.getId() == this.mBtnLcdOFF.getId()) {
			if (paramView.getId() == this.mBtnLcdON.getId()) {
				setBrightness(this, 255);
			} else if (paramView.getId() == this.mBtnLcdOFF.getId()) {
				setBrightness(this, 0);
			}
		}

		if (paramView.getId() == this.mBtOk.getId()) {
			Utils.SetPreferences(this, localSharedPreferences, R.string.backlight_name, "success");
			finish();
		} else if (paramView.getId() == this.mBtFailed.getId()) {
			Utils.SetPreferences(this, localSharedPreferences, R.string.backlight_name, "failed");
			finish();
		}
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.backlight);

		this.mBtnLcdON = (Button) findViewById(R.id.Display_lcd_on);
		this.mBtnLcdOFF = (Button) findViewById(R.id.Display_lcd_off);

		this.mBtOk = (Button) findViewById(R.id.display_bt_ok);
		this.mBtFailed = (Button) findViewById(R.id.display_bt_failed);

		this.mBtnLcdON.setOnClickListener(this);
		this.mBtnLcdOFF.setOnClickListener(this);
		this.mBtOk.setOnClickListener(this);
		this.mBtFailed.setOnClickListener(this);

		SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
		this.mSp = localSharedPreferences;
	}

	/**
	 * 判断是否开启了自动亮度调节
	 */
	public static boolean isAutoBrightness(ContentResolver aContentResolver) {

		boolean automicBrightness = false;

		try {

			automicBrightness = Settings.System.getInt(aContentResolver,

			Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

		}

		catch (SettingNotFoundException e)

		{

			e.printStackTrace();

		}

		return automicBrightness;
	}

	/**
	 * 获取屏幕的亮度
	 */
	public static int getScreenBrightness(Activity activity) {

		int nowBrightnessValue = 0;

		ContentResolver resolver = activity.getContentResolver();

		try {

			nowBrightnessValue = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);

		}

		catch (Exception e) {

			e.printStackTrace();

		}

		return nowBrightnessValue;
	}

	/**
	 * 设置亮度
	 */
	public static void setBrightness(Activity activity, int brightness) {

		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();

		lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
		Log.d("lxy", "set  lp.screenBrightness == " + lp.screenBrightness);

		activity.getWindow().setAttributes(lp);
	}

	/**
	 * 停止自动亮度调节
	 */
	public static void stopAutoBrightness(Activity activity) {

		Settings.System.putInt(activity.getContentResolver(),

		Settings.System.SCREEN_BRIGHTNESS_MODE,

		Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
	}

	/**
	 * 开启亮度自动调节
	 * 
	 * @param activity
	 */
	public static void startAutoBrightness(Activity activity) {

		Settings.System.putInt(activity.getContentResolver(),

		Settings.System.SCREEN_BRIGHTNESS_MODE,

		Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);

	}

	/**
	 * 保存亮度设置状态
	 */
	public static void saveBrightness(ContentResolver resolver, int brightness) {

		Uri uri = Settings.System.getUriFor("screen_brightness");

		Settings.System.putInt(resolver, "screen_brightness", brightness);

		resolver.notifyChange(uri, null);
	}
}