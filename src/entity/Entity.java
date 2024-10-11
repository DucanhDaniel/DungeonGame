package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// Store variables that will be use in player, monster and  NPC classes
public class Entity {
    public BufferedImage image;
    public BufferedImage[] imageArray;
    public String name;
    public boolean collision = false;

    public int worldX, worldY;
    public int speed;

    public int numAnimationFrames;
    public int actionLockCounter;
    public BufferedImage[] up, down, left, right, idle_up, idle_left, idle_right, idle_down;
    public String direction = "down";
    
    public int solidAreaDefaultX, solidAreaDefaultY;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public boolean collisionOn = false;
    public String[] dialogues = new String[20];
    public GamePanel gp;

    // Character status
    public int maxLife;
    public int currentLife;


    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    UtilityTool utilityTool = new UtilityTool();
    public BufferedImage getObjectImage(String imageFileName, int width, int height) {
        BufferedImage result = null;
        try {
            result = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                    "object/" + imageFileName + ".png")));
            result = utilityTool.scaleImage(result, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void speak(){}

    public BufferedImage setUp(String imagePath, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imagePath + ".png")));
            scaledImage = utilityTool.scaleImage(scaledImage, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    public void initializeAnimation() {
        up = new BufferedImage[numAnimationFrames];
        down = new BufferedImage[numAnimationFrames];
        left = new BufferedImage[numAnimationFrames];
        right = new BufferedImage[numAnimationFrames];
        idle_down = new BufferedImage[numAnimationFrames];
        idle_up = new BufferedImage[numAnimationFrames];
        idle_left = new BufferedImage[numAnimationFrames];
        idle_right = new BufferedImage[numAnimationFrames];
    }


    int cnt = 0;
    // Total number of frames to change entity image
    int numFramesToReverse = 5;
    boolean isIdle = true;
    public BufferedImage getImage(String direction) {

        BufferedImage[] image = switch (direction) {
            case "up" -> up;
            case "down" -> down;
            case "left" -> left;
            case null, default -> right;
        };

        if (isIdle) {
            switch (direction) {
                case "up" -> image = idle_up;
                case "down" -> {
                    image = idle_down;
                }
                case "left" -> image = idle_left;
                case null, default -> image = idle_right;
            }
        }
        cnt++;
        for (int i = 0; i < numAnimationFrames - 1; i++) {
            if (cnt <= numFramesToReverse*(i + 1)) return image[i];
        }
        if (cnt > numFramesToReverse*numAnimationFrames)
            cnt = 0;
        return image[numAnimationFrames - 1];
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Check if npc is inside the screen to decide drawing or not
        if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize
                && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize
                && worldY > gp.player.worldY - gp.player.screenY - gp.tileSize
                && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
            BufferedImage image = switch (direction) {
                case "up" -> getImage("up");
                case "down" -> getImage("down");
                case "left" -> getImage("left");
                case "right" -> getImage("right");
                default -> null;
            };
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            // Draw solid area for debugging purposes
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

    public void setAction() {

    }
    public void update() {
        setAction();
        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkPlayer(this);
        int objectIndex = gp.collisionChecker.checkObject(this, true);
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

    }
}
