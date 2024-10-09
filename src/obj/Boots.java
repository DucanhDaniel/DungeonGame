package obj;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Boots extends SuperObject {
    GamePanel gp;
    public Boots(GamePanel gp) {
        name = "Boots";
        this.gp = gp;
        image = getObjectImage("boots", gp.tileSize, gp.tileSize);

    }
}
