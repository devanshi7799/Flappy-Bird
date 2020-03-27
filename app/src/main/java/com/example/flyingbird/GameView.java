package com.example.flyingbird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    //canvas
    private int canvasWidth;
    private int canvasHeight;

    //Bird
    private Bitmap[] bird = new Bitmap[4];
    private int birdX=10;
    private int birdY;
    private int birdSpeed;

    //blue ball
    private int blueX;
    private int bluey;
    private int blueSpeed = 15;
    private Paint bluePaint = new Paint();

    //background
    private Bitmap bgImg;

    //Score
    private Paint scorePaint = new Paint();
    private int score;

    //level
    private Paint levelPaint = new Paint();

    //Life
    private Bitmap[] life = new Bitmap[2];
    private int life_count;

    //check status
    private boolean touch_flg = false;


    Rect rect;
    int ScreenWidth , ScreenHeight;

    public GameView(Context context) {
        super(context);
        bird[0] = BitmapFactory.decodeResource(getResources() , R.drawable.b1);
        bird[1] = BitmapFactory.decodeResource(getResources() , R.drawable.b2);
        bird[2] = BitmapFactory.decodeResource(getResources() , R.drawable.b3);
        bird[3] = BitmapFactory.decodeResource(getResources() , R.drawable.b4);

        bluePaint.setColor(Color.BLUE);
        bluePaint.setAntiAlias(false);


        bgImg = BitmapFactory.decodeResource(getResources() , R.drawable.sbackground);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ScreenWidth = size.x;
        ScreenHeight = size.y;

        rect = new Rect(0,0,ScreenWidth,ScreenHeight);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(32);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        levelPaint.setColor(Color.DKGRAY);
        levelPaint.setTextSize(32);
        levelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        levelPaint.setTextAlign(Paint.Align.CENTER);
        levelPaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources() , R.drawable.hear1);
        life[1] = BitmapFactory.decodeResource(getResources() , R.drawable.heart2);

        //first position
        birdY = 500;
        score=0;
        life_count = 3;
    }

    protected void onDraw(Canvas canvas) {

        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        canvas.drawBitmap(bgImg,null,rect,null);

        //bird
        int minBirdY = bird[0].getHeight();
        int maxBirdY = canvasHeight - bird[0].getHeight() * 3;

        birdY += birdSpeed;
        if(birdY < minBirdY) birdY = minBirdY;
        if(birdY > maxBirdY) birdY = maxBirdY;
        birdSpeed += 2;

        if(touch_flg) {
            //flag wings
            canvas.drawBitmap(bird[1],birdX,birdY,null);
            touch_flg=false;
        } else {
            canvas.drawBitmap(bird[0],birdX,birdY,null);
        }

        //Blue
        blueX -= blueSpeed;
        if(hitcheck(blueX,bluey)) {
            score += 10;
            birdX = -100;
        }

        if(blueX < 0) {
            blueX = canvasWidth + 20;
            bluey = (int)Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }
        canvas.drawCircle(blueX,birdY,10,bluePaint);

        canvas.drawText("Score : 0" + score, 20 ,60,scorePaint);

        canvas.drawText("Lv 1" , canvasWidth/2 , 60 , levelPaint);

        canvas.drawBitmap(life[0],500,30,null);
        canvas.drawBitmap(life[0],550,30,null);
        canvas.drawBitmap(life[1],600,30,null);

    }

    public boolean hitcheck(int x,int y) {
        if(birdX < x && x < (birdX + bird[0].getWidth()) && birdY < y && y <(birdY+bird[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            touch_flg = true;
            birdSpeed = -20;
        }
        return true;
    }
}
