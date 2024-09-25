package com.example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Underground Terrain Simulation");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new App(), config);
    }

    @Override
    public void create() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer = new ShapeRenderer();

        terrainManager = new TerrainManager();
        player = new Player(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, terrainManager.getQuadTree());
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
}