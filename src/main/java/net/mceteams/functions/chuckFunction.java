package net.mceteams.functions;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class chuckFunction {
    public static String detect(Player player) {
        Chunk chunk = player.getLocation().getChunk();

        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        return "chunk_x" + chunkX + "_z" + chunkZ;
    }

    public static String detect(Block block) {
        Chunk chunk = block.getLocation().getChunk();

        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        return "chunk_x" + chunkX + "_z" + chunkZ;
    }

    public static String detect(Entity entity) {
        Chunk chunk = entity.getLocation().getChunk();

        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        return "chunk_x" + chunkX + "_z" + chunkZ;
    }

    public static String detect(Location location) {
        Chunk chunk = location.getChunk();

        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        return "chunk_x" + chunkX + "_z" + chunkZ;
    }

    public static String detect(Item item) {
        Chunk chunk = item.getLocation().getChunk();

        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        return "chunk_x" + chunkX + "_z" + chunkZ;
    }

    public static void prepare(Player player) {
        Chunk chunkp = player.getLocation().getChunk();

        int chunkX = chunkp.getX();
        int chunkZ = chunkp.getZ();

        String chunk = "chunk_x" + chunkX + "_z" + chunkZ;

        if(!dataManagementFunction.doesDataExist(chunk, "/chunks/","avaliab")) {
            dataManagementFunction.saveData(chunk,"/chunks/","avaliab","true");
            dataManagementFunction.addData(chunk, "/chunks/","townowner", "");
            dataManagementFunction.addData(chunk, "/chunks/","claimed", "false");
        }
    }

    public static void prepare(Location location) {
        Chunk chunkp = location.getChunk();

        int chunkX = chunkp.getX();
        int chunkZ = chunkp.getZ();

        String chunk = "chunk_x" + chunkX + "_z" + chunkZ;

        if(!dataManagementFunction.doesDataExist(chunk, "/chunks/","avaliab")) {
            dataManagementFunction.saveData(chunk,"/chunks/","avaliab","true");
            dataManagementFunction.addData(chunk, "/chunks/","townowner", "");
            dataManagementFunction.addData(chunk, "/chunks/","claimed", "false");
        }
    }

    public static void prepare(Entity entity) {
        Chunk chunkp = entity.getLocation().getChunk();

        int chunkX = chunkp.getX();
        int chunkZ = chunkp.getZ();

        String chunk = "chunk_x" + chunkX + "_z" + chunkZ;

        if(!dataManagementFunction.doesDataExist(chunk, "/chunks/","avaliab")) {
            dataManagementFunction.saveData(chunk,"/chunks/","avaliab","true");
            dataManagementFunction.addData(chunk, "/chunks/","townowner", "");
            dataManagementFunction.addData(chunk, "/chunks/","claimed", "false");
        }
    }

    public static void prepare(Block block) {
        Chunk chunkp = block.getLocation().getChunk();

        int chunkX = chunkp.getX();
        int chunkZ = chunkp.getZ();

        String chunk = "chunk_x" + chunkX + "_z" + chunkZ;

        if(!dataManagementFunction.doesDataExist(chunk, "/chunks/","avaliab")) {
            dataManagementFunction.saveData(chunk,"/chunks/","avaliab","true");
            dataManagementFunction.addData(chunk, "/chunks/","townowner", "");
            dataManagementFunction.addData(chunk, "/chunks/","claimed", "false");
        }
    }

    public static void prepare(Item item) {
        Chunk chunkp = item.getLocation().getChunk();

        int chunkX = chunkp.getX();
        int chunkZ = chunkp.getZ();

        String chunk = "chunk_x" + chunkX + "_z" + chunkZ;

        if(!dataManagementFunction.doesDataExist(chunk, "/chunks/","avaliab")) {
            dataManagementFunction.saveData(chunk,"/chunks/","avaliab","true");
            dataManagementFunction.addData(chunk, "/chunks/","townowner", "");
            dataManagementFunction.addData(chunk, "/chunks/","claimed", "false");
        }
    }
}
