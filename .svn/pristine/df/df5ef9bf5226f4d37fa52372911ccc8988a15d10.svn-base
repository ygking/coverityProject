package com.goertek.factorytest.sdcard;

import com.goertek.factorytest.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.goertek.factorytest.Utils;

public class SDCard extends Activity implements View.OnClickListener {
	private Button mBtFailed;
	private Button mBtOk;
	private TextView mInfo;
	private SharedPreferences mSp;

	public void SDCardSizeTest() {
		StringBuilder localStringBuilder = new StringBuilder();

		StatFs localStatFs = new StatFs("/mnt/sdcard");
		long l1 = localStatFs.getBlockCount();
		if (l1 == 0) {
			localStringBuilder.append(this.getString(R.string.sdcard_tips_failed));
		} else {
			long l2 = localStatFs.getBlockSize();
			long l3 = localStatFs.getAvailableBlocks();
			long l4 = l1 * l2 / 1024L / 1024L;
			long l5 = l3 * l2 / 1024L / 1024L;
			localStringBuilder.append(this.getString(R.string.sdcard_tips_success)).append("\n\n");
			localStringBuilder.append(this.getString(R.string.sdcard_totalsize)).append(l4).append("MB").append("\n\n");
			localStringBuilder.append(this.getString(R.string.sdcard_freesize)).append(l5).append("MB").append("\n\n");
		}
		this.mInfo.setText(localStringBuilder.toString());
	}

	public void onClick(View paramView) {
		SharedPreferences localSharedPreferences = this.mSp;
		if (paramView.getId() == this.mBtOk.getId()) {
			Utils.SetPreferences(this, localSharedPreferences, R.string.sdcard_name, "success");
			finish();
		} else {
			Utils.SetPreferences(this, localSharedPreferences, R.string.sdcard_name, "failed");
			finish();
		}
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.sdcard);
		SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
		this.mSp = localSharedPreferences;
		TextView localTextView = (TextView) findViewById(R.id.sdcard_info);
		this.mInfo = localTextView;
		this.mBtOk = (Button) findViewById(R.id.sdcard_bt_ok);
		this.mBtOk.setOnClickListener(this);
		this.mBtFailed = (Button) findViewById(R.id.sdcard_bt_failed);
		this.mBtFailed.setOnClickListener(this);
		SDCardSizeTest();
	}
}