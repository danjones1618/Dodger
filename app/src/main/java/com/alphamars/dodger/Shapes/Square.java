package com.alphamars.dodger.Shapes;

import android.opengl.GLES20;

import com.alphamars.dodger.Game;
import com.alphamars.dodger.Graphics.Mesh;
import com.alphamars.dodger.Graphics.Shader;
import com.alphamars.dodger.Graphics.Texture;
import com.alphamars.dodger.Maths.Vector3f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Square {

    public Vector3f pos;
    public Mesh m;
    public Texture t;

    // TODO switch to using a texture
    private Shader shader = Shader.modelShader;

    public float[] vertexCoords = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            -0.5f, 0.75f, 0.0f
    };
    protected short[] drawOrder = {0, 1, 2, 0, 2, 3};
    public float color[] = {0.0f, 0.0f, 1.0f, 1.0f};

    int texId;

    public Square(){
        pos = new Vector3f(0f, 0f, 0f);
        m = new Mesh(vertexCoords, drawOrder);

        int[] cols = {0xffffffff, 0xff0000ff, 0x00ff00ff, 0x0000ffff};
        int w = (int) Math.sqrt(cols.length);
        int h = (int) Math.sqrt(cols.length);
        ByteBuffer bb = ByteBuffer.allocateDirect(w*h*4); // 4 bytes in int
        IntBuffer ib = bb.asIntBuffer();
        ib.put(cols);
        int[] ids = new int[1];
        GLES20.glGenTextures(1, ids, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, ib.get(0));
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, w, h, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        texId = ids[0];
    }

    private int positionHandle;

    private int colorHandle;
    private int MVPMatrixHandle;
    private int posHandle;
    public void draw(float[] MVPMatrix){
        GLES20.glUseProgram(shader.getShaderID());

        positionHandle = GLES20.glGetAttribLocation(shader.getShaderID(), "vecPos");

        shader.updateUniform("pos", pos);
        shader.updateUniform("uMVPMatrix", MVPMatrix);
        shader.updateUniform("vColor", color);
        //shader.updateUniform("tex", texId);
        m.draw(positionHandle);

       GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
