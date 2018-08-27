package com.quadx.webtest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.quadx.webtest.shapes1_5_1.ShapeRendererExt;
import com.quadx.webtest.timers.Delta;
import com.quadx.webtest.timers.Time;

import java.text.DateFormatSymbols;
import java.time.YearMonth;
import java.util.*;

public class Game extends ApplicationAdapter {
    private SpriteBatch sb;
    private ShapeRendererExt sr;
    ArrayList<Event> allEvents = new ArrayList<>();
    public static final Vector2 scr = new Vector2();
    public static final Random rn = new Random();
    private final ArrayList<Button> buttons = new ArrayList<>();
    private final ArrayList<Button> buttonBuff = new ArrayList<>();
    private final Delta dClick = new Delta(10 * Time.ft);
    static final Vector2 mpos = new Vector2();
    static int xdiv = 0;
    static final int pad = 10;
    static int ydiv = 0;
    private Calendar cal = Calendar.getInstance();
    private int curDay = 0;
    private int curMonth = 0;
    private int dayOfMonth = 0;
    private int daysInMonth = 0;
    private int curYear = 0;
    private int prevMonth = 0;
    private String dispMonth = "-";
    private String dispDay = "-";
    private String dispYear = "-";
    private String dispDate = "-";

    private BitmapFont font;
    private BitmapFont font2;

    @Override
    public void create() {
        allEvents.add(new Event("8-10-2018","TEST EVE","details"));
        sr = new ShapeRendererExt();
        sb = new SpriteBatch();
        font = new BitmapFont();
        font2 = new BitmapFont();
        font2.getData().setScale(2);
        scr.set(400, 400);
        cal = new GregorianCalendar(2018, 7, 1);
        Gdx.graphics.setWindowedMode((int) scr.x, (int) scr.y);
        refreshDisplay();
    }

    private void calcRects() {
        buttons.clear();
        buttonBuff.clear();
        xdiv = (int) (scr.x - (8 * pad)) / 7;
        ydiv = (int) (scr.y - (7 * pad) - 70) / 6;
        int daysInPrevMonth = YearMonth.of(curYear, prevMonth + 1).lengthOfMonth();
        int dayCnt = daysInPrevMonth - firstOfGrid() + 1;
        System.out.println(daysInMonth + " " + new DateFormatSymbols().getMonths()[curMonth] + " " + firstOfGrid());
        System.out.println(daysInPrevMonth + " " + new DateFormatSymbols().getMonths()[prevMonth] + "\n");
        boolean secondM = false;
        boolean firstM = true;
        if(firstOfGrid()==0 && dayCnt>daysInPrevMonth){
            dayCnt=1;
        }
        for (int j = 5; j >= 0; j--) {
            for (int i = 0; i < 7; i++) {

                if (firstOfGrid() != 0) {//cal has previous month at beginning
                    if (secondM) {
                        if (dayCnt > daysInMonth) {
                            dayCnt = 1;
                            secondM = false;
                        }
                    }
                    if (firstM) {
                        if (dayCnt > daysInPrevMonth) {
                            dayCnt = 1;
                            firstM = false;
                            secondM = true;
                        }
                    }
                } else {
                    if (dayCnt > daysInMonth) {
                        dayCnt = 1;
                        secondM = false;
                        firstM = false;
                    } else {
                        if(firstM)
                        secondM = true;
                    }

                }

                int x = (xdiv * i) + ((i + 1) * pad) + 2;
                int y = (ydiv * (j + 1)) + ((j + 1) * pad) - 52;
                Rectangle r = new Rectangle(x, y, xdiv, ydiv);
                Button b = new Button(r);
                b.text = dayCnt++ + "";
                if (i == curDay - 1 && j == 5) {
                    b.today = true;
                }

                b.thisMonth = secondM;


                buttons.add(b);
                buttonBuff.add(b);
            }
        }
    }

    private int firstOfGrid() {
        Calendar c = new GregorianCalendar(curYear, curMonth, 1);
        return c.get(Calendar.DAY_OF_YEAR) % 7;
    }

    private boolean canClick() {
        for (Button b : buttons) {
            if (b.clicked)
                return false;
        }
        return true;
    }

    private void update() {
        float dt = Gdx.graphics.getDeltaTime();
        dClick.update(dt);
        mpos.set(Gdx.input.getX(), scr.y - Gdx.input.getY());
        for (int i = 0; i < buttons.size(); i++) {
            Button b = buttons.get(i);
            Rectangle r = b.rect;
            if (r.contains(mpos)) {
                if (Gdx.input.isButtonPressed(0) && dClick.isDone() && (b.clicked || canClick())) {
                    b.clicked = !b.clicked;
                    dClick.reset();
                }
            } else {
                b.hover = false;

                buttons.set(i, buttonBuff.get(i));

            }
        }
        for (Button b : buttons) {
            b.update(dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && dClick.isDone()) {
            curMonth = (curMonth + 1) % 12;
            cal = new GregorianCalendar(curYear, curMonth, 1);
            refreshDisplay();
            dClick.reset();
        }
    }

    private void refreshDisplay() {
        curYear = cal.get(Calendar.YEAR);
        curMonth = cal.get(Calendar.MONTH);
        prevMonth = (curMonth - 1 + 12) % 12;
        YearMonth yearMonthObject = YearMonth.of(curYear, curMonth + 1);
        daysInMonth = yearMonthObject.lengthOfMonth();
        curDay = cal.get(Calendar.DAY_OF_WEEK);
        dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);


        dispMonth = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        dispDay = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        dispYear = curYear + "";
        dispDate = dayOfMonth + "";


        calcRects();
    }

    @Override
    public void render() {
        update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        int i = -1;
        for (Button b : buttons) {
            if (!b.clicked) {
                sr.setColor(b.getColor(mpos));
                sr.rect(b.rect);
            } else {
                i = buttons.indexOf(b);
            }
        }

        sr.end();

        sb.begin();
        sb.setColor(Color.WHITE);
        for (Button b : buttons) {
            font.draw(sb, b.text, b.rect.x, b.rect.y + 12);
        }
        try {
            font2.draw(sb, dispMonth + " " + dispDate + ", " + dispYear, pad, scr.y - 50);
            font2.draw(sb, dispDay, pad, scr.y - 20);

        } catch (Exception ignored) {

        }

        sb.end();
        //active
        if (i != -1) {
            Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
            Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);

            sr.begin(ShapeRenderer.ShapeType.Filled);
            Button act = buttons.get(i);
            sr.setColor(act.getColor(mpos));
            sr.rect(act.rect);
            sr.end();
            sb.begin();
            font.draw(sb, act.text, act.rect.x + 10, act.rect.y + 20);
            sb.end();
        }
    }

    @Override
    public void dispose() {
        sb.dispose();
    }
}
