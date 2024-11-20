package com.example.ant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.App;
import com.example.ant.AntType.*;
import com.example.terrain.TerrainManager;
import com.example.utils.Point;
import com.example.utils.QuadTree;

import java.util.List;

public class Player extends Ant {
    private float timeSinceLastMove = 0;
    private static final float MOVEMENT_COOLDOWN = 0.1f; // Movement cooldown time in seconds
    private int clearedSquares = 0; // Counter for cleared squares

    public Player(float x, float y, QuadTree quadTree) {
        super(AntType.BLACK, AntRole.WORKER, x, y, quadTree);
    }

    @Override
    public void handleMovement(float deltaTime) {
        if (timeSinceLastMove > 0) {
            timeSinceLastMove -= deltaTime; // Decrease the cooldown timer
            return; // If cooldown is still active, don't move
        }

        float moveX = 0, moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveY += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveY -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveX -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveX += 1;
        }

        if (moveX != 0 || moveY != 0) {
            // Calculate the length of the movement
            float length = Math.max(Math.abs(moveX), Math.abs(moveY));
            if (length > 0) {
                // Normalize movement
                moveX /= length;
                moveY /= length;
            }

            // Calculate the number of steps based on the speed
            int steps = (int) (length / TerrainManager.CELL_SIZE) + 1; // +1 to ensure we clear the last point

            // Clear the space between the old position and the new position
            for (int i = 0; i <= steps; i++) {
                float x = (float)this.x + moveX * TerrainManager.CELL_SIZE * i;
                float y = (float)this.y + moveY * TerrainManager.CELL_SIZE * i;
                Point newPos = new Point(x, y);
                this.getQuadTree().insert(newPos);
                clearedSquares++; // Increment the cleared squares counter
            }

            // Update the player's position
            this.x += moveX * length * TerrainManager.CELL_SIZE;
            this.y += moveY * length * TerrainManager.CELL_SIZE;

            // Add the new position only if it's not already a cleared spot
            Point newPlayerPos = new Point(this.x, this.y);
            if (!this.getQuadTree().getRoot().getPoints().contains(newPlayerPos)) {
                this.getQuadTree().insert(newPlayerPos); // Mark the new position as empty
            }

            timeSinceLastMove = MOVEMENT_COOLDOWN; // Set the cooldown

            // Check if we need to spawn NPCs
            CheckClearedArea();
        }
    }

    private void CheckClearedArea() {
        if (clearedSquares >= 20) {
            CreateNPC();
            clearedSquares = 0; // Reset the cleared squares counter
        }
    }

    private void CreateNPC() {
        // Create NPC at a random cleared position
        List<Point> clearedPoints = this.getQuadTree().getRoot().getPoints();
        if (!clearedPoints.isEmpty()) {
            int index = (int) (Math.random() * clearedPoints.size());
            Point spawnPoint = clearedPoints.get(index);
            NPCAnt npc = new NPCAnt((float)spawnPoint.x, (float)spawnPoint.y, this.getQuadTree());
            App.NPCs.add(npc);
            App.getTerrainManager().spawnEnemyAnt((float)spawnPoint.x, (float)spawnPoint.y);
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle((float) x + 5, (float) y + 5, 5f);
    }
}