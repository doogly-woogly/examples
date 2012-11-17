package org.example.opengl;


import javax.microedition.khronos.opengles.GL10;


class Entity {
	public Entity Spawn(){return new Entity();}

public float radius=0.1f;
public V3 pos=new V3();
public boolean rendered=false;
protected V3 dir=new V3();


   public Entity() {
   }
   

   public void draw(GL10 gl) {
   }
   public boolean Process(float fTime){
	   rendered=false;
	   return true;
   }
   public void Collide(Entity e,float fTime){}
  	public void Feel(Entity e,collision c){}
	public void Touch(Entity e,collision c){}
   public void Crush(Entity e,collision c){}
   
   
}

