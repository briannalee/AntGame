package com.example;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class QuadTreeTest {

    private QuadTree quadTree;
    private Rectangle bounds;

    @Before
    public void setUp() {
        bounds = new Rectangle(0, 0, 100, 100);
        quadTree = new QuadTree(bounds);
    }

    @Test
    public void testInsertAndFind() {
        Point p1 = new Point(10, 10);
        Point p2 = new Point(90, 90);
        Point p3 = new Point(50, 50);
        
        quadTree.insert(p1);
        quadTree.insert(p2);
        quadTree.insert(p3);

        assertNotNull(quadTree.find(p1));
        assertNotNull(quadTree.find(p2));
        assertNotNull(quadTree.find(p3));
    }

    @Test
    public void testDelete() {
        Point p1 = new Point(10, 10);
        Point p2 = new Point(90, 90);
        Point p3 = new Point(50, 50);
        
        quadTree.insert(p1);
        quadTree.insert(p2);
        quadTree.insert(p3);

        assertTrue(quadTree.delete(p1));
        assertNull(quadTree.find(p1));
        
        assertTrue(quadTree.delete(p2));
        assertNull(quadTree.find(p2));
        
        assertTrue(quadTree.delete(p3));
        assertNull(quadTree.find(p3));
    }

    @Test
    public void testQueryRange() {
        Point p1 = new Point(10, 10);
        Point p2 = new Point(90, 90);
        Point p3 = new Point(50, 50);
        
        quadTree.insert(p1);
        quadTree.insert(p2);
        quadTree.insert(p3);

        Rectangle range = new Rectangle(0, 0, 60, 60);
        List<Point> foundPoints = quadTree.queryRange(range);

        assertTrue(foundPoints.contains(p1));
        assertFalse(foundPoints.contains(p2));
        assertTrue(foundPoints.contains(p3));
    }

    @Test
    public void testInsertOutsideBounds() {
        Point p1 = new Point(-10, -10);
        Point p2 = new Point(110, 110);

        quadTree.insert(p1);
        quadTree.insert(p2);

        assertNull(quadTree.find(p1));
        assertNull(quadTree.find(p2));
    }

    @Test
    public void testQueryEmptyRange() {
        Rectangle range = new Rectangle(0, 0, 0, 0);
        List<Point> foundPoints = quadTree.queryRange(range);

        assertTrue(foundPoints.isEmpty());
    }

    @Test
    public void testDeleteNonExistentPoint() {
        Point p1 = new Point(10, 10);
        Point p2 = new Point(20, 20);
        
        quadTree.insert(p1);
        
        assertFalse(quadTree.delete(p2));
    }
}