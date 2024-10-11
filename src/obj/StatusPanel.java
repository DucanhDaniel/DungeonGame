package obj;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class StatusPanel extends Entity {
    GamePanel gp;
    public int barWidth, barHeight;
    public StatusPanel(GamePanel gp) {
        super(gp);
        name = "ui_bar_decoration";

        barWidth = 219 / 3 * 2 - 1;
        barHeight = 28 / 3 * 2 + 1;
        imageArray = new BufferedImage[3];
        imageArray[0] = getObjectImage("health_bar_new", barWidth, barHeight);
        imageArray[2] = getObjectImage("mana_bar_new", barWidth, barHeight);
        imageArray[1] = getObjectImage("armor_bar_new", barWidth, barHeight);

        image = getObjectImage("ui_bar_decoration", 308 / 3 * 2, 155 / 3 * 2);
        down = new BufferedImage[1];
        down[0] = image;
    }
}
