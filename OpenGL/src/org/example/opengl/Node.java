package org.example.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLUtils;

import java.util.ArrayList;
import java.util.*;

class Node {
  public V3 pos=new V3();
  public List<Entity> objs=new ArrayList<Entity>();
  private float radius=0.74f;
   public Node(float x,float y,float z) {
   	   pos.x=x;
   	   pos.y=y;
   	   pos.z=z;
   }
   

   public void draw(GL10 gl) {
   	   gl.glPushMatrix();
  // 	   gl.glTranslatef(pos.x,pos.y,pos.z);
//	   float s=radius;
	//   gl.glScalef(s,s,s);
	   for(Entity obj:objs){
		   if(obj.rendered==true||obj.radius<=0)continue;
   	  	 obj.draw(gl);
		 obj.rendered=true;
		 }
   	   gl.glPopMatrix();
   }
	public boolean Add(Entity e){
		if(e.pos.s(pos).lengthsquared()-(radius*radius+e.radius*e.radius)<=0){
//			objs.remove(e);
			objs.add(e);
			return true;
		}
		return false;
	}
   
   
}

