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
  private float radius=0.17f;//3 .16<r<=.23
  							//4  .12<r<=.18
   public Node(float x,float y,float z) {
   	   pos.x=x;
   	   pos.y=y;
   	   pos.z=z;
	   switch(World.idivs){
		   case 2:radius=.37f;break;//.36 .39
		   case 3:radius=.21f;break;//.16 .23
		   case 4:radius=.18f;break;//.12 .18
	   }
   }
   

   public void draw(GL10 gl) {
	   for(Entity obj:objs){
		   if(obj.rendered==true||obj.radius<=0)continue;
   	  	 obj.draw(gl);
		 obj.rendered=true;
		 }
   }
	public boolean Add(Entity e){
		if(e.pos.s(this.pos).lengthsquared()<=(radius*radius+e.radius*e.radius)){
			objs.add(e);
			return true;
		}
		return false;
	}
}

