package main;

import entity.NPC_OldMan;
import obj.Boots;
import obj.Chest;
import obj.Door;
import obj.Key;

public class AssertSetter {
    GamePanel gp;
    public AssertSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {


    }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
}

