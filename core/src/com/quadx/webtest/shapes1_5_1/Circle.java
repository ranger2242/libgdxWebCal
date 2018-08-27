package com.quadx.webtest.shapes1_5_1;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Chris Cavazos on 2/4/2017.
 * v0.1
 */
public class Circle extends Shape {

    public Vector2 center = new Vector2();
    public float radius = 0;

    @SuppressWarnings("unused")
    public Circle() {
        new Circle(new Vector2(0, 0), 0);
    }

    @SuppressWarnings("SameParameterValue")
    private Circle(Vector2 c, float r) {
        center = c;
        radius = r;
    }

    @SuppressWarnings("unused")
    public boolean overlaps(Rectangle rect) {
        float distX = Math.abs(center.x - rect.x - rect.width / 2);
        float distY = Math.abs(center.y - rect.y - rect.height / 2);

        if (distX > (rect.width / 2 + radius)) {
            return false;
        }
        if (distY > (rect.height / 2 + radius)) {
            return false;
        }

        if (distX <= (rect.width / 2)) {
            return true;
        }
        if (distY <= (rect.height / 2)) {
            return true;
        }

        float dx = distX - rect.width / 2;
        float dy = distY - rect.height / 2;
        return (dx * dx + dy * dy <= (radius * radius));
    }

    @SuppressWarnings("unused")
    public boolean overlaps(Circle c) {
        return this.radius + c.radius >= center.dst(c.center);
    }

    @SuppressWarnings("unused")
    public boolean overlaps(Triangle t) {
        boolean ov = false;
        //test vertexes
        float radsqr = radius * radius;
        float fx, fy, fsq;
        float[] p = t.getPoints();
        float[] c = new float[6];
        float[] csq = new float[3];

        int count = 0;
        for (int i = 0; i < 6; i += 2) {
            fx = center.x - p[i];
            fy = center.y - p[i + 1];
            fsq = fx * fx + fy * fy;
            c[i] = fx;
            c[i + 1] = fy;
            csq[count] = fsq;
            count++;
            if (fsq <= radsqr)
                ov = true;
        }
        //test circle center
        float[] e = new float[6];
        e[0] = p[2] - p[0];
        e[1] = p[3] - p[1];
        e[2] = p[4] - p[2];
        e[3] = p[5] - p[3];
        e[4] = p[0] - p[4];
        e[5] = p[1] - p[5];
        boolean a = e[1] * c[0] - e[0] * c[1] >= 0;
        boolean b = e[3] * c[2] - e[2] * c[3] >= 0;
        boolean q = e[5] * c[4] - e[4] * c[5] >= 0;

        if (a && b && q)
            ov = true;
        //test edge intersection
        float k = c[0] * e[0] + c[1] * e[1];
        if (k > 0) {
            float len = e[0] * e[0] + e[1] * e[1];
            k = k * k / len;
            if (k < len && csq[0] - k <= radsqr)
                ov = true;
        }
        k = c[2] * e[2] + c[3] * e[3];
        if (k > 0) {
            float len = e[2] * e[2] + e[3] * e[3];
            k = k * k / len;

            if (k < len && csq[1] - k <= radsqr)
                ov = true;
        }
        k = c[4] * e[4] + c[5] * e[5];
        if (k > 0) {
            float len = e[4] * e[4] + e[5] * e[5];
            k = k * k / len;
            if (k < len && csq[2] - k <= radsqr)
                ov = true;
        }
        return ov;
    }

    @SuppressWarnings("unused")
    public void scl(float scl) {
        radius *= scl;
    }

    @SuppressWarnings("unused")
    public static Vector2 point(Vector2 pos, double r, double thetaDeg) {
        double x = pos.x + (r) * (float) Math.cos(Math.toRadians(thetaDeg));
        double y = pos.y + (r) * (float) Math.sin(Math.toRadians(thetaDeg));
        return new Vector2((float) x, (float) y);
    }

    @Override
    public Vector2 getCenter() {
        return center;
    }

    @SuppressWarnings("unused")
    public boolean intersects(Line l) {
        return ((center.x - l.a.x) * (l.b.y - l.a.y) - (center.y - l.a.y) * (l.b.x - l.a.x)) <= radius;
    }
    @SuppressWarnings("unused")
    public boolean intersects(Vector2 v){
        return center.dst(v)<=radius;
    }
}