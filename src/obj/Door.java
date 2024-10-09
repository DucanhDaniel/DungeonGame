package obj;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class Door extends SuperObject{

    GamePanel gp;
    public Door(GamePanel gp) {
        this.gp = gp;
        name = "Door";
        image = getObjectImage("door", gp.tileSize, gp.tileSize);
        collision = true;
    }

}
