package org.example.opengl;

import javax.microedition.khronos.opengles.*;

public class M9
{
	public V3 x=new V3(1,0,0);
	public V3 y=new V3(0,1,0);
	public V3 z=new V3(0,0,1);
	
	public void norm(){
		z.norm();
//		y.x=z.y;
//		y.y=-z.z;
//		y.z=z.x;
		x.asCross(z,y);
		y.asCross(x,z);
		
		x.norm();
		y.norm();
	}
	public void gl(GL10 gl){
//		gl.glMultMatrixf(new float[]{x.x,y.x,z.x,0,x.y,y.y,z.y,0,x.z,y.z,z.z,0,0,0,0,1},0);
		gl.glMultMatrixf(new float[]{x.x,x.y,x.z,0,y.x,y.y,y.z,0,z.x,z.y,z.z,0,0,0,0,1},0);
	}
}
