package org.example.opengl;
import java.util.*;

class Ti {
	public short[] vs=new short[3];
	public Ti[] adj=new Ti[3]();
	public List<Node> nodes=new ArrayList<Node>();
	public Ti() {   }
	public Ti(int a,int b,int c){
		vs[0]=(short)a;
		vs[1]=(short)b;
		vs[2]=(short)c;
	}

	
	
}

