package com.example.ant;

import com.example.utils.Point;
import com.example.utils.QuadTree;

public abstract class Ant extends Point {
  private AntType type;
  private AntType.AntRole role;
  private int currentHealth;
  private QuadTree quadTree;

  public Ant(AntType type, AntType.AntRole role, float x, float y, QuadTree quadTree) {
    super(x, y);
    this.type = type;
    this.role = role;
    this.currentHealth = (int) (type.getHealth() * role.getHealthModifier());
    this.quadTree = quadTree;
  }

  public abstract void handleMovement(float deltaTime);

  public AntType getType() {
    return type;
  }

  public AntType.AntRole getRole() {
    return role;
  }

  public int getCurrentHealth() {
    return currentHealth;
  }

  public void setCurrentHealth(int currentHealth) {
    this.currentHealth = currentHealth;
  }

  public QuadTree getQuadTree() {
    return quadTree;
  }

  public float getSpeed() {
    return type.getMovementSpeed() * role.getMovementSpeedModifier();
  }
}