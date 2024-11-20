package com.example.utils;

import java.util.List;
import java.util.ArrayList;

public class QuadTreeNode {
  private static final int CAPACITY = 4;

  private Rectangle bounds;
  private List<Point> points;
  private QuadTreeNode[] children;

  // Constructor initializes the bounds of the node and initializes the points list and children array
  public QuadTreeNode(Rectangle bounds) {
    this.bounds = bounds;
    this.points = new ArrayList<>();
    this.children = null;
  }

  // Inserts a point into the quadtree
  public void insert(Point point) {
    // If the point is not within the bounds of this node, return
    if (!bounds.contains(point)) {
      return;
    }

    // If the node has fewer than CAPACITY points, add the point to the list
    if (points.size() < CAPACITY) {
      if (!points.contains(point)) {
        points.add(point);
      }
    } else {
      // If the node has CAPACITY points, subdivide the node into four children
      if (children == null) {
        subdivide();
      }

      // Insert the point into the appropriate child node
      for (QuadTreeNode child : children) {
        child.insert(point);
      }
    }
  }

  // Finds a point in the quadtree
  public QuadTreeNode find(Point point) {
    // If the point is not within the bounds of this node, return null
    if (!bounds.contains(point)) {
      return null;
    }

    // If the point is in this node's list, return this node
    if (points.contains(point)) {
      return this;
    }

    // If the node has children, search the children for the point
    if (children != null) {
      for (QuadTreeNode child : children) {
        QuadTreeNode found = child.find(point);
        if (found != null) {
          return found;
        }
      }
    }

    // If the point is not found, return null
    return null;
  }

  // Subdivides the node into four children
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

  // Deletes a point from the quadtree
  public boolean delete(Point point) {
    // If the point is not within the bounds of this node, return false
    if (!bounds.contains(point)) {
      return false;
    }

    // If the point is in this node's list, remove it and return true
    if (points.remove(point)) {
      return true;
    }

    // If the node has children, search the children for the point
    if (children != null) {
      for (QuadTreeNode child : children) {
        if (child.delete(point)) {
          return true;
        }
      }
    }

    // If the point is not found, return false
    return false;
  }

  // Queries the quadtree for points within a given range
  public List<Point> queryRange(Rectangle range) {
    List<Point> found = new ArrayList<>();

    // If the range does not intersect this node's bounds, return an empty list
    if (!bounds.intersects(range)) {
      return found;
    }

    // Add points within the range to the found list
    for (Point p : points) {
      if (range.contains(p)) {
        found.add(p);
      }
    }

    // If the node has children, query the children for points within the range
    if (children != null) {
      for (QuadTreeNode child : children) {
        found.addAll(child.queryRange(range));
      }
    }

    // Return the list of found points
    return found;
  }

  // Getter for the bounds of the node
  public Rectangle getBounds() {
    return bounds;
  }

  // Getter for the points list of the node
  public List<Point> getPoints() {
    return points;
  }

  // Getter for the children array of the node
  public QuadTreeNode[] getChildren() {
    return children;
  }
}