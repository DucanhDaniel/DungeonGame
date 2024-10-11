package obj;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class Mana extends Entity {
    GamePanel gp;
    public Mana(GamePanel gp) {
        super(gp);

        name = "Heart";

        imageArray = new BufferedImage[4];
        imageArray[0] = getObjectImage("manacrystal_blank", gp.tileSize, gp.tileSize);
        imageArray[1] = getObjectImage("manacrystal_full", gp.tileSize, gp.tileSize);

        imageArray[2] = getObjectImage("mana_bar", 46 * 3, 7 * 3);
        imageArray[3] = getObjectImage("mana_bar_decoration", 64 * 3, 17 * 3);
        idle_down = new BufferedImage[1];
        idle_down[0] = image;
        numAnimationFrames = 1;
    }
}
