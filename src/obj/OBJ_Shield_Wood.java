package obj;
import entity.Entity;
import main.GamePanel;
public class OBJ_Shield_Wood extends  Entity{
    public OBJ_Shield_Wood(GamePanel gp){
        super(gp);

        name = "Wood Shield";
        down1 = setup("/object/shield_wood.png",gp.tileSize,gp.tileSize);
        defenseValue = 1;
    }

    private int setup(String string, int tileSize, int tileSize2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setup'");
    }
}

