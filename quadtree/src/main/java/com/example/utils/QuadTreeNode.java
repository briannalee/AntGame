package com.example.utils;

import java.util.List;
import java.util.ArrayList;

public class QuadTreeNode {
  private static final int CAPACITY = 4;

  private Rectangle bounds;
  private List<Point> points;
  private QuadTreeNode[] children;

  public QuadTreeNode(Rectangle bounds) {
    this.bounds = bounds;
    this.points = new ArrayList<>();
    this.children = null; // Initially, nodes are leaf nodes
  }

  public void insert(Point point) {
    if (!bounds.contains(point)) {
      return; // Point outside this node's region
    }

    if (points.size() < CAPACITY) {
      if (!points.contains(point)) {
        points.add(point); // Add point only if it is not already present
      }
    } else {
      if (children == null) {
        subdivide(); // Split into four children if full
      }

      for (QuadTreeNode child : children) {
        child.insert(point);
      }
    }
  }

  public QuadTreeNode find(Point point) {
    if (!bounds.contains(point)) {
      return null; // Point not in this subtree
    }

    if (points.contains(point)) {
      return this; // Point found
    }

    if (children != null) {
      for (QuadTreeNode child : children) {
        QuadTreeNode found = child.find(point);
        if (found != null) {
          return found;
        }
      }
    }

    return null; // Not found
  }

  private void subdivide() {
    double x = bounds.x;
    double y = bounds.y;
    double w = bounds.width / 2;
    double h = bounds.height / 2;

    children = new QuadTreeNode[4];
    children[0] = new QuadTreeNode(new Rectangle(x, y, w, h));
    children[1] = new QuadTreeNode(new Rectangle(x + w, y, w, h));
    children[2] = new QuadTreeNode(new Rectangle(x, y + h, w, h));
    children[3] = new QuadTreeNode(new Rectangle(x + w, y + h, w, h));
  }

  public boolean delete(Point point) {
    if (!bounds.contains(point)) {
      return false; // Point outside this node's region
    }

    if (points.remove(point)) {
      return true; // Point removed
    }

    if (children != null) {
      for (QuadTreeNode child : children) {
        if (child.delete(point)) {
          return true;
        }
      }
    }

    return false; // Not found
  }

  public List<Point> queryRange(Rectangle range) {
    List<Point> found = new ArrayList<>();

    if (!bounds.intersects(range)) {
      return found; // No intersection with this node's region
    }

    for (Point p : points) {
      if (range.contains(p)) {
        found.add(p);
      }
    }

    if (children != null) {
      for (QuadTreeNode child : children) {
        found.addAll(child.queryRange(range));
      }
    }

    return found;
  }

  public Rectangle getBounds() {
    return bounds;
  }

  public List<Point> getPoints() {
    return points;
  }

  public QuadTreeNode[] getChildren() {
    return children;
  }

}
