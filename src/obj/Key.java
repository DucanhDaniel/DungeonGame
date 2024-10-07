package obj;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class Key extends SuperObject {
    GamePanel gp;
    public Key(GamePanel gp) {
        this.gp = gp;
        name = "Key";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                    "object/key.png")));
            image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
