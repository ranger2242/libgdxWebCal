package com.quadx.webtest.shapes1_5_1;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Chris Cavazos on 8/17/2018.
 */
public class Rect extends Shape {
    public static Rectangle square(Vector2 c, float s){
        return new Rectangle(c.x-(s/2), c.y-(s/2),s,s);
    }

    public static Rectangle rect(Vector2 p, Vector2 dim){
        return rect(p,dim.x,dim.y);
    }
    public static Rectangle rect(Vector2 p, double s){
        return rect(p,s,s);
    }
    private static Rectangle rect(Vector2 p, double w, double h){
        return new Rectangle(p.x,p.y,(float) w,(float)h);
    }
    @Override
    public Vector2 getCenter() {
        return null;
    }
    public static Rectangle resize(Rectangle r, Vector2 p, Vector2 d){
        Vector2 s = new Vector2(r.width,r.height);
        Vector2 fs= s.lerp(d,.1f);
        r.setSize(fs.x,fs.y);

        Vector2 pi= new Vector2(r.x,r.y);
        Vector2 fp = pi.lerp(p,.1f);
        r.setPosition(fp);
        return r;
    }
}
