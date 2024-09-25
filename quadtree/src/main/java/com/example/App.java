package com.example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class App extends ApplicationAdapter {

    private static QuadTree quadTree;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Point player;
    private static final int CELL_SIZE = 10; // Size of the cells in the grid
    private float timeSinceLastMove = 0;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Underground Terrain Simulation");
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new App(), config);
    }

    @Override
    public void create() {
        quadTree = new QuadTree(new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer = new ShapeRenderer();

        // Spawn player in the middle of the screen
        player = new Point(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        quadTree.insert(player); // Mark the player's initial position as empty
    }

    private void handleMovement(float deltaTime) {
        timeSinceLastMove += deltaTime;
        if (timeSinceLastMove < 0.1f) {
            return; // Wait for a short time before allowing movement
        }
        // Reset the timer
        timeSinceLastMove = 0;
        // Move the player based on keyboard input
        int moveX = 0, moveY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveY = CELL_SIZE;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveY = -CELL_SIZE;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveX = -CELL_SIZE;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX = CELL_SIZE;
        }

        Point newPlayerPos = new Point(player.x + moveX, player.y + moveY);
        if (quadTree.getRoot().getBounds().contains(newPlayerPos)) {
            player = newPlayerPos;

            // Add the new position only if it's not already a cleared spot
            if (!quadTree.getRoot().getPoints().contains(player)) {
                quadTree.insert(player); // Mark the new position as empty
            }
        }
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime(); // Get the time since the last frame
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawTerrain();
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle((float) player.x+CELL_SIZE/2, (float) player.y+CELL_SIZE/2, CELL_SIZE / 2);
        shapeRenderer.end();

        // Handle movement
        handleMovement(deltaTime);
    }

    private void drawTerrain() {
        // Draw the entire terrain as brown first
        shapeRenderer.setColor(new Color(0.545f, 0.271f, 0.075f, 1)); // Brown color
        for (int x = 0; x < Gdx.graphics.getWidth(); x += CELL_SIZE) {
            for (int y = 0; y < Gdx.graphics.getHeight(); y += CELL_SIZE) {
                shapeRenderer.rect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }

        // Draw the cleared terrain as light brown
        shapeRenderer.setColor(new Color(0.824f, 0.706f, 0.549f, 1)); // Light brown color
        drawClearedTerrain(quadTree.getRoot());
    }

    private void drawClearedTerrain(QuadTreeNode node) {
        if (node == null)
            return;

        for (Point p : node.getPoints()) {
            shapeRenderer.rect((float) p.x, (float) p.y, CELL_SIZE, CELL_SIZE);
        }

        if (node.getChildren() != null) {
            for (QuadTreeNode child : node.getChildren()) {
                drawClearedTerrain(child);
            }
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
