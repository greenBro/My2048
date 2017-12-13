package com.bugua.my2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;

/**
 * Created by Administrator on 2017-11-20.
 */

public class CardView extends AppCompatTextView {
    public CardView(Context context) {
        super(context);
        setTextSize(35);
        setNum(0);
        setText(getNum());
//        setBackground(setGridColor(R.color.gridlevel1));
        setTypeface(Typeface.MONOSPACE);
        setGravity(Gravity.CENTER);
    }

    public CardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private Drawable setGridColor(int rescolor){

//        Paint paint=new Paint();
//        paint.setColor(getResources().getColor(rescolor));  //设置画笔颜色
//        paint.setStyle(Paint.Style.FILL);//设置填充样式
//        paint.setStrokeWidth(15);//设置画笔宽度
//        RectF rect = new RectF(0, 0, 300, 300);
//        canvas.drawRoundRect(rect, 20, 20, paint);
        ShapeDrawable shapeDrawable =new ShapeDrawable();
        shapeDrawable.setShape(new RoundRectShape(new float[]{20,20,20,20,20,20,20,20},null,null));
        shapeDrawable.getPaint().setColor(getResources().getColor(rescolor));
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        return shapeDrawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        this.canvas=canvas;
    }
    private Canvas canvas;
    public String getNum() {

        if(num==0){
            return "";
        }
        return num + "";
    }

    public void setNum(int num) {
        this.num = num;
        setText(getNum());
        switch(getNum()){
            case "":
                setBackground(setGridColor(R.color.gridlevel1));
                break;
            case "2":
                setBackground(setGridColor(R.color.gridlevel1));
                break;
            case "4":
                setBackground(setGridColor(R.color.gridlevel2));
                break;
            case "8":
                setBackground(setGridColor(R.color.gridlevel3));
                break;
            case "16":
                setBackground(setGridColor(R.color.gridlevel4));
                break;
            case "32":
                setBackground(setGridColor(R.color.gridlevel5));
                break;
            case "64":
                setBackground(setGridColor(R.color.gridlevel6));
                break;
            case "128":
                setBackground(setGridColor(R.color.gridlevel8));
                break;
            case "256":
                setBackground(setGridColor(R.color.gridlevel9));
                break;
            case "512":
                setBackground(setGridColor(R.color.gridlevel10));
                break;
            case "1024":
                setBackground(setGridColor(R.color.gridlevel11));
                break;
            case "2048":
                setBackground(setGridColor(R.color.gridlevel12));
                break;
            case "4096":
                setBackground(setGridColor(R.color.gridlevel13));
                break;
            case "8192":
                setBackground(setGridColor(R.color.gridlevel14));
        }
    }
    //判断卡片是否相等
    public boolean equalCard(CardView cardView){
        return this.num==Integer.parseInt(cardView.getNum()==""?"0":cardView.getNum());
    }

    private int num;

}
