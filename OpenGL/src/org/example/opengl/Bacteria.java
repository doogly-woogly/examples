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
	private static float scale=0.25f;
	private static float start=0.6f;
	private static float growth=0.25f;
	private static float digestion=0.3f;
	private static float follow=0.01f;
	private static float push=0.1f;
	private static float eatEff=.7f;
	private V3 dir=new V3();
	public V3 eats=new V3();
	public V3 is=new V3();
	private float radius=start;
	private float age=0;

	
	public Bacteria(float ix,float iy,float iz,float ex,float ey,float ez,float x,float y,float z){
		is=new V3(ix,iy,iz);
		eats=new V3(ex,ey,ez);
		pos=new V3(x,y,z);
		pos.norm();
		World.Add(this);
	}
	public Bacteria(V3 i,V3 e,V3 p){
		is=new V3(i.x,i.y,i.z);
		eats=new V3(e.x,e.y,e.z);
		pos=new V3(p.x,p.y,p.z);
		pos.norm();
		World.Add(this);
	}
	@Override
	public boolean Process(float fTime){
		super.Process(fTime);
		if(radius<=0){
			bacterium.remove(this);
			return false;
		}else if(radius<=0.1f){
			radius-=growth*Frame.time*1.05f;
		}
		age+=fTime;
		if(age>=1f){
			World.Add(this);
			age=0;
		}
		Random r=new Random();
		radius+=r.nextFloat()*growth*fTime;
		if(radius>=1){
			Divide();
		}
		pos.ae(dir.m(fTime));
		dir.eq(dir.m(0.9f));
		pos.norm();
		return true;
	}
	public void Divide(){
		radius=start;
		Random r=new Random();
		float rx=(r.nextFloat()-0.5f)*0.04f;
		float ry=(r.nextFloat()-0.5f)*0.04f;
		float rz=(r.nextFloat()-0.5f)*0.04f;
		Bacteria n=new Bacteria(is, eats, new V3(pos.x+rx,pos.y+ry,pos.z+rz));
		dir.eq(dir.m(0.9f));
		n.dir.eq(dir.m(0.9f));
		World.Add(this);
		World.Add(n);
		Bacteria.bacterium.add(n);
	
	}
	public boolean Collided(Bacteria bb){
		return bb.pos.s(pos).lengthsquared()>radius*radius+bb.radius*bb.radius;
	}
	@Override
	public void Collide(Entity bb,float fTime){
		if(radius<=0||bb.radius<=0)return;
		if(bb.getClass() != getClass())return;

		
		float dist=Distance((Bacteria)bb);
		if(dist<0.005f){
			//test eat
			float eat=((Bacteria)bb).is.m(((Bacteria)bb).radius).m(eats.m(radius)).sum();
			//bb.is.x*eats.x+bb.is.y*eats.y+bb.is.z*eats.z;
		
			//test eaten
			float eaten=is.m(radius).m( ((Bacteria)bb).eats.m(((Bacteria)bb).radius) ).sum();
			eat-=eaten;
			eat*=0.3f;
			if(eat>0){
				Eat((Bacteria)bb,eat);
			}else if(eat<0){
				((Bacteria)bb).Eat(this,eat);
			
			}
			}
			if(dist<=0){
			//	bb.size=10;
			
				V3 d=pos.s(((Bacteria)bb).pos);
				d.norm();
				d.eq(d.m(-push*Frame.time));
				((Bacteria)bb).dir.ae(d);
				d.eq(d.m(-1));
				dir.ae(d);
				radius-=growth*Frame.time/2;
				((Bacteria)bb).radius-=growth*Frame.time/2;
				}
		
	}
	public void Eat(Bacteria bb,float eat){
/*		bb.is=new V3(is);
		bb.eats=new V3(eats);
		bb.size=start;*/
		bb.radius-=digestion*Frame.time;
		radius+=digestion*Frame.time*eatEff;
		V3 d=new V3();
		d.eq(pos.s(bb.pos));
		d.norm();
		dir.ae(d.m(Frame.time*-follow));
		if(bb.radius<=0)bb.Die();
	}
	public void Die(){
		radius=0;
		//remove from bacterium list
	}
	public float Distance(Bacteria bb){
		return bb.pos.s(pos).length()-(radius*scale+bb.radius*scale);
	}
	
	@Override
	public void draw(GL10 gl){
//		if(size<=0)return;
//		gl.glColor4f(is.x, is.y, is.z, 1);
		gl.glPushMatrix();
		gl.glTranslatef(pos.x,pos.y,pos.z);
		float s=radius*.6f+.4f+.2f;
//		if(s>0.85f)s=0.85f;
		float sc=scale*s;
		gl.glScalef(sc,sc,sc);
		float matAmbient[] = new float[] { 0.2f, 0.2f, .2f, 1f };
		float o=0.3f;
		float matDiffuse[] = new float[] { is.x*o, is.y*o, is.z*o, 1 };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,matAmbient, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,matDiffuse, 0);

		super.draw(gl);
		
		gl.glPopMatrix();
//		matAmbient = new float[] { 1f, 1f, 1f, 1f };
//		matDiffuse = new float[] { 1f, 1f, 1f, 1f };
//		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,matAmbient, 0);
//		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,matDiffuse, 0);
	}
}

