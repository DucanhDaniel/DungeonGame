package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Entity {
    public BufferedImage image;
    public BufferedImage[] imageArray;
    public String name;
    public boolean collision = false;

    public boolean attacking = false;

    public int worldX, worldY;
    public int speed;

    public int numAnimationFrames;


    public int spriteNum = 1;
    public int spriteCounter = 0;
    public int actionLockCounter;
    public BufferedImage[] up, down, left, right, up_left, up_right, down_left, down_right;
    public BufferedImage[] idle_up, idle_left, idle_right, idle_down, idle_up_left, idle_up_right, idle_down_left, idle_down_right;

    public BufferedImage[] attack_up, attack_down, attack_left, attack_right, attack_up_left, attack_up_right, attack_down_left, attack_down_right;
    public String direction = "down";

    public int solidAreaDefaultX, solidAreaDefaultY;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public boolean collisionOn = false;
    public String[] dialogues = new String[20];
    public GamePanel gp;

    public int type = -1; // 0: Player, 1: NPC, 2: Monster

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

    public void speak() {
    }

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
        up_left = new BufferedImage[numAnimationFrames];
        up_right = new BufferedImage[numAnimationFrames];
        down_left = new BufferedImage[numAnimationFrames];
        down_right = new BufferedImage[numAnimationFrames];

        idle_down = new BufferedImage[numAnimationFrames];
        idle_up = new BufferedImage[numAnimationFrames];
        idle_left = new BufferedImage[numAnimationFrames];
        idle_right = new BufferedImage[numAnimationFrames];
        idle_up_left = new BufferedImage[numAnimationFrames];
        idle_up_right = new BufferedImage[numAnimationFrames];
        idle_down_left = new BufferedImage[numAnimationFrames];
        idle_down_right = new BufferedImage[numAnimationFrames];

        attack_up = new BufferedImage[numAnimationFrames];
        attack_down = new BufferedImage[numAnimationFrames];
        attack_left = new BufferedImage[numAnimationFrames];
        attack_right = new BufferedImage[numAnimationFrames];
        attack_up_left = new BufferedImage[numAnimationFrames];
        attack_up_right = new BufferedImage[numAnimationFrames];
        attack_down_left = new BufferedImage[numAnimationFrames];
        attack_down_right = new BufferedImage[numAnimationFrames];
    }


    int cnt = 0;
    // Total number of frames to change entity image
    public int numFramesToReverse = 5;
    public boolean isIdle = true;

    public BufferedImage getImage(String direction) {

        BufferedImage[] image = switch (direction) {
            case "up" -> up;
            case "down" -> down;
            case "left" -> left;
            case "right" -> right;
            case "up_left" -> up_left;
            case "up_right" -> up_right;
            case "down_left" -> down_left;
            case "down_right" -> down_right;
            default -> null;
        };

        boolean isIdle1 = isIdle;
        if (isIdle1) {
            image = switch (direction) {
                case "up" -> idle_up;
                case "down" -> idle_down;
                case "left" -> idle_left;
                case "right" -> idle_right;
                case "up_left" -> idle_up_left;
                case "up_right" -> idle_up_right;
                case "down_left" -> idle_down_left;
                case "down_right" -> idle_down_right;
                default -> null;
            };
        }

        if (attacking) {
            image = switch (direction) {
                case "up" -> attack_up;
                case "down" -> attack_down;
                case "left" -> attack_left;
                case "right" -> attack_right;
                case "up_left" -> attack_up_left;
                case "up_right" -> attack_up_right;
                case "down_left" -> attack_down_left;
                case "down_right" -> attack_down_right;
                default -> null;
            };
        }
        cnt++;
        for (int i = 0; i < numAnimationFrames - 1; i++) {
            if (cnt <= numFramesToReverse * (i + 1)) {
                assert image != null;
                return image[i];
            }
        }
        if (cnt > numFramesToReverse * numAnimationFrames) {
            cnt = 0;
            if (attacking) {
                attacking = false;
            }
        }
        assert image != null;
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
//            g2.setColor(Color.WHITE);
//            g2.setStroke(new BasicStroke(3));
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter < 120) return;

        Random random = new Random();
        // Get a random number from 1 to 100
        int i = random.nextInt(100) + 1;

        if (i <= 20) {
            direction = "up";
            isIdle = false;
        } else if (i <= 40) {
            direction = "down";
            isIdle = false;
        } else if (i <= 60) {
            direction = "left";
            isIdle = false;
        } else if (i <= 80) {
            direction = "right";
            isIdle = false;
        } else {
            isIdle = true;
        }

        actionLockCounter = 0;
    }

    public void update() {
        setAction();
        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        boolean contactPlayer = gp.collisionChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer) {
            gp.player.contactMonster(this);
        }
        int objectIndex = gp.collisionChecker.checkObject(this, true);

        gp.collisionChecker.checkEntity(this, gp.npc);
        gp.collisionChecker.checkEntity(this, gp.monster);

        if (!collisionOn) {
            if (isIdle) return;
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
                case "up_left":
                    worldX -= speed / 4 * 3;
                    worldY -= speed / 4 * 3;
                    break;
                case "up_right":
                    worldX += speed / 4 * 3;
                    worldY -= speed / 4 * 3;
                    break;
                case "down_left":
                    worldX -= speed / 4 * 3;
                    worldY += speed / 4 * 3;
                    break;
                case "down_right":
                    worldX += speed / 4 * 3;
                    worldY += speed / 4 * 3;
                    break;
            }
        }

    }
}
