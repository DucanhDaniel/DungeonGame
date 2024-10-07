package obj;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class Door extends SuperObject{

    GamePanel gp;
    public Door(GamePanel gp) {
        this.gp = gp;
        name = "Door";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                    "object/door.png")));
            image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
