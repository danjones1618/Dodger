package com.alphamars.dodger.Graphics;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


public class Mesh {

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    private final static int COORDS_PER_VERTEX = 3;
    private float[] vertexCoords;
    private short[] drawOrder;

    public Mesh(float[] vertexCoords, short[] drawOrder){
        updateVertices(vertexCoords);
        updateDrawOrder(drawOrder);
    }

    private void genVertexBuffer(){
        ByteBuffer bb = ByteBuffer.allocateDirect(vertexCoords.length * 4); // 4 bytes in float
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertexCoords);
        vertexBuffer.position(0);
    }

    private void genDrawListBuffer() {
        ByteBuffer bb = ByteBuffer.allocateDirect(drawOrder.length * 2); // 2 bytes in short
        bb.order(ByteOrder.nativeOrder());
        drawListBuffer = bb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }

    public void updateVertices(float[] verticies){
        this.vertexCoords = verticies;
        genVertexBuffer();
    }

    public void updateDrawOrder(short[] drawOrder){
        this.drawOrder = drawOrder;
        genDrawListBuffer();
    }

    public void draw(int positionHandle) {
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, COORDS_PER_VERTEX * 4, vertexBuffer);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
    }
}
