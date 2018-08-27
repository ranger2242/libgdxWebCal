package com.quadx.webtest;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.quadx.webtest.shapes1_5_1.Rect;

import java.util.ArrayList;

import static com.quadx.webtest.Game.*;

/**
 * Created by Chris Cavazos on 8/26/2018.
 */
class Button {
    Rectangle rect;
    private final Vector2 ipos= new Vector2();
    boolean hover = false;
    boolean clicked = false;
    boolean today= false;
    boolean thisMonth=false;
    String text="";
    ArrayList<Event> events = new ArrayList<>();

    public Button(Rectangle r) {
        rect = r;
        ipos.set(r.x,r.y);
    }

    private boolean isClose(Vector2 a, Vector2 b){
      return  1- Math.abs(a.x/b.x)<.05;
    }

    public void update(float dt) {
        Rectangle nRect;
        if(clicked){
            Vector2 s=new Vector2( scr.x-(4*pad),scr.y-(4*pad));
            nRect= Rect.resize(rect,new Vector2(2*pad,(2*pad)),s);
        }else {
            if (hover) {
                Vector2 s=new Vector2(  xdiv + (2 * pad) - 2,ydiv + (2 * pad) - 2);
                Vector2 p = new Vector2(ipos.x-pad+1,ipos.y-pad+1);
                nRect= Rect.resize(rect,p,s);
            } else {
                Vector2 s=new Vector2( xdiv,ydiv);
                nRect= Rect.resize(rect,ipos,s);
                Vector2 ns=rect.getSize(new Vector2());
                if(isClose(s,ns)){
                    if(rect.contains(mpos))
                        hover=true;
                }
            }
        }
        rect=nRect;
    }

    public Color getColor(Vector2 mpos) {
        Vector2 p = new Vector2(rect.x, rect.y);
        float d = p.dst(new Vector2(mpos).add(-rect.width/2,-rect.height/2));
        float al=1;
        float f=1;
        if(!thisMonth)
            f=.2f;
        if (clicked)
            al = .95f;
        if(today) {
            return new Color(.5f,.2f,.2f,al);

        }else{
            if (hover|| clicked) {
                float g = .35f*f;
                return new Color(g, g, g, al);
            } else {
                float r = 90f;
                if (d < r) {
                    float s =1/( d / r)/8;
                    if(s>1)
                        s=1;
                    float a = (.1f + (s * .2f))*f;
                    return new Color(a, a, a, al);

                } else {
                    float s = .1f*f;
                    return new Color(s, s, s, al);
                }
            }
        }
    }
}
