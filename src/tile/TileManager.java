package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int tileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50];
        tileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
        getTileImage();
        loadMap("res/maps/world01.txt");
    }
    public void getTileImage() {
        // PLACEHOLDER
        int indexes[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for(int i = 0; i < indexes.length; i++){
            setUp(indexes[i], "grass00", false);
        }

        // TILES
        setUp(11, "grass01", false);

        // WATER loop
        indexes = new int[]{12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
        for(int i = 0; i < indexes.length; i++){
            String waterIndex = "water" + String.format("%02d", i);
            setUp(indexes[i], waterIndex, true);
        }

        // ROAD loop
        indexes = new int[]{26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38};
        for(int i = 0; i < indexes.length; i++){
            String roadIndex = "road" + String.format("%02d", i);
            setUp(indexes[i], roadIndex, false);
        }

        setUp(39, "earth", false);
        setUp(40, "wall", true);
        setUp(41,"tree", true);

    }

    public void setUp(int index, String imagePath, boolean collision) {
        UtilityTool utilityTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                    "tiles/" + imagePath + ".png")));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String path) {
        try {
            File file = new File(path);
            FileInputStream ft = new FileInputStream(file);

            DataInputStream in = new DataInputStream(ft);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strline;

            int row = 0;
            while ((strline = br.readLine()) != null){
                String[] numbers = strline.split(" ");
                for (int i = 0; i < gp.maxWorldCol; i++) {
                    tileNum[row][i] = Integer.parseInt(numbers[i]);
                }
                row++;
            }
            for (int i = 0; i < gp.maxWorldRow; i++) {
                for (int j = 0; j < gp.maxWorldCol; j++) {
                    System.out.print(tileNum[i][j] + " ");
                }
                System.out.println();
            }

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileId = tileNum[worldRow][worldCol];
            if (tileId >= 0) {
                int worldX = worldCol*gp.tileSize;
                int worldY = worldRow*gp.tileSize;

                int screenX = worldX - (gp.player.worldX + gp.tileSize) + (gp.player.screenX + gp.tileSize);
                int screenY = worldY - (gp.player.worldY + gp.tileSize) + (gp.player.screenY + gp.tileSize);

                if (worldX > (gp.player.worldX + gp.tileSize) - (gp.player.screenX + gp.tileSize) - gp.tileSize
                        && worldX < (gp.player.worldX + gp.tileSize) + (gp.player.screenX + gp.tileSize) + gp.tileSize
                        && worldY > (gp.player.worldY + gp.tileSize) - (gp.player.screenY + gp.tileSize) - gp.tileSize
                        && worldY < (gp.player.worldY + gp.tileSize) + (gp.player.screenY + gp.tileSize) + gp.tileSize)
                    g2.drawImage(tile[tileId].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
