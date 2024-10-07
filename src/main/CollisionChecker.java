package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.tileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileManager.tileNum[entityTopRow][entityRightCol];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision)
                    entity.collisionOn = true;
                else entity.collisionOn = false;
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.tileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileManager.tileNum[entityBottomRow][entityRightCol];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision)
                    entity.collisionOn = true;
                else entity.collisionOn = false;
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.tileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileManager.tileNum[entityBottomRow][entityLeftCol];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision)
                    entity.collisionOn = true;
                else entity.collisionOn = false;
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.tileNum[entityTopRow][entityRightCol];
                tileNum2 = gp.tileManager.tileNum[entityBottomRow][entityRightCol];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision)
                    entity.collisionOn = true;
                else entity.collisionOn = false;
                break;
        }
    }
    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for (int i = 0; i < gp.superObject.length; i++) {
            if (gp.superObject[i] != null) {

                // Get entity solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // Get the object solid area position
                gp.superObject[i].solidArea.x = gp.superObject[i].solidArea.x + gp.superObject[i].worldX;
                gp.superObject[i].solidArea.y = gp.superObject[i].solidArea.y + gp.superObject[i].worldY;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.superObject[i].solidArea)) {
                            if (gp.superObject[i].collision)
                                entity.collisionOn = true;
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.superObject[i].solidArea)){
                            if (gp.superObject[i].collision)
                                entity.collisionOn = true;
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.superObject[i].solidArea)){
                            if (gp.superObject[i].collision)
                                entity.collisionOn = true;
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.superObject[i].solidArea)){
                            if (gp.superObject[i].collision)
                                entity.collisionOn = true;
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }

                gp.superObject[i].solidArea.x = gp.superObject[i].defaultSolidAreaX;
                gp.superObject[i].solidArea.y = gp.superObject[i].defaultSolidAreaY;
            }
            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
        }

        return index;
    }

    // NPC and monster collision
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {

                // Get entity solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the target solid area position
                target[i].solidArea.x = target[i].solidArea.x + target[i].worldX;
                target[i].solidArea.y = target[i].solidArea.y + target[i].worldY;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }

                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
        }

        return index;
    }

    public void checkPlayer(Entity entity) {
        // Get entity solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Get the target solid area position
        gp.player.solidArea.x = gp.player.solidArea.x + gp.player.worldX;
        gp.player.solidArea.y = gp.player.solidArea.y + gp.player.worldY;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)){
                    entity.collisionOn = true;
                }
                break;
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
    }
}
