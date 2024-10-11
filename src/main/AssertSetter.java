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
//        gp.superObject[0] = new Boots(gp);
//        gp.superObject[0].worldX = gp.tileSize*20;
//        gp.superObject[0].worldY = gp.tileSize*20;
//
//        gp.superObject[1] = new Chest(gp);
//        gp.superObject[1].worldX = gp.tileSize*22;
//        gp.superObject[1].worldY = gp.tileSize*22;
//
//        gp.superObject[2] = new Door(gp);
//        gp.superObject[2].worldX = gp.tileSize*24;
//        gp.superObject[2].worldY = gp.tileSize*24;
//
//        gp.superObject[3] = new Key(gp);
//        gp.superObject[3].worldX = gp.tileSize*26;
//        gp.superObject[3].worldY = gp.tileSize*26;

    }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
}

