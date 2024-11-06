package com.example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.ant.Player;
import com.example.terrain.TerrainManager;

public class App extends ApplicationAdapter {
    private TerrainManager terrainManager;
    private Player player;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Graphics graphics;



    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Underground Terrain Simulation");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new App(), config); // Pass Gdx.graphics
    }

    @Override
    public void create() {
        graphics = Gdx.graphics;
        camera = new OrthographicCamera(graphics.getWidth(), graphics.getHeight());
        camera.setToOrtho(false, graphics.getWidth(), graphics.getHeight());
        shapeRenderer = new ShapeRenderer();

        terrainManager = new TerrainManager();
        player = new Player(graphics.getWidth() / 2, graphics.getHeight() / 2, terrainManager.getQuadTree());
        terrainManager.addPlayer(player);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        terrainManager.drawTerrain(shapeRenderer);
        player.draw(shapeRenderer);
        shapeRenderer.end();

        player.handleMovement(deltaTime);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    Player getPlayer() {
        return player;
    }

    TerrainManager getTerrainManager() {
        return terrainManager;
    }

    OrthographicCamera getCamera() {
        return camera;
    }

    void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    void setPlayer(Player player) {
        this.player = player;
    }

    void setTerrainManager(TerrainManager terrainManager) {
        this.terrainManager = terrainManager;
    }

    void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }
}