package main;

import entity.Entity;
import obj.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    public boolean messageOn = false;
    public String message = "";
    public String currentDialogue = "";
    public int dialogueIndex = 0;
    public int commandNumber = 0;

    BufferedImage heart_full, heart_half, heart_blank, health_bar, health_bar_decoration;
    BufferedImage mana_bar, mana_bar_decoration;

    BufferedImage statusPanel;
    BufferedImage manaBar;
    BufferedImage healthBar;
    BufferedImage armorBar;

    StatusPanel sp;
    Entity heart, mana;

    // smaller state of title
    public int titleScreenState = 0;
    Font maruMonica, purisaBold;
    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            assert is != null;
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            assert is != null;
            purisaBold = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        // Create hub object
        heart = new Heart(gp);
        heart_full = heart.imageArray[0];
        heart_half = heart.imageArray[1];
        heart_blank = heart.imageArray[2];
        health_bar = heart.imageArray[3];
        health_bar_decoration = heart.imageArray[4];

        mana = new Mana(gp);
        mana_bar = mana.imageArray[2];
        mana_bar_decoration = mana.imageArray[3];

        sp = new StatusPanel(gp);
        statusPanel = sp.image;
        healthBar = sp.imageArray[0];
        armorBar = sp.imageArray[1];
        manaBar = sp.imageArray[2];

    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        g2.setColor(Color.WHITE);

        // Title screen
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        else if (gp.gameState == gp.playState) {
            // Do play state
            drawPlayerStatus();
        }
        else if (gp.gameState == gp.pauseState) {
            drawPlayerStatus();
            drawPauseScreen();
        }
        
        // Dialog state
        else if (gp.gameState == gp.dialogueState) {
            drawPlayerStatus();
            drawDialogueScreen();
        }
    }

    public void drawPlayerStatus() {
        int x = gp.tileSize/4;
        int y = gp.tileSize/4;

        g2.drawImage(statusPanel, x, y, null);

        // Scale healthBar, armorBar, manaBar to precisely match the current state
        int width = (int)(sp.barWidth * (1.0 * gp.player.currentLife / gp.player.maxLife));
        if (width > 0) healthBar = sp.getObjectImage("health_bar_new", width, sp.barHeight);
        else healthBar = null;

        width = (int)(sp.barWidth * (1.0 * gp.player.currentArmor / gp.player.maxArmor));
        if (width > 0) armorBar = sp.getObjectImage("armor_bar_new", width, sp.barHeight);
        else armorBar = null;

        width = (int)(sp.barWidth * (1.0 * gp.player.currentMana / gp.player.maxMana));
        if (width > 0) manaBar = sp.getObjectImage("mana_bar_new", width, sp.barHeight);
        else manaBar = null;

        // Draw health bar, armor bar, mana bar
        if (healthBar != null) g2.drawImage(healthBar, x + 65 / 3 * 2 + 1, y + 14 / 3 * 2 + 1, null);
        if (armorBar != null) g2.drawImage(armorBar, x + 65 / 3 * 2 + 1, y + 14 / 3 * 2 + 1 + 29, null);
        if (manaBar != null) g2.drawImage(manaBar, x + 65 / 3 * 2 + 1, y + 14 / 3 * 2 + 1 + 56, null);

        // Draw text to show number
        String text = gp.player.currentLife + "/" + gp.player.maxLife;
        g2.setFont(purisaBold);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
        g2.drawString(text, x + 65 / 3 * 2 + 15, y + 25);

        text = gp.player.currentArmor + "/" + gp.player.maxArmor;
        g2.drawString(text, x + 65 / 3 * 2 + 15, y + 25 + 28);

        text = gp.player.currentMana + "/" + gp.player.maxMana;
        g2.drawString(text, x + 65 / 3 * 2 + 15, y + 25 + 55);
    }

    public void drawPlayerMana() {
        int x = gp.tileSize/4;
        int y = gp.tileSize/4 + gp.tileSize;
        g2.drawImage(mana_bar_decoration, x, y, null);

        int mana_bar_width = (int) (46 * 3 * ((double) gp.player.currentMana / gp.player.maxMana));
        if (mana_bar_width > 0) {
            mana_bar = mana.getObjectImage("mana_bar", mana_bar_width, 7 * 3);
            g2.drawImage(mana_bar, x + 17 * 3, y + 5 * 3, null);
        }

        // Draw text to show player health as number
        String text = gp.player.currentMana + "/" + gp.player.maxMana;
        g2.setFont(purisaBold);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
        g2.drawString(text, x + 23 * 3, y + 32);
    }

    public void drawPlayerLife() {
        int x = gp.tileSize/4;
        int y = gp.tileSize/4;
        g2.drawImage(health_bar_decoration, x, y, null);

        int health_bar_width = (int) (46 * 3 * ((double) gp.player.currentLife / gp.player.maxLife));
        if (health_bar_width > 0) {
            health_bar = heart.getObjectImage("health_bar", health_bar_width, 7 * 3);
            g2.drawImage(health_bar, x + 17 * 3, y + 5 * 3, null);
        }

        // Draw text to show player health as number
        String text = gp.player.currentLife + "/" + gp.player.maxLife;
        g2.setFont(purisaBold);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
        g2.drawString(text, x + 23 * 3, y + 32);
    }



    String[] directionArray = {"down", "down_left", "left", "up_left", "up", "up_right", "right", "down_right"};
    int frameCounter = 0;
    int directionIndex = 0;
    private void drawTitleScreen() {
        if (titleScreenState == 0) {

            // Configure background color
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // Create shadow for
            g2.setColor(Color.GRAY);
            String gameTitle = "Dungeon Game";
            int x = getXForCenterText(gameTitle);
            int y = getYForCenterText(gameTitle) - gp.tileSize * 3;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
            g2.drawString(gameTitle, x + 5, y + 3);

            // Title name
            g2.setColor(Color.WHITE);
            g2.drawString(gameTitle, x, y);

            // Display main character
            x = gp.screenWidth / 2 - gp.tileSize;
            y += gp.tileSize / 2;
            String direction;
            frameCounter++;
            if (frameCounter >= 60) {
                directionIndex = (directionIndex + 1) % 8;
                frameCounter = 0;
            }
            direction = directionArray[directionIndex];
            BufferedImage image = switch (direction) {
                case "up" -> gp.player.getImage("up");
                case "down" -> gp.player.getImage("down");
                case "left" -> gp.player.getImage("left");
                case "right" -> gp.player.getImage("right");
                case "up_left" -> gp.player.getImage("up_left");
                case "up_right" -> gp.player.getImage("up_right");
                case "down_left" -> gp.player.getImage("down_left");
                case "down_right" -> gp.player.getImage("down_right");
                default -> null;
            };
            g2.drawImage(image, x - 16 * 4, y - 16 * 4, 48 * 5, 64 * 5, null);

            // Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
            String text = "New game";
            x = getXForCenterText(text);
            y = getYForCenterText(text) + gp.tileSize * 3 / 2;
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.drawString("->", x - gp.tileSize, y);
            }

            text = "Load game";
            x = getXForCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNumber == 1) {
                g2.drawString("->", x - gp.tileSize, y);
            }

            text = "Quit";
            x = getXForCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNumber == 2) {
                g2.drawString("->", x - gp.tileSize, y);
            }
        }
        else if (titleScreenState == 1) {
            // Class selection screen
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class:";
            int x = getXForCenterText(text);
            int y = getYForCenterText(text);
//            y -= gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Warrior";
            x = getXForCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.drawString("->", x - gp.tileSize, y);
            }

            text = "Fighter";
            x = getXForCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNumber == 1) {
                g2.drawString("->", x - gp.tileSize, y);
            }

            text = "Wizard";
            x = getXForCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNumber == 2) {
                g2.drawString("->", x - gp.tileSize, y);
            }

            text = "Go back";
            x = getXForCenterText(text);
            y += gp.tileSize*2;
            g2.drawString(text, x, y);
            if (commandNumber == 3) {
                g2.drawString("->", x - gp.tileSize, y);
            }

        }
    }

    private void drawDialogueScreen() {
        // Window
        int x = gp.tileSize * 2, y = gp.tileSize / 2;
        int width = gp.screenWidth - gp.tileSize*4 , height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        if (currentDialogue == null) {
            gp.ui.dialogueIndex = 0;
            gp.gameState = gp.playState;
            gp.player.doneInteractingNPC = true;
            return;
        }
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }

    }
    private void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = getXForCenterText(text);
        int y = getYForCenterText(text);

        g2.drawString(text, x, y);
    }

    public int getXForCenterText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
    public int getYForCenterText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        return gp.screenHeight/2 - length/2;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
}
