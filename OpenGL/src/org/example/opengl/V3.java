package org.example.opengl;
import android.util.*;

class V3 {
	public float x=0,y=0,z=0;
	public V3() {   }
	public V3(float fx,float fy,float fz){
		x=fx;y=fy;z=fz;
	}
	public void norm(){
		float l=FloatMath.sqrt(x*x+y*y+z*z);
		x/=l;
		y/=l;
		z/=l;
	}
	
	
}

