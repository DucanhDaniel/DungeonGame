package obj;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class Key extends SuperObject {
    GamePanel gp;
    public Key(GamePanel gp) {
        this.gp = gp;
        name = "Key";
        image = getObjectImage("key", gp.tileSize, gp.tileSize);
    }
}
