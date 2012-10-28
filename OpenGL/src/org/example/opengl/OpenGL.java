/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package org.example.opengl;

import android.app.Activity;
import android.os.Bundle;
import android.opengl.*;
import android.view.*;
import android.hardware.*;
import android.location.*;
import java.util.*;
import android.content.*;
import android.util.*;
import android.widget.*;

public class OpenGL extends Activity {
	private MultisampleConfigChooser mConfigChooser;
   TouchGLView view;
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      view = new TouchGLView(this);
      setContentView(view);
   }

   @Override
   protected void onPause() {
       super.onPause();
       view.onPause();
   }

   @Override
   protected void onResume() {
       super.onResume();
       view.onResume();
   }
   
   	// Subclass GLSurfaceView to receive touch events. This class does nothing
	// but touch event handling.
	private class TouchGLView extends GLSurfaceView
	implements GestureDetector.OnGestureListener,
	ScaleGestureDetector.OnScaleGestureListener,
	SensorEventListener , LocationListener {
		private GLRenderer mRenderer;
		private GestureDetector mTapDetector;
		private ScaleGestureDetector mScaleDetector;
		private float mLastSpan = 0;
		private long mLastNonTapTouchEventTimeNS = 0;
		
		private  SensorManager sensorMgr = null;
		private  List<Sensor> sensors = null;
		private  Sensor sensorGrav = null;
		private  Sensor sensorMag = null;
		
		private LocationManager locationMgr;
		
		private Location currentLocation;
		
		private static final float grav[] = new float[3]; //Gravity (a.k.a accelerometer data)
		private static final float mag[] = new float[3]; //Magnetic 
		private static final float rotation[] = new float[16]; //Rotation matrix in Android format
		private static final float orientation[] = new float[3]; //azimuth, pitch,
		private static final int MIN_TIME = 30*1000;
		private static final int MIN_DISTANCE = 10;
		
		private GeomagneticField gmf;

		private static final boolean kUseMultisampling = false;
		TouchGLView(Context c) {
			super(c);
			// Use Android's built-in gesture detectors to detect
			// which touch event the user is doing.
			mTapDetector = new GestureDetector(c, this);
			mTapDetector.setIsLongpressEnabled(false);
			mScaleDetector = new ScaleGestureDetector(c, this);
			
			try {
				
				sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
				
				sensors = sensorMgr.getSensorList(Sensor.TYPE_ACCELEROMETER);
				if (sensors.size() > 0) sensorGrav = sensors.get(0);
				
				sensors = sensorMgr.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
				if (sensors.size() > 0) sensorMag = sensors.get(0);
				
				sensorMgr.registerListener(this, sensorGrav, SensorManager.SENSOR_DELAY_GAME);
				sensorMgr.registerListener(this, sensorMag, SensorManager.SENSOR_DELAY_GAME);
				
				//            locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				//            locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
				
				
				
				try {
					/*defaulting to our place*/
					Location hardFix = new Location("ATL");
					hardFix.setLatitude(39.931261);
					hardFix.setLongitude(-75.051267);
					hardFix.setAltitude(1);
					
					try {
						Location gps=locationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						Location network=locationMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if(gps!=null)
							currentLocation=(gps);
						else if (network!=null)
							currentLocation=(network);
						else
							currentLocation=(hardFix);
					} catch (Exception ex2) {
						currentLocation=(hardFix);
					}
					onLocationChanged(currentLocation);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} catch (Exception ex1) {
				try {
					if (sensorMgr != null) {
						sensorMgr.unregisterListener(this, sensorGrav);
						sensorMgr.unregisterListener(this, sensorMag);
						sensorMgr = null;
					}
					if (locationMgr != null) {
						locationMgr.removeUpdates(this);
						locationMgr = null;
					}
				} catch (Exception ex2) {
					ex2.printStackTrace();
				}
			}
			// Create an OpenGL ES 2.0 context.
			setEGLContextClientVersion(2);
			if (kUseMultisampling)setEGLConfigChooser(mConfigChooser = new MultisampleConfigChooser());
			setRenderer(mRenderer = new GLRenderer(c));
			grav[0]=0;
			grav[1]=0;
			grav[2]=0;
			mag[0]=0;
			mag[1]=0;
			mag[2]=0;
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			//synchronized (this) {
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				grav[0] = event.values[0];
				grav[1] = event.values[1];
				grav[2] = event.values[2];
			} else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				mag[0] = event.values[0];
				mag[1] = event.values[1];
				mag[2] = event.values[2];
			}
			
			//Get rotation matrix given the gravity and geomagnetic matrices
			//if((mag[0]==0&&mag[1]==0&&mag[2]==0)||(grav[0]==0&&grav[1]==0&&grav[2]==0))return;
			SensorManager.getRotationMatrix(rotation, null, grav, mag);
			//  SensorManager.getOrientation(rotation, orientation);
			//  floatBearing = orientation[0];
			
			//Convert from radians to degrees
			//  floatBearing = Math.toDegrees(floatBearing); //degrees east of true north (180 to -180)
			
			//Compensate for the difference between true north and magnetic north
			//    if (gmf!=null) floatBearing += gmf.getDeclination();
			
			//adjust to 0-360
			//    if (floatBearing<0) floatBearing+=360;
			
			//    GlobalData.setBearing((int)floatBearing);
			
			
			queueEvent(new Runnable() {
					public void run() {
						mRenderer.orientate(rotation);
			}});
			//}//syncro
		}
		
		@Override
		public boolean onTouchEvent(final MotionEvent e) {
			// Forward touch events to the gesture detectors.
			mScaleDetector.onTouchEvent(e);
			mTapDetector.onTouchEvent(e);
			return true;
		}
		
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			// Forward the scale event to the renderer.
			final float amount = detector.getCurrentSpan() - mLastSpan;
			queueEvent(new Runnable() {
					public void run() {
						// This Runnable will be executed on the render
						// thread.
						// In a real app, you'd want to divide this by
						// the display resolution first.
						mRenderer.zoom(amount);
			}});
			mLastSpan = detector.getCurrentSpan();
			mLastNonTapTouchEventTimeNS = System.nanoTime();
			return true;
		}
		
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			mLastSpan = detector.getCurrentSpan();
			return true;
		}
		
		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
		}
		
		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float vx, float vy) {
			return false;
		}
		
		@Override
		public void onLongPress(MotionEvent e) {
		}
		
		/**
		* {@inheritDoc}
		*/
		@Override
		public void onProviderDisabled(String provider) {
			//Ignore
		}
		
		/**
		* {@inheritDoc}
		*/
		@Override
		public void onProviderEnabled(String provider) {
			//Ignore
		}
		@Override
		public void onLocationChanged(Location location) {
			if (location==null) throw new NullPointerException();
			currentLocation=(location);
			gmf = new GeomagneticField((float) currentLocation.getLatitude(), 
				(float) currentLocation.getLongitude(),
				(float) currentLocation.getAltitude(), 
				System.currentTimeMillis());
		}
		/**
		* {@inheritDoc}
		*/
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			//Ignore
		}
		
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
			final float dx, final float dy) {
		// Forward the drag event to the renderer.
		queueEvent(new Runnable() { public void run() {mRenderer.drag(dx, dy);  }});
		mLastNonTapTouchEventTimeNS = System.nanoTime();
		return true;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
			}
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// Have a short dead time after rotating and zooming,
				// to make erratic taps less likely.
				final double kDeadTimeS = 0.3;
				if ((System.nanoTime() - mLastNonTapTouchEventTimeNS) / 1e9f < kDeadTimeS)
					return true;
				
				//screen size
				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				// Copy x/y into local variables, because |e| is changed and reused for
				// other views after this has been called.
				final float x = e.getX()/dm.widthPixels-0.5f;
				final float y = e.getY()/dm.heightPixels-0.5f;
				float tV[]=new float[4];
				float tR[]=new float[4];
				//point forwards
				tV[0]=-x;
				tV[1]=y;
				tV[2]=0.5f;//near clip
				//build direction vector
				float invRot[]=new float[16];
				Matrix.invertM(invRot,0,rotation,0);
				Matrix.multiplyMV(tR,0,invRot,0,tV,0);
				float l=FloatMath.sqrt(tR[0]*tR[0]+tR[1]*tR[1]+tR[2]*tR[2]);
				tR[0]/=l;
				tR[1]/=l;
				tR[2]/=l;
				
				tR[0]*=10;
				tR[1]*=10;
				tR[2]*=10;
				
				//queueEvent(new Runnable() { public void run() {mRenderer.addCell(tR);  }});
				Toast.makeText(getApplicationContext(), String.valueOf(tR[0])+' '+String.valueOf(tR[1])+' '+String.valueOf(tR[2]), Toast.LENGTH_SHORT).show();
				
				// Run something on the render thread...
				queueEvent(new Runnable(){
						public void run() {
							// Here you could call a method on the renderer that
							// checks which object has been tapped. A good way to
							// do this is color picking: Render your scene with one
							// unique color per entity (make sure to disable
							// multisampling, blending, and everything else that
							// changes colors), and then get the pixel color below
							// the tap (or in a small neighborhood if nothing is
							// below the tap). Map the color back to the object.
							// mRenderer.getEntityAt(x, y);
							
							// ...once that's done, post the result back to the UI
							// thread:
							getHandler().post(new Runnable() {
									@Override
									public void run() {
										// ...
							}});
				}});
				return true;
			}
	}

}
