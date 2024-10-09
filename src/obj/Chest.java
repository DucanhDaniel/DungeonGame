package obj;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Chest extends SuperObject {

    GamePanel gp;

    public Chest(GamePanel gp) {
        name = "Chest";
        this.gp = gp;
        image = getObjectImage("chest", gp.tileSize, gp.tileSize);
    }
}
