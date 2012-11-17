package org.example.opengl;


import android.util.*;
import javax.microedition.khronos.opengles.*;

public class Blue extends Bacteria
{
	private static gleser g=null;
	@Override public Entity Spawn(){return new Blue();}
	private void Set(){
		is=new V3(0,0,1);
		render=new V3(.3f,.5f,.72f);
		eats=new V3(1,0,0);
	}
	public Blue(){
		Set();
	}
	public Blue(float x,float y,float z){
		Set();
		pos=new V3(x,y,z);
		pos.norm();
	}
/*	private void G(){
		g.gl.glPushMatrix();
		g.gl.glTranslatef(pos.x,pos.y,pos.z);

		M9 m=new M9();
		m.z.eq(pos);
//		m.y.eq(new V3(0,1,0));
		m.norm();
		m.gl(g.gl);

		g.gl.glScalef(radius,radius*2,radius);

//		float matAmbient[] = new float[] { 1f, 1f, 1f, 1f };
		float matDiffuse[] = new float[] { render.x, render.y, render.z, 1 };
		g.gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,matDiffuse, 0);
		g.gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,matDiffuse, 0);
	}
	@Override public void draw(GL10 gl){
		if(radius<=0)return;


		if(g!=null){
			G();
			g.Draw();
			g.gl.glPopMatrix();
			return;
		}//else
		g=new gleser(gl);
		G();
//		g.gl.glDisable(g.gl.GL_LIGHTING);
		final float height=1;
		final int rings=10;//(int)(radius/0.01f);
		final int veins=6;
		float inR=0;
		float outR=.1f;
		float ringHeight=height/rings;//radius/rings;
		final float curve=3;


		outR=0;
		float width=.3f;//radius*0.3f;
//		if(width<scale*0.6f)width=scale*.6f;
		for(int s=0;s<rings;s++){
			inR=outR;
			if(s<curve){
//				inR=(float)Math.sin((float)(s)/(curve*2)*Math.PI)*radius;
				outR=(float)Math.sin((float)((s+1))/(curve*2)*Math.PI)*width;
			}else if(s>=rings-curve){
				outR=(float)Math.sin((float)(rings-s-1)/(curve*2)*Math.PI)*width;
//				inR=(float)Math.sin((float)((rings-s+1))/(curve*2)*Math.PI)*radius;
			}else{
//				inR=radius;
				outR=width;
			}
			g.begin(g.gl.GL_TRIANGLE_STRIP);
			for(int a=0;a<=veins;a++){
				float r=(float)a/veins*(float)Math.PI*2;
				g.vertex(new V3(FloatMath.sin(r)*inR,s*ringHeight-height/2,FloatMath.cos(r)*inR));
				g.vertex(new V3(FloatMath.sin(r)*outR,(s+1)*ringHeight-height/2,FloatMath.cos(r)*outR));
			}
			g.end();
//			g.clear();
		}
		g.Draw();
	}//Draw*/
}
