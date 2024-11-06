package com.example.ant;

import com.example.utils.Point;
import com.example.utils.QuadTree;

public class NPC extends Ant {
    public NPC(float x, float y, QuadTree quadTree) {
        super(AntType.RED, AntType.AntRole.WORKER, x, y, quadTree);
    }

    @Override
    public void handleMovement(float deltaTime) {
        // NPC movement logic can be added here if needed
    }

    public static void createNPC(float x, float y, QuadTree quadTree) {
        NPC npc = new NPC(x, y, quadTree);
        quadTree.insert(new Point(npc.getX(), npc.getY()));
    }
}