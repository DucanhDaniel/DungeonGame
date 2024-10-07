package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{
    public NPC_OldMan(GamePanel gp) {
        super(gp);

        setDefaultValue();
        setDialogue();

        super.initializeAnimation();
        getOldManImage();
    }
    public void setDefaultValue() {
        numAnimationFrames = 2;
        direction = "down";
        speed = 1;
        isIdle = true;
        numFramesToReverse = 20;
    }
    public void getOldManImage() {
        for (int i = 1; i <= numAnimationFrames; i++) {
            up[i - 1] = setUp("npc/oldman_up_" + i, gp.tileSize, gp.tileSize);
            down[i - 1] = setUp("npc/oldman_down_" + i, gp.tileSize, gp.tileSize);
            left[i - 1] = setUp("npc/oldman_left_" + i, gp.tileSize, gp.tileSize);
            right[i - 1] = setUp("npc/oldman_right_" + i, gp.tileSize, gp.tileSize);
            idle_down[i - 1] = down[0];
            idle_up[i - 1] = up[0];
            idle_left[i - 1] = left[0];
            idle_right[i - 1] = right[0];
        }
    }

    @Override
    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter < 180) return;

        Random random = new Random();
        // Get a random number from 1 to 100
        int i = random.nextInt(100) + 1;

        if (i <= 20) {
            direction = "up";
            isIdle = false;
        }
        else if (i <= 40) {
            direction = "down";
            isIdle = false;
        } else if (i <= 60) {
            direction = "left";
            isIdle = false;
        }
        else if (i <= 80){
            direction = "right";
            isIdle = false;
        } else {
            isIdle = true;
        }

        actionLockCounter = 0;
    }

    public void setDialogue() {
        dialogues[0] = "Hello, adventure!";
        dialogues[1] = "I'm here to help you find your way \nthrough this mysterious forest.";
        dialogues[2] = "Don't hesitate to ask any questions!";
        dialogues[3] = "Good luck, brave adventurer!";
        dialogues[4] = "Remember to stay safe and always \nfollow the path to the right.";
        dialogues[5] = "I'm sure you'll find the treasure \nyou're looking for!";
        dialogues[6] = "I'm used to be a great wizard, but \nnow I'm too old for these thing...";
    }

    @Override
    public void speak() {
        gp.ui.currentDialogue = dialogues[gp.ui.dialogueIndex];
        gp.ui.dialogueIndex++;

    }
}
