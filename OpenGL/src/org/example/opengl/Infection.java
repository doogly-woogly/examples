package org.example.opengl;

import java.util.*;
import javax.microedition.khronos.opengles.*;

public class Infection extends Bacteria
{
	protected static float growth=Bacteria.growth*.6f;
	protected static float start=(Bacteria.scale-Bacteria.start)*.1f+Bacteria.start;
	
	public Infection(){
		Random();
	}
	public void Random(){
//		is=new V3(1.6f,1,0);
//		is=new V3(0,0,1);
		float l=1;
		switch(Frame.r.nextInt(3)){
		case 0:	eats=new V3(l,0,0);break;//.randPos();
		case 1:eats=new V3(0,l,0);break;
		case 2:eats=new V3(0,0,l);break;
		}
/*		is.y=eats.x;
		is.z=eats.y;
		is.x=eats.z;*/
		is.x=eats.y;
		is.y=eats.z;
		is.z=eats.x;
		is.me(.6f);
		render.eq(is.m(.1f).a(.01f));
		radius=start;
	}
	@Override
	public Bacteria Spawn(){
		return new Infection();
	}
	@Override
	public void Eat(Bacteria bb,float eat){
		float e=eat*this.digestion*Frame.time;
		bb.radius-=e;
//		this.radius+=e*eatEff;
	}
//	@Override
//	public void Crush(Entity bb,float dist,V3 d,float eat){}
	
//	@Overrive
	public void Infect(Entity bb,collision c){
		//super.Crush(bb,dist,d,eat);
		if(bb.getClass()==getClass())return;
		Bacteria bac=((Bacteria)bb);
//		radius-=Frame.time*digestion*eat;
		if(c.eat<=0)return;
		if(bb.radius>this.radius)return;
//		Eat(bac,eat);
	for(int ii=0;ii<1;ii++){	
			Bacteria i=Spawn();
			i.eq(this);
			i.radius=start;//this.start;
			i.pos.eq(((Bacteria)bb).pos);
	//		i.pos.x+=r.nextFloat();
			Bacteria.Append(i);
	}
			((Bacteria)bb).radius=-1;
//			((Bacteria)bb).render.de(1.1f);
	}
	
	@Override
	public void Touch(Entity b,collision c){
		super.Touch(b,c);
		Infect(((Bacteria)b),c);
	}
	@Override
	public boolean Calc(){
		if(this.radius<=this.start*.72f){
			this.radius-=this.growth*1.02f*Frame.time;
		}
		this.age+=Frame.time;
		if(this.age>=2f){
			World.Add(this);
			this.age=0;
		}
		Random r=new Random();
		this.radius+=r.nextFloat()*this.growth*Frame.time;
		if(this.radius>=this.scale){
			Divide();
		}
		return true;
	}

	@Override
	public void draw(GL10 gl){
		gl.glPushMatrix();
		gl.glTranslatef(pos.x,pos.y,pos.z);
		float sc=this.radius+this.enlarge;
		gl.glScalef(sc,sc,sc);
		float matAmbient[] = new float[] { 0,0,0,0 };
		float matDiffuse[] = new float[] { render.x, render.y, render.z, 1 };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,matAmbient, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,matDiffuse, 0);

//		GLSphere.draw(gl);
		//		gl.glPushMatrix();
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		if (World.sphereBac.mNormalBuffer != null) {
			// Enabled the normal buffer for writing and to be used during rendering.
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

			// Specifies the location and data format of an array of normals to use when rendering.
			gl.glNormalPointer(GL10.GL_FLOAT, 0, World.sphereBac.mNormalBuffer);
		}
		//	gl.glEnableClientState(GL10.GL_INDEN_ARRAY);
//		gl.glScalef(1f,1f,1f);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, World.sphereBac.mVertexBuffer);
		//	gl.glIndexPointer(3,GL10.GL_SHORT,0,mIndexBuffer);

//		gl.glNormal3f(0, 0, 1);
		gl.glDrawElements(GL10.GL_TRIANGLES, 60,GL10.GL_UNSIGNED_SHORT,World.sphere.mIndexBuffer);
//		matAmbient = new float[] { 1f, 1f, 1f, 1f };
		matDiffuse = new float[] { eats.x, eats.y, eats.z, 1 };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,matDiffuse, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,matDiffuse, 0);
		gl.glPointSize(2);
		gl.glDrawArrays(GL10.GL_POINTS,0,World.sphereBac.vertices.size());
//		gl.glPopMatrix();
		
		gl.glPopMatrix();//*/
//		GLSphere s=(GLSphere)this;
//		s.draw(gl);
//		super.draw(gl);

	}
}
