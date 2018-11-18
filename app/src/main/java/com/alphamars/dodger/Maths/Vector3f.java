package com.alphamars.dodger.Maths;

public class Vector3f {

    private float x,y,z;

    public Vector3f(){
        this (0.0f, 0.0f, 0.0f);
    }

    public Vector3f(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void addVector(Vector3f rhs) {
        x += rhs.getX();
        y += rhs.getY();
        z += rhs.getZ();
    }

    public void mul(float sf){
        x *= sf;
        y *= sf;
        z *= sf;
    }

    public void div (float sf){
        mul(1/sf);
    }

    public float magnitude(){
        return (float)Math.abs(Math.sqrt((double)(x*x + y*y + z*z)));
    }

    public Vector3f direction(){
        //return this.div()
        return this;
    }

    public float dotProduct(Vector3f rhs){
        return x * rhs.getX() + y * rhs.getY() + z * rhs.getZ();
    }

    public float[] toFloatArray(){
        return new float[] {x, y, z};
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public void setZ(float z){
        this.z = z;
    }

    public float getX(){return x;}
    public float getY(){return y;}
    public float getZ(){return z;}
}
