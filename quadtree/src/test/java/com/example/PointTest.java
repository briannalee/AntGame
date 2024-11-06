package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.example.utils.Point;


public class PointTest {
  @Test
  public void testEquals() {
      Point point1 = new Point(100, 100);
      Point point2 = new Point(100, 100);
      Point point3 = new Point(200, 200);

      assertTrue(point1.equals(point2));
      assertFalse(point1.equals(point3));
  }
}