package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    KeyHandler keyHandler;

    public final int screenX, screenY;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        super(gp);

        this.keyHandler = keyHandler;
        setDefaultValues();

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        // Set the solid area for collision detection
        solidArea = new Rectangle();
        solidArea.setBounds(3* gp.scale, 16* gp.scale, 10 * gp.scale, 12 * gp.scale);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        super.initializeAnimation();
        getPlayerImage();
    }
    public void setDefaultValues() {
        numAnimationFrames = 8;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
    }

    public void getPlayerImage() {
        for (int i = 1; i <= numAnimationFrames; i++) {
            up[i - 1] = setUp("player/up" + i, gp.tileSize, 2*gp.tileSize);
            down[i - 1] = setUp("player/down" + i, gp.tileSize, 2*gp.tileSize);
            left[i - 1] = setUp("player/left" + i, gp.tileSize, 2*gp.tileSize);
            right[i - 1] = setUp("player/right" + i, gp.tileSize, 2*gp.tileSize);
            idle_up[i - 1] = setUp("player/idle_up" + i, gp.tileSize, 2*gp.tileSize);
            idle_down[i - 1] = setUp("player/idle_down" + i, gp.tileSize, 2*gp.tileSize);
            idle_left[i - 1] = setUp("player/idle_left" + i, gp.tileSize, 2*gp.tileSize);
            idle_right[i - 1] = setUp("player/idle_right" + i, gp.tileSize, 2*gp.tileSize);
        }
    }

    public boolean doneInteractingNPC = false;
    @Override
    public void update() {

        if (keyHandler.upPressed && keyHandler.rightPressed) {
            if (keyHandler.timeUpPressed > keyHandler.timeRightPressed) {
                direction = "up";
            }
            else direction = "right";
        }
        else if (keyHandler.upPressed && keyHandler.leftPressed) {
            if (keyHandler.timeUpPressed > keyHandler.timeLeftPressed) {
                direction = "up";
            }
            else direction = "left";
        }
        else if (keyHandler.downPressed && keyHandler.rightPressed) {
            if (keyHandler.timeDownPressed > keyHandler.timeRightPressed) {
                direction = "down";
            }
            else direction = "right";
        }
        else if (keyHandler.downPressed && keyHandler.leftPressed) {
            if (keyHandler.timeDownPressed > keyHandler.timeLeftPressed) {
                direction = "down";
            }
            else direction = "left";
        } else if (keyHandler.downPressed) direction = "down";
        else if (keyHandler.upPressed) direction = "up";
        else if (keyHandler.leftPressed) direction = "left";
        else if (keyHandler.rightPressed) direction = "right";

        // Check tile collision
        gp.collisionChecker.checkTile(this);

        // Check NPCs collision
        int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
        if (npcIndex == 999) doneInteractingNPC = false;
        if (!doneInteractingNPC)
            interactNPC(npcIndex);

        int objectIndex = gp.collisionChecker.checkObject(this, true);
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    if (keyHandler.upPressed) worldY -= speed;
                    break;
                case "down":
                    if (keyHandler.downPressed) worldY += speed;
                    break;
                case "left":
                    if (keyHandler.leftPressed) worldX -= speed;
                    break;
                case "right":
                    if (keyHandler.rightPressed) worldX += speed;
                    break;
            }
        }

        // Check object collision
        pickUpObject(objectIndex);
    }

    public BufferedImage getImage(String direction) {
        isIdle = !keyHandler.leftPressed && !keyHandler.rightPressed && !keyHandler.downPressed && !keyHandler.upPressed;

        // Make main character in titleState moving
        if (gp.gameState == gp.titleState)
            isIdle = false;
        return super.getImage(direction);
    }

    public void pickUpObject(int index) {
        if (index == 999) return;

    }

    public void interactNPC(int index) {
        if (index == 999) {
            return;
        }
        gp.gameState = gp.dialogueState;
        gp.npc[index].speak();
        System.out.println("Speaking");
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" -> getImage("up");
            case "down" -> getImage("down");
            case "left" -> getImage("left");
            case "right" -> getImage("right");
            default -> null;
        };
        g2.drawImage(image, screenX, screenY, null);
    }


}
