package org.example.opengl;


import javax.microedition.khronos.opengles.GL10;


class Entity {
public float radius=0.1f;
public V3 pos=new V3();
public boolean rendered=false;
   public Entity() {
   }
   

   public void draw(GL10 gl) {
   }
   public boolean Process(float fTime){
	   rendered=false;
	   return true;
   }
   public void Collide(Entity e,float fTime){}
   
   
}

