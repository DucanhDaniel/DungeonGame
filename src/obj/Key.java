package obj;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Key extends Entity {
    GamePanel gp;
    public Key(GamePanel gp) {
        super(gp);
        name = "Key";
        image = getObjectImage("key", gp.tileSize, gp.tileSize);
        idle_down = new BufferedImage[1];
        idle_down[0] = image;
        numAnimationFrames = 1;
    }
}
