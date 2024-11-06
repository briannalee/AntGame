package com.example.ant;

public enum AntType {
    RED(100, 10, 10),
    BLACK(80, 10, 8);

    private final int health;
    private final int movementSpeed;
    private final int attack;

    AntType(int health, int movementSpeed, int attack) {
        this.health = health;
        this.movementSpeed = movementSpeed;
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public int getAttack() {
        return attack;
    }

    public enum AntRole {
        QUEEN(1.5f, 0.5f, 0.2f),
        FIGHTER(1.2f, 1.2f, 1.2f),
        WORKER(0.8f, 0.8f, 0.8f);

        private final float healthModifier;
        private final float movementSpeedModifier;
        private final float attackModifier;

        AntRole(float healthModifier, float movementSpeedModifier, float attackModifier) {
            this.healthModifier = healthModifier;
            this.movementSpeedModifier = movementSpeedModifier;
            this.attackModifier = attackModifier;
        }

        public float getHealthModifier() {
            return healthModifier;
        }

        public float getMovementSpeedModifier() {
            return movementSpeedModifier;
        }

        public float getAttackModifier() {
            return attackModifier;
        }
    }
}