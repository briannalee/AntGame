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

    public int countClearedPoints() {
        return countPoints(root);
    }

    private int countPoints(QuadTreeNode node) {
        int count = node.getPoints().size();
        if (node.getChildren() != null) {
            for (QuadTreeNode child : node.getChildren()) {
                count += countPoints(child);
            }
        }
        return count;
    }

    public QuadTreeNode getRoot() {
      return root;
    }
}