package com.example.utils;

import java.util.List;

public class QuadTree {
    private QuadTreeNode root;

    // Constructor initializes the root node with the given bounds
    public QuadTree(Rectangle bounds) {
        this.root = new QuadTreeNode(bounds);
    }

    // Inserts a point into the quadtree
    public void insert(Point point) {
        if (!root.getPoints().contains(point)) {
            root.insert(point);
        }
    }

    // Finds a point in the quadtree
    public QuadTreeNode find(Point point) {
        return root.find(point);
    }

    // Deletes a point from the quadtree
    public boolean delete(Point point) {
        return root.delete(point);
    }

    // Queries the quadtree for points within a given range
    public List<Point> queryRange(Rectangle range) {
        return root.queryRange(range);
    }

    // Getter for the root node of the quadtree
    public QuadTreeNode getRoot() {
      return root;
    }

    // Checks if a given rectangle is entirely contained within the bounds of the quadtree
    public boolean contains(Rectangle rectangle) {
        return root.getBounds().contains(rectangle);
    }
}