package obj;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Heart extends Entity {
    GamePanel gp;
    public Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        imageArray = new BufferedImage[5];
        imageArray[0] = getObjectImage("heart_full", gp.tileSize, gp.tileSize);
        imageArray[1] = getObjectImage("heart_half", gp.tileSize, gp.tileSize);
        imageArray[2] = getObjectImage("heart_blank", gp.tileSize, gp.tileSize);

        imageArray[3] = getObjectImage("health_bar", 46 * 3, 7 * 3);
        imageArray[4] = getObjectImage("health_bar_decoration", 64 * 3, 17 * 3);

        idle_down = new BufferedImage[1];
        idle_down[0] = image;
        numAnimationFrames = 1;
    }
}
