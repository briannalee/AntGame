package com.example.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.utils.Point;
import com.example.utils.QuadTreeNode;
import com.example.utils.Rectangle;
import com.example.utils.QuadTree;

public class TerrainManager {
    public static final int CELL_SIZE = 10;
    private QuadTree quadTree;

    public TerrainManager() {
        quadTree = new QuadTree(new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    public void addPlayer(Point player) {
        quadTree.insert(player);
    }

    public void drawTerrain(ShapeRenderer shapeRenderer) {
        // Draw the entire terrain as brown first
        shapeRenderer.setColor(new Color(0.545f, 0.271f, 0.075f, 1)); // Brown color
        for (int x = 0; x < Gdx.graphics.getWidth(); x += CELL_SIZE) {
            for (int y = 0; y < Gdx.graphics.getHeight(); y += CELL_SIZE) {
                shapeRenderer.rect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }

        // Draw the cleared terrain as light brown
        shapeRenderer.setColor(new Color(0.824f, 0.706f, 0.549f, 1)); // Light brown color
        drawClearedTerrain(shapeRenderer, quadTree.getRoot());
    }

    private void drawClearedTerrain(ShapeRenderer shapeRenderer, QuadTreeNode node) {
        if (node == null)
            return;

        for (Point p : node.getPoints()) {
            shapeRenderer.rect((float) p.x, (float) p.y, CELL_SIZE, CELL_SIZE);
        }

        if (node.getChildren() != null) {
            for (QuadTreeNode child : node.getChildren()) {
                drawClearedTerrain(shapeRenderer, child);
            }
        }
    }

    public QuadTree getQuadTree() {
        return quadTree;
    }

}