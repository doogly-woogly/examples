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
	public V3 normed(){
		V3 v=new V3(this);
		v.norm();
		return v;
	}
	
	
	/*God damn you java you stupid, fucking twat just allow operator overloading piece of rotton, old filth buried deep within the bowels of some dark, wet scum bucket!*/
	public V3 a(V3 r){
		V3 v=new V3(this);
		v.x+=r.x;v.y+=r.y;v.z+=r.z;
		return v;
	}
	
	public void ae(V3 r){
		x+=r.x;y+=r.y;z+=r.z;
	}
	
	public V3 s(V3 r){
		V3 v=new V3(this);
		v.x-=r.x;v.y-=r.y;v.z-=r.z;
		return v;
	}
	
	public V3 m(V3 r){
		V3 v=new V3(this);
		v.x*=r.x;v.y*=r.y;v.z*=r.z;
		return v;
	}
	
	public V3 m(float r){
		V3 v=new V3(this);
		v.x*=r;v.y*=r;v.z*=r;
		return v;
	}
	
	public V3 d(V3 r){
		V3 v=new V3(this);
		v.x/=r.x;v.y/=r.y;v.z/=r.z;
		return v;
	}
	
	public V3 d(float r){
		V3 v=new V3(this);
		v.x/=r;v.y/=r;v.z/=r;
		return v;
	}
	
	public void eq(V3 r){
		x=r.x;y=r.y;z=r.z;
	}
	
}

