package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class CakeView extends SurfaceView{

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();

    Paint balloonPaint = new Paint();

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 80.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;
    Paint paint = new Paint();

    public static final float balloonHeight = 70.0f;
    public static final float balloonWidth = balloonHeight * 0.7f;


    //Need Cake Model
    CakeModel cakeModel;



    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //When CakeView is created must have CakeModel
        cakeModel = new CakeModel();

        //Setup our palette
        cakePaint.setColor(0xFFC755B5);  //violet-red
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setTextSize(30);

        setBackgroundColor(Color.WHITE);  //better than black default
        balloonPaint.setColor(Color.BLUE);

    }

    /**
     * need to have cakeModel instance accessible
     *
     * @return CakeModel
     */
    public CakeModel getCakeModel(){
        return cakeModel;
    }

    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        canvas.drawRect(left, bottom - candleHeight, left + candleWidth, bottom, candlePaint);

        if(cakeModel.litCandles) {
            //draw the outer flame
            float flameCenterX = left + candleWidth / 2;
            float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius / 3;
            canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

            //draw the inner flame
            flameCenterY += outerFlameRadius / 3;
            canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);
        }

        //draw the wick
        float wickLeft = left + candleWidth/2 - wickWidth/2;
        float wickTop = bottom - wickHeight - candleHeight;
        canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);

    }

    /**
     * drawCandles draws an amount of candles equivalent to user suggestion
     *
     */
    public void drawCandles(Canvas canvas, int sections){
        for(int i = 1; i < sections; i++){
            drawCandle(canvas, cakeLeft + cakeWidth*i/sections - candleWidth/2, cakeTop);
        }
    }

    public void drawBalloon(Canvas canvas){
        canvas.drawOval(cakeModel.balloonCX - balloonWidth/2, cakeModel.balloonCY - balloonHeight/2,
                cakeModel.balloonCX + balloonWidth/2, cakeModel.balloonCY + balloonHeight/2, balloonPaint);
        canvas.drawLine(cakeModel.balloonCX, cakeModel.balloonCY + balloonHeight/2, cakeModel.balloonCX,
                cakeModel.balloonCY + balloonHeight, balloonPaint);
    }

    /**
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     *
     * This method will draw a birthday cake
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top = cakeTop;
        float bottom = cakeTop + frostHeight;

        //Frosting on top
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        top += layerHeight;
        bottom += frostHeight;

        //Then a second frosting layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a second cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);

        //Now a candle in the center
//        drawCandle(canvas, cakeLeft + cakeWidth/2 - candleWidth/2, cakeTop);
        if(cakeModel.hasCandles){
//            drawCandle(canvas, cakeLeft + cakeWidth/3 - candleWidth/2, cakeTop);
//            drawCandle(canvas, cakeLeft + cakeWidth*2/3 - candleWidth/2, cakeTop);
            drawCandles(canvas, cakeModel.numCandles + 1);
        }
        String xyLoc = Integer.toString(cakeModel.x) + ", " + Integer.toString(cakeModel.y);
        canvas.drawText(xyLoc, getWidth()-150, getHeight()-100, paint);

        if(cakeModel.balloonCX >= 0 && cakeModel.balloonCY >= 0){
            drawBalloon(canvas);
        }


    }//onDraw

}//class CakeView

