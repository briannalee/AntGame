package com.example.terrain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.ant.Player;
import com.example.utils.Point;
import com.example.utils.QuadTree;
import com.example.utils.QuadTreeNode;
import com.example.utils.Rectangle;

public class TerrainManager {
    public static final int CELL_SIZE = 10;
    private QuadTree quadTree;

    public TerrainManager() {
        // Initialize the quad tree with a large initial bounds
        quadTree = new QuadTree(new Rectangle(0, 0, 10000, 10000));
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
}