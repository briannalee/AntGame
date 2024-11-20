package com.example.ant;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.ant.AntType.AntRole;
import com.example.terrain.TerrainManager;
import com.example.utils.Point;
import com.example.utils.QuadTree;

import java.util.List;
import java.util.Random;

public class EnemyAnt extends Ant {
    private static final float MOVEMENT_COOLDOWN = 0.5f; // Movement cooldown time in seconds
    private float timeSinceLastMove = 0;
    private Random random = new Random();

    public EnemyAnt(float x, float y, QuadTree quadTree) {
        super(AntType.RED, AntRole.FIGHTER, x, y, quadTree);
    }

    @Override
    public void handleMovement(float deltaTime) {
        if (timeSinceLastMove > 0) {
            timeSinceLastMove -= deltaTime; // Decrease the cooldown timer
            return; // If cooldown is still active, don't move
        }

        // Get the list of cleared points from the quadtree
        List<Point> clearedPoints = getQuadTree().queryRange(new com.example.utils.Rectangle(x, y, 100, 100));
        if (clearedPoints.isEmpty()) {
            return; // No cleared points to move to
        }

        // Choose a random cleared point to move to
        Point targetPoint = clearedPoints.get(random.nextInt(clearedPoints.size()));

        // Move towards the target point
        float moveX = (float)(targetPoint.x - x);
        float moveY = (float)(targetPoint.y - y);
        float length = Math.max(Math.abs(moveX), Math.abs(moveY));
        if (length > 0) {
            moveX /= length;
            moveY /= length;
        }

        // Update the position
        x += moveX * TerrainManager.CELL_SIZE;
        y += moveY * TerrainManager.CELL_SIZE;

        // Set the cooldown
        timeSinceLastMove = MOVEMENT_COOLDOWN;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.circle((float) x + 5, (float) y + 5, 5f);
    }
}