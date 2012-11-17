package org.example.opengl;

import java.util.*;
import javax.microedition.khronos.opengles.*;

public class Player
{
	private float rot=0;
	public Bacteria hold;
	public Player(){
		Random();
	}
	public void draw(GL10 gl){
		if(hold==null)return;
		gl.glLoadIdentity();
		gl.glClear(GL10.GL_DEPTH_BUFFER_BIT);
//		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glTranslatef(2f,-1f,-3f);
		gl.glRotatef(rot,0,1,0);
		rot+=Frame.time*5f;
		gl.glScalef(2,2,2);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, GLRenderer.mLightVector, 0);
		hold.draw(gl);

//		gl.glEnable(GL10.GL_DEPTH_TEST);
	}
	public void Place(V3 pos){
		hold.pos=pos;
		Bacteria.Append(hold);
		hold=null;
		
		Random();
	}
	public void Random(){
		float bonus=1f;
		float d=Frame.r.nextFloat();
		if(d<=bonus/3)
			hold=new Red();
		else if(d<=bonus/3*2)
			hold=new Green();
		else if(d<=bonus)
			hold=new Blue();
		else{
			hold=new Infection();
		}
		hold=new Blue();
		//	hold=new Bacteria();hold.Random();
	}
}
