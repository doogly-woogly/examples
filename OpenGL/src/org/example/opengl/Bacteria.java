package org.example.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import java.util.*;

class Bacteria extends GLSphere{
	public static List<Bacteria> bacterium=new ArrayList<Bacteria>();
	private V3 pos;
	public V3 eats=new V3();
	public V3 is=new V3();
	private float size=1.0f;
	private float age=0;
	private static float scale=0.05f;
	
	public Bacteria(float ix,float iy,float iz,float ex,float ey,float ez,float x,float y,float z){
		is=new V3(ix,iy,iz);
		eats=new V3(ex,ey,ez);
		pos=new V3(x,y,z);
		pos.norm();
	}
	public Bacteria(V3 i,V3 e,V3 p){
		is=new V3(i.x,i.y,i.z);
		eats=new V3(e.x,e.y,e.z);
		pos=new V3(p.x,p.y,p.z);
		pos.norm();
	}
	
	public void Process(float fTime){
		if(size<=0){//bacterium.remove(this);
		return;}
		age+=fTime;
		Random r=new Random();
		size+=r.nextFloat()*scale;
		if(size>=2){
			Divide();
		}
	}
	public void Divide(){
		size=1;
		Random r=new Random();
		float rx=(r.nextFloat()-0.5f)*0.1f;
		float ry=(r.nextFloat()-0.5f)*0.1f;
		float rz=(r.nextFloat()-0.5f)*0.1f;
		Bacteria.bacterium.add(new Bacteria(is, eats, new V3(pos.x+rx,pos.y+ry,pos.z+rz)));
	}
	public boolean Collided(Bacteria bb){
		return bb.pos.s(pos).lengthsquared()>size*size+bb.size*bb.size;
	}
	public void Collide(Bacteria bb,float fTime){
		if(size<=0||bb.size<=0)return;
		float dist=Distance(bb);
		if(dist<0){
			//test eat
			float eat=is.m(bb.eats).sum();
			//test eaten
			float eaten=is.m(eats).sum();
			eat-=eaten;
			eat*=5;
			if(eat>0){
				Eat(bb,eat);
			}else if(eat<0){
				bb.Eat(this,eat);
			}
				V3 dir=pos.s(bb.pos);
				dir.norm();
				dir.eq(dir.m(-0.3f));
				bb.pos.ae(dir);
				dir.eq(dir.m(-1));
				pos.ae(dir);
			
		}
	}
	public void Eat(Bacteria bb,float eat){
		bb.size-=eat;
		size+=eat;
		if(bb.size<=0)bb.Die();
	}
	public void Die(){
		size=0;
		//remove from bacterium list
	}
	public float Distance(Bacteria bb){
		return bb.pos.s(pos).length()-(size*scale+bb.size*scale);
	}
	
	@Override
	public void draw(GL10 gl){
		gl.glColor4f(is.x, is.y, is.z, 1);
		gl.glPushMatrix();
		gl.glTranslatef(pos.x,pos.y,pos.z);
		float sc=scale*size;
		gl.glScalef(sc,sc,sc);
		float matAmbient[] = new float[] { 0.1f, 0.1f, .1f, 1f };
		float matDiffuse[] = new float[] { is.x, is.y, is.z, 1 };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,matAmbient, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,matDiffuse, 0);
		super.draw(gl);
		gl.glPopMatrix();
		matAmbient = new float[] { 1f, 1f, 1f, 1f };
		matDiffuse = new float[] { 1f, 1f, 1f, 1f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,matAmbient, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,matDiffuse, 0);
	}
}

