/***
* Excerpted from "Hello, Android!",
* published by The Pragmatic Bookshelf.
* Copyrights apply to this code. It may not be used to create training material, 
* courses, books, articles, and the like. Contact us if you are in doubt.
* We make no guarantees that this code is fit for any purpose. 
* Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package org.example.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.opengl.Matrix;

class GLRenderer implements GLSurfaceView.Renderer {
	private static final String TAG = "GLRenderer";
	private final Context context;
	
	
	private final World world = new World();
	
	private long startTime;
	private long fpsStartTime;
	private long numFrames;
	
	private float mFOV=45.0f,mAspect=1.0f;
        private float[] mProjectionMatrix = new float[16];
        private float[] mViewMatrix = new float[16];
        private float[] mViewProjectionMatrix = new float[16];
	
	
	
	GLRenderer(Context context) {
		this.context = context;
	}

	public void drag(float dx, float dy)
	{
		// TODO: Implement this method
	}

	public void zoom(float amount)
	{
		// TODO: Implement this method
	}

	public void orientate(float[] rotation)
	{
			mViewMatrix[0]=m[0];
			mViewMatrix[1]=m[1];
			mViewMatrix[2]=m[2];
			mViewMatrix[3]=m[3];
			
			mViewMatrix[4]=m[4];
			mViewMatrix[5]=m[5];
			mViewMatrix[6]=m[6];
			mViewMatrix[7]=m[7];
			
			mViewMatrix[8]=m[8];
			mViewMatrix[9]=m[9];
			mViewMatrix[10]=m[10];
			mViewMatrix[11]=m[11];
			
			mViewMatrix[12]=m[12];
			mViewMatrix[13]=m[13];
			mViewMatrix[14]=m[14];
			mViewMatrix[15]=m[15];
			
		//	updateMatrices();
		}
	}
	
	
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		// ...
		
		
		
		boolean SEE_THRU = true;
		
		
		startTime = System.currentTimeMillis();
		fpsStartTime = startTime;
		numFrames = 0;
		
		
		
		// Define the lighting
		float lightAmbient[] = new float[] { 0.2f, 0.2f, 0.2f, 1 };
		float lightDiffuse[] = new float[] { 1, 1, 1, 1 };
		float[] lightPos = new float[] { 1, 1, 1, 1 };
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPos, 0);
		
		
		
		// What is the cube made of?
		float matAmbient[] = new float[] { 1, 1, 1, 1 };
		float matDiffuse[] = new float[] { 1, 1, 1, 1 };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,
			matAmbient, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
			matDiffuse, 0);
		
		
		
		// Set up any OpenGL options we need
		gl.glEnable(GL10.GL_DEPTH_TEST); 
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		// Optional: disable dither to boost performance
		// gl.glDisable(GL10.GL_DITHER);
		
		
		
		// ...
		if (SEE_THRU) {
			gl.glDisable(GL10.GL_DEPTH_TEST);
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		}
		
		
		// Enable textures
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_CULL_FACE);
		
		
		// Load the cube's texture from a bitmap
		world.Load(gl,context);
	}
	
	
	
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		// ...
		
		
		// Define the view frustum
		Matrix.setIdentityM(mViewMatrix, 0);
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		float mAspect = (float) width / height;
		perspectiveM(mProjectionMatrix, (float)Math.toRadians(mFOV), mAspect, 0.1f, 200.f);
//		Matrix.multiplyMM(mViewProjectionMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
		gl.glLoadMatrixf(mProjectionMatrix,0);
//		updateMatrices(gl);
//		GLU.gluPerspective(gl, 45.0f, mAspect, 0.1f, 2f); 
		
	}
	
	private void updateMatrices(GL10 gl) {   }
	
	
	
	public void onDrawFrame(GL10 gl) {		
		// Clear the screen to black
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT	| GL10.GL_DEPTH_BUFFER_BIT);
		
		// Position model so we can see it
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		Matrix.setIdentityM(mViewMatrix, 0);

//		gl.glLoadIdentity();
		Matrix.setIdentityM(mViewMatrix, 0);
		long elapsed = System.currentTimeMillis() - startTime;
//		gl.glTranslatef(0, 0, 0);
		Matrix.translateM(mViewMatrix, 0, 0, 0, -5);
		 
//		gl.glRotatef(elapsed * (30f / 1000f), 0, 1, 0);
//		gl.glRotatef(elapsed * (15f / 1000f), 1, 0, 0);
		 Matrix.rotateM(mViewMatrix, 0, elapsed*(30f/1000f), 0, 1, 0);
		 Matrix.rotateM(mViewMatrix, 0, elapsed*(15f/1000f), 1, 0, 0);
		gl.glLoadMatrixf(mViewMatrix,0);
		
		// Other drawing commands go here...
	
		
		// Draw the model
		world.draw(gl,mProjectionMatrix,mViewMatrix);
		
		
		
		// Keep track of number of frames drawn
		numFrames++;
		long fpsElapsed = System.currentTimeMillis() - fpsStartTime;
		if (fpsElapsed > 5 * 1000) { // every 5 seconds
			float fps = (numFrames * 1000.0F) / fpsElapsed;
			Log.d(TAG, "Frames per second: " + fps + " (" + numFrames
				+ " frames in " + fpsElapsed + " ms)");
			fpsStartTime = System.currentTimeMillis();
			numFrames = 0;
		}
		
		
		
		
		
	}
	
	// Like gluPerspective(), but writes the output to a Matrix.
        private void perspectiveM(float[] m, float angle, float aspect, float near, float far) {
        	float f = (float)Math.tan(0.5 * (Math.PI - angle));
        	float range = near - far;
        	
        	m[0] = f / aspect;
        	m[1] = 0;
        	m[2] = 0;
        	m[3] = 0;
        	
        	m[4] = 0;
        	m[5] = f;
        	m[6] = 0;
        	m[7] = 0;
        	
        	m[8] = 0;
        	m[9] = 0; 
        	m[10] = far / range;
        	m[11] = -1;
        	
        	m[12] = 0;
        	m[13] = 0;
        	m[14] = near * far / range;
        	m[15] = 0;
        } 
        
}
