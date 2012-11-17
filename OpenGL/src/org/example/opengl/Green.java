package org.example.opengl;

public class Green extends Bacteria
{
	@Override public Entity Spawn(){return new Green();}
	public void Set(){
		is=new V3(0,1,0);
		render=new V3(.05f,.6f,.1f);
		eats=new V3(0,0,1);
	}
	public Green(){
		Set();
	}
	public Green(float x,float y,float z){
		Set();
		pos=new V3(x,y,z);
		pos.norm();
	}
}
