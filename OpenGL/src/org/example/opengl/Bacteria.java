package org.example.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

class Bacteria extends GLSphere{
	public static List<Bacteria> bacterium=new ArrayList<Bacteria>;
	private V3 pos;
	public V3 eats=new V3();
	public V3 is=new V3();
	private float size=1.0f;
	private float age=0;
	
	public Bacteria(float ix,float iy,float iz,float ex,float ey,float ez,float x,float y,float z){
		is=new V3(ix,iy,iz);
		eats=new V3(ex,ey,ez);
		pos=new V3(x,y,z);
		pos.norm();
	}
	
	public static void Process(float fTime){
		if(size<=0)bacterium.remove(this);
		age+=fTime;
	}
	public boolean Collided(Bacteria bb){
		return bb.pos.s(pos).lengthsquared()>size*size+bb.size*bb.size;
	}
	public void Collide(Bacteria bb,float fTime){
		if(size<=0||bb.size<=0)return;
		float dist=Distance(bb);
		if(dist<0){
			//test eat
			float eat=bb.is.s(eats).sum();
			//test eaten
			float eaten=is.s(bb.eats).sum();
			eat-=eaten;
			if(eat>0){
				Eat(bb,fTime);
			}else if(eat<0){
				bb.Eat(this,fTime);
			}
		}
	}
	public void Eat(Bacteria bb,fTime){
		bb.size-=eat*fTime;
		size+=eat*fTime;
		if(bb.size<=0)bb.Die();
	}
	public void Die(){
		size=0;
		//remove from bacterium list
	}
	public float Distance(Bacteria bb){
		return bb.pos.s(pos).length()-(size+bb.size);
	}
	
	@Override
	public void draw(GL10 gl){
		gl.glColor4f(is.x, is.y, is.z, 1);
		gl.glPushMatrix();
		gl.glTranslatef(pos.x,pos.y,pos.z);
		super.draw(gl);
		gl.glPopMatrix();
	}
}

