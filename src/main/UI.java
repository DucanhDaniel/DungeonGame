package main;

import obj.Key;

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

        }
        else if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        
        // Dialog state
        else if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
    }


    String[] directionArray = {"down", "left", "right"};
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
                directionIndex = (directionIndex + 1) % 3;
                frameCounter = 0;
            }
            direction = directionArray[directionIndex];
            BufferedImage image = switch (direction) {
                case "up" -> gp.player.getImage("up");
                case "down" -> gp.player.getImage("down");
                case "left" -> gp.player.getImage("left");
                case "right" -> gp.player.getImage("right");
                default -> null;
            };
            g2.drawImage(image, x, y, gp.tileSize * 2, gp.tileSize * 4, null);

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
