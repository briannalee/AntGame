package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.example.ant.Player;
import com.example.utils.QuadTree;
import com.example.utils.Rectangle;

import static org.mockito.Mockito.*;

public class PlayerTest {

    @BeforeEach
    public void setUp() {
        // Mock Gdx.input
        Gdx.input = Mockito.mock(Input.class);
    }

    @Test
    public void testGetSpeed() {
        Player player = new Player(10, 10, new QuadTree(new Rectangle(0, 0, 100, 100)));

        assertEquals(8, player.getSpeed());
    }

    @Test
    public void testHandleMovement() {
        Player player = new Player(10, 10, new QuadTree(new Rectangle(0, 0, 100, 100)));

        // Simulate key press for movement (for example, W for up and D for right)
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(true);  // Move up
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);  // Move right

        // Call handleMovement, which should handle the mock key presses
        player.handleMovement(0.1f);

        // Assert that the player has moved from its initial position
        assertNotEquals(10, player.x); // Ensure x position has changed
        assertNotEquals(10, player.y); // Ensure y position has changed
    }
}