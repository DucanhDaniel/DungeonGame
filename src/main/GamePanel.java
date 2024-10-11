package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.awt.Color.BLACK;

public class GamePanel extends JPanel implements Runnable{
    // Screen settings
    public final int originalTileSize = 16; // 16x16 pixels
    public final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 pixels
    public final int maxScreenCol = 16; // 16 tiles
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // World map parameters
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    // System
    KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;
    public TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssertSetter assertSetter = new AssertSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler;

    // Entity and Object
    public Entity[] superObject = new Entity[10];
    public Player player = new Player(this, keyHandler);
    public Entity[] npc = new Entity[10];
    ArrayList<Entity> entityList= new ArrayList<>();

    // Game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    // Sound
    Sound music = new Sound();
    Sound soundEffect = new Sound();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(BLACK);
        this.setDoubleBuffered(true); // Make game render better
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.eventHandler = new EventHandler(this);
    }

    public void setupGame() {
        // Place object
        assertSetter.setObject();

        // Place NPCs
        assertSetter.setNPC();

        // Play background music
//        playMusic(0);

        // Set gameState
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread =  new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS; //0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            // 1. Update information such as character position
            update();
            // 2. Draw the screen with the updated information
            repaint();

            double remainingTime = nextDrawTime - System.nanoTime();
            remainingTime = remainingTime/1000000;

            if (remainingTime < 0) remainingTime = 0;

            try {
                Thread.sleep((long) remainingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            nextDrawTime += drawInterval;
        }
    }

    public void update() {
        if (gameState == playState) {
            // Update NPC
            for (Entity entity : npc) {
                if (entity != null) entity.update();
            }

            // Update player
            player.update();
        }
        if (gameState == pauseState) {
            // Nothing
        }
    }

    public void paintComponent(Graphics g) {
        // Debug
        long drawStart = System.currentTimeMillis();

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Title screen
        if (gameState == titleState) {
            // Draw ui
            ui.draw(g2);
        }
        else {
            // Draw tile
            tileManager.draw(g2);

            // Add entity into entityList
            entityList.add(player);
            for (Entity entity : npc) {
                if (entity!= null) entityList.add(entity);
            }

            for (Entity entity : superObject) {
                if (entity!= null) entityList.add(entity);
            }

            // Sort
            entityList.sort(Comparator.comparingInt(o -> o.worldY));

            // Draw entity
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            entityList.clear();


            // Draw UI
            ui.draw(g2);

            g2.dispose(); //Dispose of this graphics context and release resources that it is using
        }
        //Debug
        long drawEnd = System.currentTimeMillis();
        long drawTime = drawEnd - drawStart;

        if (keyHandler.checkDrawTime) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 36));
            g2.drawString("Draw Time: " + drawTime + " ms", 10, 565);
        }
    }

    public void playMusic(int index) {
        music.setFile(index);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void soundEffect(int index) {
        soundEffect.setFile(index);
        soundEffect.play();
    }
}
