package com.alphamars.dodger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alphamars.dodger.Graphics.SurfaceView;

public class MainActivity extends AppCompatActivity {
    private SurfaceView game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new SurfaceView(this);
        setContentView(game);
        //setContentView(R.layout.activity_main);
    }
}
