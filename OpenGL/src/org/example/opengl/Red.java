package org.example.opengl;

public class Red extends Bacteria
{
@Override public Entity Spawn(){return new Red();}

public void Set(){
is=new V3(.95f,.3f,.5f);
render=new V3(.6f,0,.6f);
eats=new V3(0,1,0);
}
	public Red(){
		Set();
	}
	public Red(float x,float y,float z){
		Set();
		pos=new V3(x,y,z);
		pos.norm();
	}
}
