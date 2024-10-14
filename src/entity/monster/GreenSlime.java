package entity.monster;

import entity.Entity;
import main.GamePanel;

public class GreenSlime extends Entity {

    public GreenSlime(GamePanel gp) {
        super(gp);

        type = 2;

        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        currentLife = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        numAnimationFrames = 2;
        numFramesToReverse = 30;
        initializeAnimation();

        getGreenSlimeImage();
    }


    public void getGreenSlimeImage() {
        for (int i = 1; i <= numAnimationFrames; i++) {
            up[i - 1] = setUp("monster/greenslime_down_" + i, gp.tileSize, gp.tileSize);
            down[i - 1] = setUp("monster/greenslime_down_" + i, gp.tileSize, gp.tileSize);
            left[i - 1] = setUp("monster/greenslime_down_" + i, gp.tileSize, gp.tileSize);
            right[i - 1] = setUp("monster/greenslime_down_" + i, gp.tileSize, gp.tileSize);
        }
        idle_down = down;
        idle_up = down;
        idle_left = down;
        idle_right = down;
    }


}
