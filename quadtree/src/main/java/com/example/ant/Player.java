package com.example.ant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.ant.AntType.*;
import com.example.utils.Point;
import com.example.utils.QuadTree;

public class Player extends Ant {
    private float timeSinceLastMove = 0;

    public Player(float x, float y, QuadTree quadTree) {
        super(AntType.BLACK, AntRole.WORKER, x, y, quadTree);
        
    }

    @Override
    public void handleMovement(float deltaTime) {
        timeSinceLastMove += deltaTime;
        if (timeSinceLastMove < 0.1f) {
            return; // Wait for a short time before allowing movement
        }
        timeSinceLastMove = 0;

        float moveX = 0, moveY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveY += this.getSpeed();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveY -= this.getSpeed();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveX -= this.getSpeed();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveX += this.getSpeed();
        }


        Point newPlayerPos = new Point(this.x + moveX, this.y + moveY);
        if (this.getQuadTree().getRoot().getBounds().contains(newPlayerPos)) {
            this.x = newPlayerPos.x;
            this.y = newPlayerPos.y;
            
            // Add the new position only if it's not already a cleared spot
            if (!this.getQuadTree().getRoot().getPoints().contains(newPlayerPos)) {
                this.getQuadTree().insert(newPlayerPos); // Mark the new position as empty
            }
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle((float)x + 5, (float)y + 5, 5f);
    }
}