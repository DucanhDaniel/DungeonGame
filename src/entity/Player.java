package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    // Player's mana
    public int maxMana, currentMana;

    // Player's armor
    public int maxArmor, currentArmor;

    public boolean invincible = false;
    public boolean attackCanceled = false;
    public int invincibleCounter = 0;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        super(gp);

        this.keyHandler = keyHandler;
        setDefaultValues();

        screenX = gp.screenWidth/2 - gp.tileSize/2 - 16 * gp.scale;
        screenY = gp.screenHeight/2 - gp.tileSize/2 - 16 * gp.scale;

        // Set the solid area for collision detection
        solidArea = new Rectangle();
        solidArea.setBounds(18* gp.scale, 32* gp.scale, 13 * gp.scale, 12 * gp.scale);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        super.initializeAnimation();
        getPlayerImage();
    }
    public void setDefaultValues() {
        type = 0;

        numAnimationFrames = 8;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;

        // Player status
        maxLife = 1200;
        currentLife = 1200;
        maxMana = 200;
        currentMana = 200;
        maxArmor = 20;
        currentArmor = 20;

    }

    public void getPlayerImage() {
        int playerImageWidth = 48 * gp.scale;
        int playerImageHeight = 64 * gp.scale;
        for (int i = 1; i <= numAnimationFrames; i++) {
            up[i - 1] = setUp("player/up" + i, playerImageWidth, playerImageHeight);
            down[i - 1] = setUp("player/down" + i, playerImageWidth, playerImageHeight);
            left[i - 1] = setUp("player/left" + i, playerImageWidth, playerImageHeight);
            right[i - 1] = setUp("player/right" + i, playerImageWidth, playerImageHeight);
            up_left[i - 1] = setUp("player/up_left" + i, playerImageWidth, playerImageHeight);
            up_right[i - 1] = setUp("player/up_right" + i, playerImageWidth, playerImageHeight);
            down_left[i - 1] = setUp("player/down_left" + i, playerImageWidth, playerImageHeight);
            down_right[i - 1] = setUp("player/down_right" + i, playerImageWidth, playerImageHeight);
            
            idle_up[i - 1] = setUp("player/idle_up" + i, playerImageWidth, playerImageHeight);
            idle_down[i - 1] = setUp("player/idle_down" + i, playerImageWidth, playerImageHeight);
            idle_left[i - 1] = setUp("player/idle_left" + i, playerImageWidth, playerImageHeight);
            idle_right[i - 1] = setUp("player/idle_right" + i, playerImageWidth, playerImageHeight);
            idle_up_left[i - 1] = setUp("player/idle_up_left" + i, playerImageWidth, playerImageHeight);
            idle_up_right[i - 1] = setUp("player/idle_up_right" + i, playerImageWidth, playerImageHeight);
            idle_down_left[i - 1] = setUp("player/idle_down_left" + i, playerImageWidth, playerImageHeight);
            idle_down_right[i - 1] = setUp("player/idle_down_right" + i, playerImageWidth, playerImageHeight);
        }
    }

    public boolean doneInteractingNPC = false;
    @Override
    public void update() {
        // Update the player's direction based on the pressed keys'
        if (keyHandler.upPressed) {
            if (keyHandler.rightPressed) direction = "up_right";
            else if (keyHandler.leftPressed) direction = "up_left";
            else direction = "up";
        }
        else if (keyHandler.downPressed) {
            if (keyHandler.rightPressed) direction = "down_right";
            else if (keyHandler.leftPressed) direction = "down_left";
            else direction = "down";
        }
        else if (keyHandler.leftPressed) direction = "left";
        else if (keyHandler.rightPressed) direction = "right";

        // Check tile collision
        gp.collisionChecker.checkTile(this);

        // Check NPCs collision
        int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
        if (npcIndex == 999) doneInteractingNPC = false;
        if (!doneInteractingNPC)
            interactNPC(npcIndex);

        // Check monsters collision
        int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
        if (monsterIndex != 999) contactMonster(gp.monster[monsterIndex]);

        // Update the player's position base on position
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
                case "up_left":
                    if (keyHandler.upPressed && keyHandler.leftPressed) {
                        worldX -= speed/4 * 3;
                        worldY -= speed/4 * 3;
                    }
                    break;
                case "up_right":
                    if (keyHandler.upPressed && keyHandler.rightPressed) {
                        worldX += speed/4 * 3;
                        worldY -= speed/4 * 3;
                    }
                    break;
                case "down_left":
                    if (keyHandler.downPressed && keyHandler.leftPressed) {
                        worldX -= speed/4 * 3;
                        worldY += speed/4 * 3;
                    }
                    break;
                case "down_right":
                    if (keyHandler.downPressed && keyHandler.rightPressed) {
                        worldX += speed/4 * 3;
                        worldY += speed/4 * 3;
                    }
                    break;
            }
        }
        // Check event
        gp.eventHandler.checkEvent();

        // Check object collision
        pickUpObject(objectIndex);

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter >= 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void contactMonster(Entity monster) {
        // Implement monster's attack
        if (invincible) {
            return;
        }
        currentLife -= 100; // -= monster.damage
        invincible = true;
        System.out.println("Monster hit! Remaining life: " + currentLife);
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
            case "up_left" -> getImage("up_left");
            case "up_right" -> getImage("up_right");
            case "down_left" -> getImage("down_left");
            case "down_right" -> getImage("down_right");
            default -> null;
        };
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, screenX, screenY, null);
        g2.drawRect(screenX, screenY, gp.tileSize * 3, gp.tileSize * 4);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));



        // Debug
//        g2.setFont(new Font("Arial", Font.BOLD, 26));
//        g2.setColor(Color.WHITE);
//        g2.drawString("Invincible time left: " + (60 - invincibleCounter), 10, 500);
    }


}
