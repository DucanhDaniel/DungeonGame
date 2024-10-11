package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    public int timeUpPressed, timeDownPressed, timeLeftPressed, timeRightPressed;

    public boolean checkDrawTime = false;

    public GamePanel gp;
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // Play state
        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
                timeUpPressed = (int) System.nanoTime();
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
                timeLeftPressed = (int) System.nanoTime();
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
                timeDownPressed = (int) System.nanoTime();
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
                timeRightPressed = (int) System.nanoTime();
            }
            if (code == KeyEvent.VK_ENTER) {
                System.out.println("Enter pressed");
                enterPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                if (gp.gameState == gp.playState) {
                    gp.gameState = gp.pauseState;
                }
            }

            if (!checkDrawTime && code == KeyEvent.VK_T) {
                checkDrawTime = true;
            } else if (checkDrawTime && code == KeyEvent.VK_T) {
                checkDrawTime = false;
            }
        }

        // Pause state
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }

        // Dialogue state
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }
        // Title state
        else if (gp.gameState == gp.titleState) {
            if (gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                    if (gp.ui.commandNumber >= 1) gp.ui.commandNumber--;
                    else gp.ui.commandNumber = 0;
                } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                    if (gp.ui.commandNumber < 2) gp.ui.commandNumber++;
                    else gp.ui.commandNumber = 2;
                } else if (code == KeyEvent.VK_ENTER) {
                    switch (gp.ui.commandNumber) {
                        case 0:
                            // Switch to the class selection
                            gp.ui.titleScreenState = 1;
                            gp.playMusic(0);
                            break;
                        case 1:
                            // Do load game

                            gp.gameState = gp.playState;
                            gp.playMusic(0);
                            break;
                        case 2:
                            System.exit(0);
                            break;
                    }
                }
            }
            else if (gp.ui.titleScreenState == 1) {
                // Class selection state
                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                    if (gp.ui.commandNumber >= 1) gp.ui.commandNumber--;
                    else gp.ui.commandNumber = 0;
                } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                    if (gp.ui.commandNumber < 3) gp.ui.commandNumber++;
                    else gp.ui.commandNumber = 3;
                } else if (code == KeyEvent.VK_ENTER) {
                    switch (gp.ui.commandNumber) {
                        case 0:
                            // Warrior class

                            gp.gameState = gp.playState;
                            break;
                        case 1:
                            // Fighter class

                            gp.gameState = gp.playState;
                            break;
                        case 2:
                            // Wizard class

                            gp.gameState = gp.playState;
                            break;
                        case 3:
                            // Switch to the title screen
                            gp.ui.titleScreenState = 0;
                            gp.gameState = gp.titleState;
                            gp.stopMusic();
                            break;
                    }

                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
            timeUpPressed = 0;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
            timeLeftPressed = 0;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
            timeDownPressed = 0;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
            timeRightPressed = 0;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
    }
}
