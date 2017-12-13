package com.bugua.my2048;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-11-17.
 */

public class GameLayout extends GridLayout {
    public GameLayout(Context context) {
        super(context);
        initView();
    }

    public GameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public GameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    float x = 0;
    float y = 0;
    ScoreChange scoreChange;

    public void initView() {
        if (getContext() instanceof MainActivity) {
            scoreChange = (MainActivity) getContext();
        }
        setColumnCount(4);
        setRowCount(4);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int direction = 0;//1:left,2:right,3:top,4:bottom
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = motionEvent.getX();
                        y = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        float disx = motionEvent.getX() - x;
                        float disy = motionEvent.getY() - y;
                        Log.e("GameLayout", "onTouch: " + disx + "disy:" + disy);
                        if (Math.abs(disx) > Math.abs(disy) && Math.abs(disx) > 20) {
                            direction = (disx < 0) ? 1 : 2;
                            slidHandle(direction);

                        } else if (Math.abs(disx) < Math.abs(disy) && Math.abs(disy) > 20) {
                            direction = (disy < 0) ? 3 : 4;
                            slidHandle(direction);
                        }
                        break;
                }
                return true;
            }
        });
    }

    private boolean notInit = true;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (notInit) {
            int width = (Math.min(w, h) - 20) / 4;
            addCardView(width, width);
            notInit = false;
        }
    }

    public void addCardView(int pw, int ph) {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                FrameLayout frameLayout = new FrameLayout(getContext());
                FrameLayout.LayoutParams cardParams = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(pw, ph);
                cardParams.setMargins(20, 20, 0, 0);
                addView(frameLayout, params);
                CardView cardView = new CardView(getContext());
                frameLayout.addView(cardView, cardParams);
                arr[x][y] = cardView;
            }
        }
        addAround();
        addAround();
    }

    private void addAround() {
        ArrayList<CardView> nullView = new ArrayList<CardView>();
        nullView.clear();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if ((arr[x][y]).getNum() == "") {
                    nullView.add(arr[x][y]);
                }
            }
        }
        if (nullView.size() != 0) {
            int position = (int) (Math.random() * nullView.size());
            if (Math.random() > 0.1) {
                nullView.get(position).setNum(2);
            } else if (Math.random() <= 0.1 && Math.random() > 0.01) {
                nullView.get(position).setNum(4);
            } else if (Math.random() <= 0.01 && Math.random() > 0.001) {
                nullView.get(position).setNum(8);
            } else if (Math.random() <= 0.001 && Math.random() > 0) {
                nullView.get(position).setNum(16);
            }
            nullView.remove(position);
            if (nullView.size() == 0 && !cancelAble()) {
                Toast.makeText(getContext(), "没有可以消得了", Toast.LENGTH_LONG).show();
            }
        } else {//当没有空的卡片，且没有可以消的情况
            if (nullView.size() == 0 && !cancelAble()) {
                Toast.makeText(getContext(), "没有可以消得了", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean cancelAble() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (y == 3 && x != 3 && arr[x][y].equalCard(arr[x + 1][y])) {
                    return true;
                } else if (x == 3 && y != 3 && arr[x][y].equalCard(arr[x][y + 1])) {
                    return true;
                } else if (y < 3 && x < 3 && arr[x][y].equalCard(arr[x][y + 1])) {
                    return true;
                } else if (y < 3 && x < 3 && arr[x][y].equalCard(arr[x + 1][y])) {
                    return true;
                }
            }
        }
        return false;
    }

    CardView[][] arr = new CardView[4][4];

    public static int SCORE = 0;

    private void slidHandle(int dir) {
        switch (dir) {
            case 1:
//                Log.e("GameLayout", "slidHandle: 向左滑了");
                for (int x = 0; x < 4; x++) {
                    int offset = 0;
                    for (int y = 1; y < 4; y++) {
/*                        if (arr[x][y].getNum() != "") {
                            if (arr[x][y].equalCard(arr[x][offset])) {
                                arr[x][offset].setNum(Integer.parseInt(arr[x][y].getNum()) * 2);
                                scoreChange.scoreLisnner(SCORE += Integer.parseInt(arr[x][offset].getNum()));
                                arr[x][y].setNum(0);
                                if (offset != 0) {
                                    offset = 0;
                                    y = 0;
                                }
                            } else {
                                if (arr[x][offset].getNum() != "") {
                                    ++offset;
                                    arr[x][offset].setNum(Integer.parseInt(arr[x][y].getNum()));
                                } else {
                                    arr[x][offset].setNum(Integer.parseInt(arr[x][y].getNum()));
                                }
                                if (offset != y) {
                                    arr[x][y].setNum(0);
                                }
                            }
                        }
                    }*/
                        if (arr[x][y].getNum() != "") {
                            if (arr[x][offset].getNum() != "") {
                                if (arr[x][y].equalCard(arr[x][offset])) {
                                    arr[x][offset].setNum(Integer.parseInt(arr[x][y].getNum()) * 2);
                                    scoreChange.scoreLisnner(SCORE += Integer.parseInt(arr[x][offset].getNum()));
                                    arr[x][y].setNum(0);
                                    ++offset;
                                } else {
                                    ++offset;
                                }
                            } else {
                                arr[x][offset].setNum(Integer.parseInt(arr[x][y].getNum()));
                                arr[x][y].setNum(0);
                            }
                        }
                    }
                }
                addAround();
                break;


            case 2:
//                Log.e("GameLayout", "slidHandle: 向右滑了");
                for (int x = 0; x < 4; x++) {
                    int offset = 3;
                    for (int y = 2; y >= 0; y--) {
                        /*if (arr[x][y].getNum() != "") {
                            if (arr[x][y].equalCard(arr[x][offset])) {
                                arr[x][offset].setNum(Integer.parseInt(arr[x][y].getNum()) * 2);
                                scoreChange.scoreLisnner(SCORE += Integer.parseInt(arr[x][offset].getNum()));
                                arr[x][y].setNum(0);
                                if (offset != 3) {
                                    offset = 3;
                                    y = 3;
                                }
                            } else {
                                if (arr[x][offset].getNum() != "") {
                                    --offset;
                                    arr[x][offset].setNum(Integer.parseInt(arr[x][y].getNum()));
                                } else {
                                    arr[x][offset].setNum(Integer.parseInt(arr[x][y].getNum()));
                                }
                                if (offset != y) {
                                    arr[x][y].setNum(0);
                                }
                            }
                        }*/
                        if (arr[x][y].getNum() != "") {
                            if (arr[x][offset].getNum() != "") {
                                if (arr[x][y].equalCard(arr[x][offset])) {
                                    arr[x][offset].setNum(Integer.parseInt(arr[x][y].getNum()) * 2);
                                    scoreChange.scoreLisnner(SCORE += Integer.parseInt(arr[x][offset].getNum()));
                                    arr[x][y].setNum(0);
                                    --offset;
                                } else {
                                    --offset;
                                }
                            } else {
                                arr[x][offset].setNum(Integer.parseInt(arr[x][y].getNum()));
                                arr[x][y].setNum(0);
                            }
                        }
                    }
                }
                addAround();
                break;
            case 3:
//                Log.e("GameLayout", "slidHandle: 向上滑了");
                for (int y = 0; y < 4; y++) {
                    int offset = 0;
                    for (int x = 1; x < 4; x++) {
                        if (arr[x][y].getNum() != "") {
                            if (arr[x][y].equalCard(arr[offset][y])) {
                                arr[offset][y].setNum(Integer.parseInt(arr[x][y].getNum()) * 2);
                                scoreChange.scoreLisnner(SCORE += Integer.parseInt(arr[offset][y].getNum()));
                                arr[x][y].setNum(0);
                                if (offset != 0) {
                                    offset = 0;
                                    x = 0;
                                }
                            } else {
                                if (arr[offset][y].getNum() != "") {
                                    ++offset;
                                    arr[offset][y].setNum(Integer.parseInt(arr[x][y].getNum()));
                                } else {
                                    arr[offset][y].setNum(Integer.parseInt(arr[x][y].getNum()));
                                }
                                if (offset != x) {
                                    arr[x][y].setNum(0);
                                }
                            }
                        }
                    }
                }
                addAround();
                break;
            case 4:
//                Log.e("GameLayout", "slidHandle: 向下滑了");
                for (int y = 0; y < 4; y++) {
                    int offset = 3;
                    for (int x = 2; x >= 0; x--) {
                        if (arr[x][y].getNum() != "") {
                            if (arr[x][y].equalCard(arr[offset][y])) {
                                arr[offset][y].setNum(Integer.parseInt(arr[x][y].getNum()) * 2);
                                scoreChange.scoreLisnner(SCORE += Integer.parseInt(arr[offset][y].getNum()));
                                arr[x][y].setNum(0);
                                if (offset != 3) {
                                    offset = 3;
                                    x = 3;
                                }
                            } else {
                                if (arr[offset][y].getNum() != "") {
                                    --offset;
                                    arr[offset][y].setNum(Integer.parseInt(arr[x][y].getNum()));
                                } else {
                                    arr[offset][y].setNum(Integer.parseInt(arr[x][y].getNum()));
                                }
                                if (offset != x) {
                                    arr[x][y].setNum(0);
                                }
                            }
                        }
                    }
                }
                addAround();
                break;
        }
    }

}
