package com.example.utils;

import java.util.List;

public class QuadTree {
    private QuadTreeNode root;

    public QuadTree(Rectangle bounds) {
        this.root = new QuadTreeNode(bounds);
    }

    public void insert(Point point) {
        if (!root.getPoints().contains(point)) {
            root.insert(point);
        }
    }

    public QuadTreeNode find(Point point) {
        return root.find(point);
    }

    public boolean delete(Point point) {
        return root.delete(point);
    }

    public List<Point> queryRange(Rectangle range) {
        return root.queryRange(range);
    }

    public QuadTreeNode getRoot() {
      return root;
    }
}