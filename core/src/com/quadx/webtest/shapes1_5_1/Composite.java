package com.quadx.webtest.shapes1_5_1;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Chris Cavazos on 12/14/2017.
 */
@SuppressWarnings("unused")
public class Composite extends Shape {
    private final ArrayList<Shape> body = new ArrayList<>();

    public void add(Shape s) {
        body.add(s);
    }

    public boolean intersects(Ngon ngon) {
        ArrayList<Line> lines = null;
        for (Triangle t : Triangle.triangulate(ngon)) {
            lines.addAll(Line.asLines(t));
        }

        for (Shape s : body) {
            for (Line ls : lines) {
                if (s instanceof Line) {
                    Line l = (Line) s;
                    return l.intersects(ls);
                }
            }
        }
        return false;
    }

    @Override
    public Vector2 getCenter() {
        return null;
    }
}
