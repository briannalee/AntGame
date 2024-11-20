package com.example.terrain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.ant.EnemyAnt;
import com.example.ant.Player;
import com.example.utils.Point;
import com.example.utils.QuadTree;
import com.example.utils.QuadTreeNode;
import com.example.utils.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class TerrainManager {
    public static final int CELL_SIZE = 10;
    private static final int CHUNK_SIZE = 1000; // Size of each terrain chunk (adjustable)
    private QuadTree quadTree;
    private List<EnemyAnt> enemyAnts;

    public TerrainManager() {
        // Initialize the quad tree with a large initial bounds
        quadTree = new QuadTree(new Rectangle(0, 0, CHUNK_SIZE * 10, CHUNK_SIZE * 10));
        enemyAnts = new ArrayList<>();
    }

    public Player addPlayer() {
        // Set the player position to be the center of the QuadTree bounds
        float centerX = (float)(quadTree.getRoot().getBounds().x + quadTree.getRoot().getBounds().width / 2);
        float centerY = (float)(quadTree.getRoot().getBounds().y + quadTree.getRoot().getBounds().height / 2);

        // Initialize the player at the center of the QuadTree
        Player player = new Player(centerX, centerY, quadTree);
        quadTree.insert(player);
        return player;
    }

    public void drawTerrain(ShapeRenderer shapeRenderer, float cameraX, float cameraY) {
        // Calculate the visible area based on the camera position
        Rectangle visibleArea = new Rectangle(cameraX - 400, cameraY - 300, 800, 600);

        // Ensure that terrain is loaded dynamically based on the camera position
        loadNearbyChunks(visibleArea);

        // Draw the entire terrain as brown first
        shapeRenderer.setColor(new Color(0.545f, 0.271f, 0.075f, 1)); // Brown color
        for (float x = (float) visibleArea.x; x <= visibleArea.x + visibleArea.width; x += CELL_SIZE) {
            for (float y = (float) visibleArea.y; y <= visibleArea.y + visibleArea.height; y += CELL_SIZE) {
                shapeRenderer.rect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }

        // Draw the cleared terrain as light brown
        shapeRenderer.setColor(new Color(0.824f, 0.706f, 0.549f, 1)); // Light brown color
        drawClearedTerrain(shapeRenderer, quadTree.getRoot(), visibleArea);

        // Draw enemy ants
        for (EnemyAnt enemyAnt : enemyAnts) {
            enemyAnt.draw(shapeRenderer);
        }
    }

    private void loadNearbyChunks(Rectangle visibleArea) {
        // Ensure that terrain chunks are loaded around the player
        float minX = (float)(visibleArea.x - visibleArea.width);
        float maxX = (float)(visibleArea.x + visibleArea.width);
        float minY = (float)(visibleArea.y - visibleArea.height);
        float maxY = (float)(visibleArea.y + visibleArea.height);

        // Iterate over the area surrounding the camera to load new chunks
        for (float x = minX; x <= maxX; x += CHUNK_SIZE) {
            for (float y = minY; y <= maxY; y += CHUNK_SIZE) {
                Rectangle chunkBounds = new Rectangle(x, y, CHUNK_SIZE, CHUNK_SIZE);
                if (!quadTree.contains(chunkBounds)) {
                    // If the chunk is not already in the QuadTree, create it
                    generateTerrainChunk(chunkBounds);
                }
            }
        }
    }

    private void generateTerrainChunk(Rectangle chunkBounds) {
        // Simulate terrain generation by adding points to the QuadTree within the chunk's bounds
        for (float x = (float)chunkBounds.x; x < chunkBounds.x + chunkBounds.width; x += CELL_SIZE) {
            for (float y = (float)chunkBounds.y; y < chunkBounds.y + chunkBounds.height; y += CELL_SIZE) {
                Point point = new Point(x, y);
                quadTree.insert(point);
            }
        }
    }

    private void drawClearedTerrain(ShapeRenderer shapeRenderer, QuadTreeNode node, Rectangle visibleArea) {
        if (node == null || !visibleArea.intersects(node.getBounds())) {
            return;
        }

        for (Point p : node.getPoints()) {
            if (visibleArea.contains(p)) {
                shapeRenderer.rect((float) p.x, (float) p.y, CELL_SIZE, CELL_SIZE);
            }
        }

        if (node.getChildren() != null) {
            for (QuadTreeNode child : node.getChildren()) {
                drawClearedTerrain(shapeRenderer, child, visibleArea);
            }
        }
    }

    public QuadTree getQuadTree() {
        return quadTree;
    }

    public List<EnemyAnt> getEnemyAnts() {
        return enemyAnts;
    }

    public void spawnEnemyAnt(float x, float y) {
        EnemyAnt enemyAnt = new EnemyAnt(x, y, quadTree);
        enemyAnts.add(enemyAnt);
    }
}