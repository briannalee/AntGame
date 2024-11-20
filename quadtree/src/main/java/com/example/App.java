package com.example;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.example.ant.NPCAnt;
import com.example.ant.Player;
import com.example.terrain.TerrainManager;

public class App extends ApplicationAdapter {
    private TerrainManager terrainManager;
    private Player player;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Graphics graphics;
    public static ArrayList<NPCAnt> NPCs;

    // Main method to start the application
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Underground Terrain Simulation");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new App(), config); // Pass Gdx.graphics
    }

    // Create method to initialize the application
    @Override
    public void create() {
        NPCs = new ArrayList<NPCAnt>();
        graphics = Gdx.graphics;
        camera = new OrthographicCamera(graphics.getWidth(), graphics.getHeight());
        camera.setToOrtho(false, graphics.getWidth(), graphics.getHeight());
        shapeRenderer = new ShapeRenderer();

        terrainManager = new TerrainManager();
        // Set the player position to be the center of the QuadTree bounds
        player = terrainManager.addPlayer();

        camera.position.set((float)player.getX(), (float)player.getY(), 0);
    }

    // Render method to update the application state and draw the scene
    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update player movement
        player.handleMovement(deltaTime);

        // Update camera position based on player position
        updateCamera();

        // Update and render the camera
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Draw everything
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        terrainManager.drawTerrain(shapeRenderer, camera.position.x, camera.position.y);
        player.draw(shapeRenderer);
        for (NPCAnt npc : NPCs) {
            npc.draw(shapeRenderer);
        }
        shapeRenderer.end();
    }

    // Dispose method to clean up resources
    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    // Getter for the player
    Player getPlayer() {
        return player;
    }

    // Getter for the terrain manager
    TerrainManager getTerrainManager() {
        return terrainManager;
    }

    // Getter for the camera
    OrthographicCamera getCamera() {
        return camera;
    }

    // Setter for the camera
    void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    // Setter for the player
    void setPlayer(Player player) {
        this.player = player;
    }

    // Setter for the terrain manager
    void setTerrainManager(TerrainManager terrainManager) {
        this.terrainManager = terrainManager;
    }

    // Setter for the shape renderer
    void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    private void updateCamera() {
        // Screen dimensions
        float halfScreenWidth = camera.viewportWidth / 2;
        float halfScreenHeight = camera.viewportHeight / 2;
        float margin = 15; // Margin before the camera moves

        // Player position
        float playerX = (float) player.getX();
        float playerY = (float) player.getY();

        // Camera's current boundaries
        float leftBound = camera.position.x - halfScreenWidth + margin;
        float rightBound = camera.position.x + halfScreenWidth - margin;
        float bottomBound = camera.position.y - halfScreenHeight + margin;
        float topBound = camera.position.y + halfScreenHeight - margin;

        // Adjust camera position to follow the player
        if (playerX < leftBound) {
            camera.position.x += playerX - leftBound;
        } else if (playerX > rightBound) {
            camera.position.x += playerX - rightBound;
        }

        if (playerY < bottomBound) {
            camera.position.y += playerY - bottomBound;
        } else if (playerY > topBound) {
            camera.position.y += playerY - topBound;
        }
    }

}