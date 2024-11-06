package com.example.ant;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.App;
import com.example.utils.Point;
import com.example.utils.QuadTree;

public class NPC extends Ant {
    public NPC(float x, float y, QuadTree quadTree) {
        super(AntType.RED, AntType.AntRole.WORKER, x, y, quadTree);
        App.NPCs.add(this);
    }

    @Override
    public void handleMovement(float deltaTime) {
        // NPC movement logic can be added here if needed
    }

    public static void createNPC(float x, float y, QuadTree quadTree) {
        NPC npc = new NPC(x, y, quadTree);
        quadTree.insert(new Point(npc.getX(), npc.getY()));
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle((float) x + 5, (float) y + 5, 5f);
    }
}