package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.example.utils.Point;
import com.example.terrain.TerrainManager;

public class TerrainManagerTest {
    @Test
    public void testAddPlayer() {
        TerrainManager terrainManager = new TerrainManager();
        Point player = new Point(10, 10);

        terrainManager.addPlayer(player);

        assertTrue(terrainManager.getQuadTree().getRoot().getPoints().contains(player));
    }
}