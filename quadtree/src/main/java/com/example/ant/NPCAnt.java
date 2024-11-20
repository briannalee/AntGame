package com.example.ant;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.ant.AntType.AntRole;
import com.example.utils.QuadTree;

public class NPCAnt extends Ant {
    public NPCAnt(float x, float y, QuadTree quadTree) {
        super(AntType.BLACK, AntRole.WORKER, x, y, quadTree);
    }

    @Override
    public void handleMovement(float deltaTime) {
        // Implement NPC movement logic if needed
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle((float) x + 5, (float) y + 5, 5f);
    }
}