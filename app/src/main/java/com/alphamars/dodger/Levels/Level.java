package com.alphamars.dodger.Levels;

public interface Level {
    public void update(double deltaTime);
    public void render(float[] MVPMatrix);
}
