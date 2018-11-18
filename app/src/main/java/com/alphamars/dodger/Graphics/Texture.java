package com.alphamars.dodger.Graphics;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {

    public Texture(int[] cols){
        int w = (int) Math.sqrt(cols.length);
        int h = (int) Math.sqrt(cols.length);
        ByteBuffer bb = ByteBuffer.allocateDirect(w*h*4); // 4 bytes in int
        IntBuffer ib = bb.asIntBuffer();
        ib.put(cols);
        GLES20.glGenTextures(1, ib);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, ib.get(0));
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, w, h, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_BYTE, ib);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
    }
}
