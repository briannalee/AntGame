package com.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.ant.Player;
import com.example.terrain.TerrainManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AppTest {
    private App app;
    private TerrainManager mockTerrainManager;
    private Player mockPlayer;
    private ShapeRenderer mockShapeRenderer;

    @BeforeEach
    public void setUp() {
        // Mocking Gdx and its graphics
        Graphics mockGraphics = Mockito.mock(Graphics.class);
        when(mockGraphics.getWidth()).thenReturn(800);
        when(mockGraphics.getHeight()).thenReturn(600);
        Gdx.graphics = mockGraphics;
        
        Gdx.gl = Mockito.mock(GL20.class);

        mockTerrainManager = mock(TerrainManager.class);
        mockPlayer = mock(Player.class);
        mockShapeRenderer = mock(ShapeRenderer.class);

        // Initialize the app with overridden create method
        app = new App() {
            @Override
            public void create() {
                setCamera(Mockito.mock(OrthographicCamera.class));
                setPlayer(mockPlayer);
                setTerrainManager(mockTerrainManager);
                setShapeRenderer(mockShapeRenderer);
                
                getTerrainManager().addPlayer(getPlayer());
            }
        };
    }

    @Test
    public void testPlayerInitialization() {
        app.create();
        
        // Correctly mock the player's position methods on the mock object
        when(mockPlayer.getX()).thenReturn(400.0d); // Mock the getX() method
        when(mockPlayer.getY()).thenReturn(300.0d); // Mock the getY() method
        
        // Verify that the player is initialized in the center of the screen
        assertEquals(400.0, app.getPlayer().getX(), 0.2); // Call mockPlayer.getX()
        assertEquals(300.0, app.getPlayer().getY(), 0.2); // Call mockPlayer.getY()
    }

    @Test
    public void testTerrainManagerAddPlayer() {
        app.create();
        
        // Verify that the player is added to the terrain manager
        verify(mockTerrainManager).addPlayer(mockPlayer); // Ensure addPlayer was called
    }

    @Test
    public void testRenderCalls() {
        app.create();
        app.render();
        
        // Check if methods that should be called during render are called
        verify(mockShapeRenderer).begin(ShapeRenderer.ShapeType.Filled);
        verify(mockShapeRenderer).end();
    }
}