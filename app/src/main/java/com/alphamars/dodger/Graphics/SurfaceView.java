package com.alphamars.dodger.Graphics;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.alphamars.dodger.Game;
import com.alphamars.dodger.Input.TouchHandler;
import com.alphamars.dodger.MainActivity;

public class SurfaceView extends GLSurfaceView {


    private Game renderer;

    public SurfaceView(MainActivity activity){
        super (activity);
        setEGLContextClientVersion(2);
        renderer = new Game();
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public boolean onTouchEvent(MotionEvent e){
        return TouchHandler.onTouchEvent(e);
    }
}
