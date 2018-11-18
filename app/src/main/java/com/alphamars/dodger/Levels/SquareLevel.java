package com.alphamars.dodger.Levels;

import com.alphamars.dodger.Shapes.Square;

public class SquareLevel implements Level {

    public Square square;

    public SquareLevel(){
        square = new Square();
    }

    float colorStep = 0.01f;
    public void update(double delta){
        square.pos.setY(square.pos.getY() + colorStep / 2);
        square.color[0] += colorStep;
        if (square.color[0] >= 1f || square.color[0] <= 0f)
            colorStep *= -1;
    }

    public void render(float[] MVPMatrix){
        square.draw(MVPMatrix);
    }
}
