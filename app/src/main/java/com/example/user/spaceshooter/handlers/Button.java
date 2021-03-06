package com.example.user.spaceshooter.handlers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

/**
 * Created by User on 2017-07-02.
 */

public class Button {

    private Rect rect;
    private Context ctx;
    private String txt;
    private boolean clicked, clickLen = true;
    private long clickTimer;
    private int color;
    private String id;


    public Button(Context ctx, Rect rect, String txt, int color, String id){
        this.ctx = ctx;
        this.rect = rect;
        this.txt = txt;
        this.color = color;
        this.id = id;
        clicked = false;
    }

    public String getId(){ return id; }

    public boolean getClicked(){
        return clicked;
    }

    public void setClicked(boolean clicked) { this.clicked = clicked; }

    public String getText(){
        return txt;
    }

    public void clicked(int x, int y){
        if(clickLen) {
            if (rect.contains(x, y))
                clicked = true;
            else
                clicked = false;
            clickLen = false;
            clickTimer = System.nanoTime();
        }
    }

    public void drawButton(Canvas c){
        Typeface type = Typeface.createFromAsset(ctx.getAssets(),"fonts/space_font.ttf");
        Paint paint = new Paint();
        paint.setTypeface(type);
        paint.setColor(color);
        paint.setTextSize(70);

        //Draw Button
        if(!clicked) {
            paint.setStyle(Paint.Style.STROKE);
            c.drawRect(rect, paint);
        }else{
            paint.setStyle(Paint.Style.FILL);
            c.drawRect(rect,paint);
        }

        //***CENTER BUTTONS
        RectF bounds = new RectF(rect);
        // measure text width
        bounds.right = paint.measureText(txt, 0, txt.length());
        // measure text height
        bounds.bottom = paint.descent() - paint.ascent();

        bounds.left += (rect.width() - bounds.right) / 2.0f;
        bounds.top += (rect.height() - bounds.bottom) / 2.0f;

        paint.setColor(Color.YELLOW);
        //Draw Centered Text
        c.drawText(txt, bounds.left, bounds.top - paint.ascent(), paint);

        updateTimer();
    }

    public void updateTimer(){
        long elapsed = (System.nanoTime() - clickTimer) / 1000000;
        if(elapsed > 500)
            clickLen = true;
    }
}
