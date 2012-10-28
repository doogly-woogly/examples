package org.example.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLUtils;

import java.util.ArrayList;

class Node {
  public float[] pos=new float[3];
  public Entity obj=new GLSphere();
   public Node(float x,float y,float z) {
   	   pos[0]=x;
   	   pos[1]=y;
   	   pos[2]=z;
   }
   

   public void draw(GL10 gl) {
   	   gl.glPushMatrix();
   	   gl.glTranslatef(pos[0],pos[1],pos[2]);
	   gl.glScalef(0.1f,0.1f,0.1f);
   	   obj.draw(gl);
   	   gl.glPopMatrix();
   }
   
   
}

