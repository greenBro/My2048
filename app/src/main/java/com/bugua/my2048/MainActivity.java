package com.bugua.my2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ScoreChange{

    private TextView tvscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvscore = (TextView)findViewById(R.id.tvscore);

    }

    @Override
    public void scoreLisnner(int score) {
        tvscore.setText(GameLayout.SCORE+"");
    }
}
