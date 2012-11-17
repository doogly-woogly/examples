package org.example.opengl;

import java.util.*;
import javax.microedition.khronos.opengles.*;

public class gleser
{
public GL10 gl;
public gler current;
public List<gler> glers=new ArrayList<gler>();

public gleser(GL10 gl){
this.gl=gl;
}
public void begin(int what){
gler n=new gler(gl);
n.begin(what);
current=n;
glers.add(n);
}
public void end(){
current.end();
}
public void vertex(V3 v){
current.vertex(v);
}
public void Draw(){
for(int b=0;b<glers.size();b++){
glers.get(b).Draw();
}
}
}
