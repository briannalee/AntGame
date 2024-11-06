package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.example.utils.Point;
import com.example.utils.QuadTree;
import com.example.utils.QuadTreeNode;
import com.example.utils.Rectangle;

import java.util.List;

public class QuadTreeTest {
    @Test
    public void testInsertAndContains() {
        QuadTree quadTree = new QuadTree(new Rectangle(0, 0, 100, 100));
        Point point1 = new Point(10, 10);
        Point point2 = new Point(20, 20);

        quadTree.insert(point1);
        quadTree.insert(point2);

        assertTrue(quadTree.getRoot().getPoints().contains(point1));
        assertTrue(quadTree.getRoot().getPoints().contains(point2));
    }

    @Test
    public void testFind() {
        QuadTree quadTree = new QuadTree(new Rectangle(0, 0, 100, 100));
        Point point = new Point(10, 10);

        quadTree.insert(point);

        QuadTreeNode node = quadTree.find(point);

        assertNotNull(node);
        assertTrue(node.getPoints().contains(point));
    }

    @Test
    public void testDelete() {
        QuadTree quadTree = new QuadTree(new Rectangle(0, 0, 100, 100));
        Point point = new Point(10, 10);

        quadTree.insert(point);
        assertTrue(quadTree.delete(point));
        assertFalse(quadTree.getRoot().getPoints().contains(point));
    }

    @Test
    public void testQueryRange() {
        QuadTree quadTree = new QuadTree(new Rectangle(0, 0, 100, 100));
        Point point1 = new Point(10, 10);
        Point point2 = new Point(20, 20);
        Point point3 = new Point(30, 30);

        quadTree.insert(point1);
        quadTree.insert(point2);
        quadTree.insert(point3);

        Rectangle range = new Rectangle(0, 0, 25, 25);
        List<Point> points = quadTree.queryRange(range);

        assertEquals(2, points.size());
        assertTrue(points.contains(point1));
        assertTrue(points.contains(point2));
        assertFalse(points.contains(point3));
    }
}