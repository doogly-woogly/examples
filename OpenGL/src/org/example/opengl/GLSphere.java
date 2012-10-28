/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/

package org.example.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.util.ArrayList;
import java.util.List;

class GLSphere extends Entity{
   private final IntBuffer mVertexBuffer;
   
   
   private final IntBuffer mTextureBuffer;

   
   public GLSphere() {
      
      int one = 65536;
      int gold = (int)((double)one/1.61803398875);
      int half = one / 2;
      List<int> vertices = new ArrayList<int>();
      vertices.add(new V3(-gold,+one,0));
      vertices.add(new V3(+gold,+one,0));
      vertices.add(new V3(0,+gold,-one));
      vertices.add(new V3(0,+gold,+one));
      vertices.add(new V3(-one,0,-gold));
      vertices.add(new V3(-one,0,+gold));
      vertices.add(new V3(+one,0,-gold));
      vertices.add(new V3(+one,0,+gold));
      vertices.add(new V3(0,-gold,-one));
      vertices.add(new V3(0,-gold,+one));
      vertices.add(new V3(-gold,-one,0));
      vertices.add(new V3(+gold,-one,0));

      
      
      // Buffers to be passed to gl*Pointer() functions must be
      // direct, i.e., they must be placed on the native heap
      // where the garbage collector cannot move them.
      //
      // Buffers with multi-byte data types (e.g., short, int,
      // float) must have their byte order set to native order
      ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.size() * 4);
      vbb.order(ByteOrder.nativeOrder());
      mVertexBuffer = vbb.asIntBuffer();
      for(int i=0;i<vertices.size();i++){
      	      mVertexBuffer.put(vertices.get(i));
      }
      
      mVertexBuffer.position(0);
      
      
      
      // ...
      //ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
      //tbb.order(ByteOrder.nativeOrder());
      //mTextureBuffer = tbb.asIntBuffer();
      //mTextureBuffer.put(texCoords);
      //mTextureBuffer.position(0);
      
   }
   
@Override
   public void draw(GL10 gl) {
	   gl.glPushMatrix();
	   gl.glScalef(1f,1f,1f);
      gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);
      
      
      //gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, mTextureBuffer);
      gl.glColor4f(1, 1, 1, 1);
      gl.glNormal3f(0, 0, 1);
      gl.glDrawArrays(GL10.GL_POINTS, 0, 12);
	  gl.glPopMatrix();
      /*

      gl.glColor4f(1, 1, 1, 1);
      gl.glNormal3f(0, 0, 1);
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
      gl.glNormal3f(0, 0, -1);
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);

      gl.glColor4f(1, 1, 1, 1);
      gl.glNormal3f(-1, 0, 0);
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
      gl.glNormal3f(1, 0, 0);
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);

      gl.glColor4f(1, 1, 1, 1);
      gl.glNormal3f(0, 1, 0);
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
      gl.glNormal3f(0, -1, 0);
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
      */
   }
   
   
   static void loadTexture(GL10 gl, Context context, int resource) {
      Bitmap bmp = BitmapFactory.decodeResource(
            context.getResources(), resource);
      GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
      gl.glTexParameterx(GL10.GL_TEXTURE_2D,
            GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
      gl.glTexParameterx(GL10.GL_TEXTURE_2D,
            GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
      bmp.recycle();
   }
   
}

