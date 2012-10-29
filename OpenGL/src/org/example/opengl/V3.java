package org.example.opengl;
import android.util.*;

class V3 {
	public float x=0,y=0,z=0;
	public V3() {   }
	public V3(V3 v){x=v.x;y=v.y;z=v.y;}
	public V3(float fx,float fy,float fz){
		x=fx;y=fy;z=fz;
	}
	public void norm(){
		float l=FloatMath.sqrt(x*x+y*y+z*z);
		x/=l;
		y/=l;
		z/=l;
	}
	public void normed(){
		V3 v=new V3(this);
		v.norm();
		return v;
	}
	
	
	/*God damn you java you stupid, fucking twat just allow operator overloading piece of rotton, old filth buried deep within the bowels of some dark, wet scum bucket!*/
	public float a(V3 r){
		V3 v=new V3(this);
		v.x+=r.x;v.y+=r.y;v.z+=r.z;
		return v;
	}
	
}

