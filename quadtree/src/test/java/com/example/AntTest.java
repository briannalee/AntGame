package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.example.utils.QuadTree;
import com.example.ant.Ant;
import com.example.ant.Player;

public class AntTest {
    @Test
    public void testGetters() {
        Ant ant = new Player(10, 10, new QuadTree(new com.example.utils.Rectangle(0, 0, 100, 100)));

        assertEquals(10, ant.x);
        assertEquals(10, ant.y);
        assertNotNull(ant.getQuadTree());
    }
}