package com.goertek.factorytest.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;
import java.util.Iterator;
import java.util.List;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import java.io.IOException;

public class CameraTest extends Activity
  implements OnClickListener
{
  private Button mBtFailed;
  private Button mBtFinish;
  private Button mBtOk;
  private Camera mCamera;
  SharedPreferences mSp;
  PictureCallback pictureCallback;
  private Parameters mCameraParam;
  Callback surfaceCallback;
  private SurfaceHolder surfaceHolder;
  private SurfaceView surfaceView;
  private String mISO = "AUTO";

  class CameraTest1
  implements PictureCallback
{
  public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera)
  {

  }
}
  
  class CameraTest2
  implements Callback
{
  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
  {
    startPreview();
  }

  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
	  openCamera();
      try {
          mCamera.setPreviewDisplay(paramSurfaceHolder);
      } catch (IOException exception) {
          closeCamera();
    
      }
  }

  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    if (mCamera == null)
      return;
    mCamera.stopPreview();
    mCamera.release();
    mCamera = null;
    surfaceHolder =null;
  }
}
  
  public CameraTest()
  {
    CameraTest1 local1 = new CameraTest1();
    this.pictureCallback = local1;
    CameraTest2 local2 = new CameraTest2();
    this.surfaceCallback = local2;
  }


  private Size getOptimalPreviewSize(List<Size> sizes, double targetRatio)
  {
      final double ASPECT_TOLERANCE = 0.05;
      if (sizes == null) return null;

      Size optimalSize = null;
      double minDiff = Double.MAX_VALUE;

      // Because of bugs of overlay and layout, we sometimes will try to
      // layout the viewfinder in the portrait orientation and thus get the
      // wrong size of mSurfaceView. When we change the preview size, the
      // new overlay will be created before the old one closed, which causes
      // an exception. For now, just get the screen size

      Display display = getWindowManager().getDefaultDisplay();
      int targetHeight = Math.min(display.getHeight(), display.getWidth());

      if (targetHeight <= 0) {
          // We don't know the size of SurefaceView, use screen height
          WindowManager windowManager = (WindowManager)
                  getSystemService(Context.WINDOW_SERVICE);
          targetHeight = windowManager.getDefaultDisplay().getHeight();
      }

      // try to find a size larger but closet to the desired preview size
      for (Size size : sizes) {
          if (targetHeight > size.height) continue;
          
          double ratio = (double) size.width / size.height;
          if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
          if (Math.abs(size.height - targetHeight) < minDiff) {
              optimalSize = size;
              minDiff = Math.abs(size.height - targetHeight);
          }
      }

      // not found, apply origional policy.
      if (optimalSize == null) {

          // Try to find an size match aspect ratio and size
          for (Size size : sizes) {
              double ratio = (double) size.width / size.height;
              if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
              if (Math.abs(size.height - targetHeight) < minDiff) {
                  optimalSize = size;
                  minDiff = Math.abs(size.height - targetHeight);
              }
          }
      }

      // Cannot find the one match the aspect ratio, ignore the requirement
      if (optimalSize == null) {
          //Log.v(TAG, "No preview size match the aspect ratio");
          minDiff = Double.MAX_VALUE;
          for (Size size : sizes) {
              if (Math.abs(size.height - targetHeight) < minDiff) {
                  optimalSize = size;
                  minDiff = Math.abs(size.height - targetHeight);
              }
          }
      }
      return optimalSize;
  }

  private void setupViews()
  {
    SurfaceView localSurfaceView = (SurfaceView)findViewById(R.id.camera_view);
    this.surfaceView = localSurfaceView;
    SurfaceHolder localSurfaceHolder1 = this.surfaceView.getHolder();
    this.surfaceHolder = localSurfaceHolder1;
    SurfaceHolder localSurfaceHolder2 = this.surfaceHolder;
    Callback localCallback = this.surfaceCallback;
    localSurfaceHolder2.addCallback(localCallback);
    this.surfaceHolder.setType(3);
    Button localButton1 = (Button)findViewById(R.id.camera_take);
    this.mBtFinish = localButton1;
    this.mBtFinish.setOnClickListener(this);
    Button localButton2 = (Button)findViewById(R.id.camera_btok);
    this.mBtOk = localButton2;
    this.mBtOk.setOnClickListener(this);
    Button localButton3 = (Button)findViewById(R.id.camera_btfailed);
    this.mBtFailed = localButton3;
    this.mBtFailed.setOnClickListener(this);
  }

  private void takePic()
  {
    this.mBtFinish.setEnabled(false);
    Camera localCamera = this.mCamera;
    PictureCallback localPictureCallback = this.pictureCallback;
    localCamera.takePicture(null, null, localPictureCallback);
  }

  public void onClick(View paramView)
  {
    int i = R.string.camera_name;
    int j = paramView.getId();
    int k = this.mBtFinish.getId();
    if (j == k)
      takePic();
  
      
      int l = paramView.getId();
      int i1 = this.mBtOk.getId();
      if (l == i1)
      {
        Context localContext1 = getApplicationContext();
        SharedPreferences localSharedPreferences1 = this.mSp;
        Utils.SetPreferences(localContext1, localSharedPreferences1, i, "success");
        finish();
      }
      int i2 = paramView.getId();
      int i3 = this.mBtFailed.getId();
      if (i2 == i3)
      {
       
      Context localContext2 = getApplicationContext();
      SharedPreferences localSharedPreferences2 = this.mSp;
      Utils.SetPreferences(localContext2, localSharedPreferences2, i, "failed");
      finish();
     }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    getWindow().setFlags(1024, 1024);
    setContentView(R.layout.camera);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
    setupViews();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
	    int i = 27;
	    
	    if (paramInt == i)
	    {
	    	takePic();
	    	return true;
	    }
	    if (paramInt != i)
	    {
	     
	    }
	    	    	   
	    return  super.onKeyDown(paramInt, paramKeyEvent);
	  }
  
  private void openCamera() 
  {
      if (mCamera == null) {
          mCamera = Camera.open();
          mCameraParam = mCamera.getParameters();
          mCameraParam.setFlashMode(Parameters.FLASH_MODE_ON);
          //Added by Shaoying Han 2011-04-25
          Intent intent = getIntent();
          try {
              mISO = intent.getStringExtra("ISO");
              if (TextUtils.isEmpty(mISO)) {
                  mISO = "AUTO";
              }

          } catch (Exception e) {
              mISO = "AUTO";

          }
          if (null != mCameraParam && null != mCamera)
          {
             // mCameraParam.setISOSpeedEng(mISO);
              mCamera.setParameters(mCameraParam);
          }

	  
      }

  }

  private void closeCamera() {
      if (null != mCamera) {
          mCamera.release();
          mCamera = null;
      }
  }

  private void startPreview() {
      if (null != mCamera) {
          mCameraParam = mCamera.getParameters();

          Size size = mCameraParam.getPictureSize();
         // PreviewFrameLayout frameLayout = (PreviewFrameLayout) findViewById(R.id.frame_layout);
         // frameLayout.setAspectRatio((double) size.width / size.height);

          List<Size> sizes = mCameraParam.getSupportedPreviewSizes();
          Size optimalSize = null;
          if (size != null && size.height != 0)
              optimalSize = getOptimalPreviewSize(sizes, (double) size.width / size.height);
          if (optimalSize != null) {
              mCameraParam.setPreviewSize(optimalSize.width, optimalSize.height);
          }
	 // mCamera.setDisplayOrientation(180);
          // end
          // mCameraParam.setPreviewSize(mPrvW, mPrvH);
          mCameraParam.set("fps-mode", 0); // Frame rate is normal
          mCameraParam.set("cam-mode", 0); // Cam mode is preview
          mCamera.setParameters(mCameraParam);

          }

          mCamera.startPreview();
	
      } 
      
  }
