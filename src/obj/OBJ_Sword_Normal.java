package obj;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity{
    public OBJ_Sword_Normal (GamePanel gp){
        super(gp);

        name = "Normal Sword";
        down1 = setup("/object/sword_normal.png",gp.tileSize,gp.tileSize);
        attackValue = 1;
    }

    private int setup(String string, int tileSize, int tileSize2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setup'");
    }
}
