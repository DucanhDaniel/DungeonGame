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

        int tileNum1 = 0, tileNum2 = 0;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.tileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileManager.tileNum[entityTopRow][entityRightCol];
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.tileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileManager.tileNum[entityBottomRow][entityRightCol];
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.tileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileManager.tileNum[entityBottomRow][entityLeftCol];
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.tileNum[entityTopRow][entityRightCol];
                tileNum2 = gp.tileManager.tileNum[entityBottomRow][entityRightCol];
                break;
            case "up_left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.tileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileManager.tileNum[entityTopRow][entityRightCol];
                break;
            case "up_right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.tileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileManager.tileNum[entityTopRow][entityRightCol];
                break;
            case "down_left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.tileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileManager.tileNum[entityBottomRow][entityRightCol];
                break;
            case "down_right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.tileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileManager.tileNum[entityBottomRow][entityRightCol];
                break;
        }
        entity.collisionOn = gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision;
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
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;

                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;

                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                    case "up_left":
                        entity.solidArea.x -= entity.speed;
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "up_right":
                        entity.solidArea.x += entity.speed;
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down_left":
                        entity.solidArea.x -= entity.speed;
                        entity.solidArea.y += entity.speed;
                        break;
                    case "down_right":
                        entity.solidArea.x += entity.speed;
                        entity.solidArea.y += entity.speed;
                        break;
                }
                if (entity.solidArea.intersects(gp.superObject[i].solidArea)){
                    if (gp.superObject[i].collision)
                        entity.collisionOn = true;
                    if (player) {
                        index = i;
                    }
                }

                gp.superObject[i].solidArea.x = gp.superObject[i].solidAreaDefaultX;
                gp.superObject[i].solidArea.y = gp.superObject[i].solidAreaDefaultY;
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
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                    case "up_left":
                        entity.solidArea.x -= entity.speed;
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "up_right":
                        entity.solidArea.x += entity.speed;
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down_left":
                        entity.solidArea.x -= entity.speed;
                        entity.solidArea.y += entity.speed;
                        break;
                    case "down_right":
                        entity.solidArea.x += entity.speed;
                        entity.solidArea.y += entity.speed;
                        break;
                }
                if (entity.solidArea.intersects(target[i].solidArea) && target[i] != entity) {
                    entity.collisionOn = true;
                    index = i;
                }

                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
        }

        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        // Get entity solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Get the target solid area position
        gp.player.solidArea.x = gp.player.solidArea.x + gp.player.worldX;
        gp.player.solidArea.y = gp.player.solidArea.y + gp.player.worldY;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
            case "up_left":
                entity.solidArea.x -= entity.speed;
                entity.solidArea.y -= entity.speed;
                break;
            case "up_right":
                entity.solidArea.x += entity.speed;
                entity.solidArea.y -= entity.speed;
                break;
            case "down_left":
                entity.solidArea.x -= entity.speed;
                entity.solidArea.y += entity.speed;
                break;
            case "down_right":
                entity.solidArea.x += entity.speed;
                entity.solidArea.y += entity.speed;
                break;
        }
        if (entity.solidArea.intersects(gp.player.solidArea)){
            entity.collisionOn = true;

            contactPlayer = true;
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;

        return contactPlayer;
    }
}
