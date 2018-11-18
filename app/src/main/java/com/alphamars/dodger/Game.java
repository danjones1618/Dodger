package com.alphamars.dodger;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.view.MotionEvent;

import com.alphamars.dodger.Levels.Level;
import com.alphamars.dodger.Levels.SquareLevel;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Game  implements GLSurfaceView.Renderer{

    // MVP = model view projection
    public final float[] MVPMatrix = new float[16];
    public final float[] projectionMatrix = new float[16];
    public final float[] viewMatrix = new float[16];

    private Level currentLevel;

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(1.0f,1.0f,1.0f,1.0f);
        currentLevel = new SquareLevel();
    }

    long lastTime = System.nanoTime();
    long timer = System.currentTimeMillis();
    final double ns = 1000000000.0 / 30.0;
    double deltaTime = 0;
    int frames = 0;
    int updates = 0;
    public void onDrawFrame(GL10 unused) {
        long currentTime = System.nanoTime();
        deltaTime += (currentTime - lastTime) / ns;
        lastTime = currentTime;

        while (deltaTime >= 1){
            update(deltaTime);
            updates++;
            deltaTime--;
        }

        render();
        frames++;

        if (System.currentTimeMillis() - timer > 1000){
            timer += 1000;
            System.out.println("FPS: " + frames + " UPS: " + updates);
            updates = 0;
            frames = 0;
        }
    }

    public void update(double deltaTime){
        currentLevel.update(deltaTime);
    }

    public void render(){
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set camera position - Y axis is up
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f,
                0f, 0f, 0f,1.0f, 0.0f);

        // MVP matrix contains the transformations of projection & view
        Matrix.multiplyMM(MVPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        currentLevel.render(MVPMatrix);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height){
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        // Generate the projection matrix - the space is transformed into a frustum
        Matrix.frustumM(projectionMatrix, 0, ratio, -ratio, -1, 1, 3, 7);
    }
}
