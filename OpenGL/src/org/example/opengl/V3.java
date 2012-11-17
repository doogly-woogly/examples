package org.example.opengl;
import android.util.*;
import java.util.*;
import java.util.zip.*;

class V3 {
	public float x=0,y=0,z=0;
	public V3() {   }
	public V3(V3 v){x=v.x;y=v.y;z=v.z;}
	public V3(float fx,float fy,float fz){
		x=fx;y=fy;z=fz;
	}
	public void norm(){
		float l=FloatMath.sqrt(x*x+y*y+z*z);
		x/=l;
		y/=l;
		z/=l;
	}
	public void asCross(V3 a,V3 b){
		x=a.y*b.z - a.z*b.y;
		y=a.z*b.x - a.x*b.z;
		z=a.x*b.y - a.y*b.x;
	}
//	vZ[0]=vX[1]*vY[2] - vX[2]*vY[1];
//	vZ[1]=vX[2]*vY[0] - vX[0]*vY[2];
//	vZ[2]=vX[0]*vY[1] - vX[1]*vY[0];
	public void randShift(float d){
		x+=(Frame.r.nextFloat()-.5f)*d;
		y+=(Frame.r.nextFloat()-.5f)*d;
		z+=(Frame.r.nextFloat()-.5f)*d;
	}
	public void rand(){
		Random r=new Random();
		x=r.nextFloat()-.5f;
		y=r.nextFloat()-.5f;
		z=r.nextFloat()-.5f;
		norm();
	}
	public void randPos(){
		Random r=new Random();
		x=r.nextFloat();
		y=r.nextFloat();
		z=r.nextFloat();
		norm();
	}
	public float lengthsquared(){
		return (x*x+y*y+z*z);
	}
	public float length(){
		return FloatMath.sqrt(x*x+y*y+z*z);
	}
	public V3 normed(){
		V3 v=new V3(this);
		v.norm();
		return v;
	}
	public float sum(){
		return x+y+z;
	}
	
	
	/*God damn you java you stupid, fucking twat just allow operator overloading piece of rotton, old filth buried deep within the bowels of some dark, wet scum bucket!*/
	public V3 a(V3 r){
		V3 v=new V3(this);
		v.x+=r.x;
		v.y+=r.y;
		v.z+=r.z;
		return v;
	}
	public V3 a(float r){
		V3 v=new V3();
		v.x=x+r;
		v.y=y+r;
		v.z=z+r;
		return v;
	}
	
	public void ae(V3 r){
		x+=r.x;
		y+=r.y;
		z+=r.z;
	}
	public void me(float r){
		x*=r;
		y*=r;
		z*=r;
	}
	public void de(float r){
		x/=r;
		y/=r;
		z/=r;
	}
	
	public V3 s(V3 r){
		V3 v=new V3(this);
		v.x-=r.x;
		v.y-=r.y;
		v.z-=r.z;
		return v;
	}
	
	public V3 m(V3 r){
		V3 v=new V3(this);
		v.x*=r.x;
		v.y*=r.y;
		v.z*=r.z;
		return v;
	}
	
	public V3 m(float r){
		V3 v=new V3(this);
		v.x*=r;
		v.y*=r;
		v.z*=r;
		return v;
	}
	
	public V3 d(V3 r){
		V3 v=new V3(this);
		v.x/=r.x;
		v.y/=r.y;
		v.z/=r.z;
		return v;
	}
	
	public V3 d(float r){
		V3 v=new V3(this);
		v.x/=r;
		v.y/=r;
		v.z/=r;
		return v;
	}
	
	public void eq(V3 r){
		x=r.x;
		y=r.y;
		z=r.z;
	}
	
}

