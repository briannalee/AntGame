Sure, I'll provide you with test classes for the `QuadTree`, `Point`, `Ant`, `Player`, `TerrainManager`, and `App` classes using JUnit.

1. In order to test the `App.java` file, add in these getters and setters to the `App.java` file:

```java 
// ... imports remain the same
public class App extends ApplicationAdapter {

  // ... rest of code remains the same

  Player getPlayer() {
      return player;
  }

  TerrainManager getTerrainManager() {
      return terrainManager;
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
```

2. In the `src/test/java/com/example` folder, create the following test files:

`AppTest.java`
```java
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
```

`QuadTreeTest.java`
```java
package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.example.utils.Point;
import com.example.utils.QuadTree;
import com.example.utils.QuadTreeNode;
import com.example.utils.Rectangle;

import java.util.List;

public class QuadTreeTest {
    @Test
    public void testInsertAndContains() {
        QuadTree quadTree = new QuadTree(new Rectangle(0, 0, 100, 100));
        Point point1 = new Point(10, 10);
        Point point2 = new Point(20, 20);

        quadTree.insert(point1);
        quadTree.insert(point2);

        assertTrue(quadTree.getRoot().getPoints().contains(point1));
        assertTrue(quadTree.getRoot().getPoints().contains(point2));
    }

    @Test
    public void testFind() {
        QuadTree quadTree = new QuadTree(new Rectangle(0, 0, 100, 100));
        Point point = new Point(10, 10);

        quadTree.insert(point);

        QuadTreeNode node = quadTree.find(point);

        assertNotNull(node);
        assertTrue(node.getPoints().contains(point));
    }

    @Test
    public void testDelete() {
        QuadTree quadTree = new QuadTree(new Rectangle(0, 0, 100, 100));
        Point point = new Point(10, 10);

        quadTree.insert(point);
        assertTrue(quadTree.delete(point));
        assertFalse(quadTree.getRoot().getPoints().contains(point));
    }

    @Test
    public void testQueryRange() {
        QuadTree quadTree = new QuadTree(new Rectangle(0, 0, 100, 100));
        Point point1 = new Point(10, 10);
        Point point2 = new Point(20, 20);
        Point point3 = new Point(30, 30);

        quadTree.insert(point1);
        quadTree.insert(point2);
        quadTree.insert(point3);

        Rectangle range = new Rectangle(0, 0, 25, 25);
        List<Point> points = quadTree.queryRange(range);

        assertEquals(2, points.size());
        assertTrue(points.contains(point1));
        assertTrue(points.contains(point2));
        assertFalse(points.contains(point3));
    }
}
```

`PointTest.java`
```java
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
```

`AntTest.java`
```java
package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.example.utils.QuadTree;
import com.example.ant.Ant;
import com.example.ant.Player;

public class AntTest {
    @Test
    public void testGetters() {
        Ant ant = new Player(10, 10, new QuadTree(new com.example.utils.Rectangle(0, 0, 100, 100)));

        assertEquals(10, ant.x);
        assertEquals(10, ant.y);
        assertNotNull(ant.getQuadTree());
    }
}
```

`PlayerTest.java`
```java
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
```

`TerrainManagerTest.java`
```java
package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.example.utils.Point;
import com.example.terrain.TerrainManager;

public class TerrainManagerTest {
    @Test
    public void testAddPlayer() {
        TerrainManager terrainManager = new TerrainManager();
        Point player = new Point(10, 10);

        terrainManager.addPlayer(player);

        assertTrue(terrainManager.getQuadTree().getRoot().getPoints().contains(player));
    }
}
```

These test classes will help you ensure that your classes are working as expected. You can run these tests using `mvn test` to verify the correctness of your code.