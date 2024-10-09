package obj;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class SuperObject {

    public BufferedImage image;
    public BufferedImage[] imageArray;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int defaultSolidAreaX = 0;
    public int defaultSolidAreaY = 0;

    UtilityTool utilityTool = new UtilityTool();

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize
                && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize
                && worldY > gp.player.worldY - gp.player.screenY - gp.tileSize
                && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize)
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    public BufferedImage getObjectImage(String imageFileName, int width, int height) {
        BufferedImage result = null;
        try {
            result = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                    "object/" + imageFileName + ".png")));
            result = utilityTool.scaleImage(result, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
