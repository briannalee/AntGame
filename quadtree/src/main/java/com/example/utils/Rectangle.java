package com.example.utils;

public class Rectangle {
  public double x;
  public double y;
  public double width;
  public double height;

  public Rectangle(double x, double y, double width, double height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
  }

  public boolean contains(Point point) {
      return (point.x >= x && point.x <= x + width &&
              point.y >= y && point.y <= y + height);
  }

  public boolean intersects(Rectangle range) {
      return !(range.x > x + width || 
               range.x + range.width < x || 
               range.y > y + height || 
               range.y + range.height < y);
  }
}