package com.medikeen.patient;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.medikeen.R;

import org.acra.ACRA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CameraFragment extends Fragment{

	private static final String TAG = "CameraFragment";
	private SurfaceView mSurfaceView;
	private Camera mCamera;
	private View mProgressContainer;
	public static final String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GridView/";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
		
		@Override
		public void onShutter() {
			mProgressContainer.setVisibility(View.VISIBLE);
		}
	};
	
	private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			String filename = GridViewDemo_ImagePath + dateFormat.format(new Date()) + ".jpg";
			FileOutputStream os = null;
			boolean success = true;
			
			File file = new File(filename);
			
			try {
				os = new FileOutputStream(file);
				os.write(data);
			} catch (Exception e) {
				Log.e(TAG, "Error writing to file " + filename, e);
				success = false;
				
				ACRA.getErrorReporter().handleException(e);
				
			} finally {
				try {
					if(os != null)
						os.close();
				} catch (Exception e) {
					Log.e(TAG, "Error closing file " + filename, e);
					success = false;
					ACRA.getErrorReporter().handleException(e);
				}
			}
			
			if(success) {
				Log.i(TAG, "Jpeg saved at " + filename);
			}
						
			getActivity().setResult(Activity.RESULT_OK, null);
            getActivity().finish();
		}
	};
	
	@Override
	public void onResume() {
		super.onResume();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			mCamera = Camera.open(0);
		} else {
			mCamera = Camera.open();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if(mCamera!=null) {
			mCamera.release();
			mCamera = null;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_camera, parent, false);
		
		mProgressContainer = v.findViewById(R.id.camera_progressContainer);
		mProgressContainer.setVisibility(View.INVISIBLE);
		
		Button takePictureButton = (Button)v.findViewById(R.id.camera_takePictureButton);
		takePictureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mCamera!=null) {
					mCamera.takePicture(mShutterCallback, null, mJpegCallback);
				}
			}
		});
		
		mSurfaceView = (SurfaceView)  v.findViewById(R.id.camera_surfaceView);
		SurfaceHolder holder = mSurfaceView.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {

				if(mCamera!=null) {
					mCamera.stopPreview();
				}
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				try {
					if (mCamera!=null) {
						mCamera.setDisplayOrientation(90);
						mCamera.setPreviewDisplay(holder);
					}
				} catch (IOException exception) {
					Log.e(TAG, "Error setting up preview display", exception);
				}
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				if (mCamera==null) return;
				
				// The surface has changed size; update the camera preview size
				Camera.Parameters parameters = mCamera.getParameters();
				Size s = getOptimalPreviewSize(parameters.getSupportedPreviewSizes(), width, height);
				parameters.setPreviewSize(s.width, s.height);
				parameters.setPictureSize(s.width, s.height);
				mCamera.setParameters(parameters);
				try {
					mCamera.startPreview();
				} catch (Exception e) {
					Log.e(TAG, "Could not start preview", e);
					mCamera.release();
					mCamera = null;
				}
			}
		});
		
		return v;
		
	}
	
	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
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
	
	private Size getBestSupportedSize(List<Size> sizes, int width, int height) {
		Size bestSize = sizes.get(0);
		int largestArea = bestSize.width * bestSize.height;
		for(Size s: sizes) {
			int area = s.width * s.height;
			if(area > largestArea) {
				bestSize = s;
				largestArea = area;
			}
		}
		return bestSize;
	}
	
}
