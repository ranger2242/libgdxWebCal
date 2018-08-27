package com.quadx.webtest.shapes1_5_1;

import com.badlogic.gdx.math.Vector2;

import static com.quadx.webtest.Game.scr;


/**
 * Created by Chris Cavazos on 2/1/2018.
 */
class Tools {
    public static Vector2 wrap(Vector2 pos) {
        float x=pos.x %= scr.x + 1;
        float y=pos.y %= scr.y+1;
        return new Vector2((x < 0) ? x + scr.x : x,(y < 0) ? y + scr.y : y);
    }
}
