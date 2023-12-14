package net.mceteams.functions;

import java.util.*;

public class presetTemporaryDataFunction {
    private static Set<UUID> spectatingPlayers = new HashSet<>();
    private static Set<UUID> loggedin = new HashSet<>();

    private static Map<UUID, String> playerChunkData = new HashMap<>();

    public static void onPlayerEnterChunk(UUID playerId, String chunkCoord) {
        playerChunkData.put(playerId, chunkCoord);
    }

    public static void onPlayerLeaveChunk(UUID playerId) {
        playerChunkData.remove(playerId);
    }

    public static boolean isPlayerInChunk(UUID playerId, String chunkCoord) {
        String playerChunk = playerChunkData.get(playerId);
        return playerChunk.equals(chunkCoord);
    }



    public static void addlogin(UUID playerId) {
        loggedin.add(playerId);
    }

    public static void removelogin(UUID playerId) {
        loggedin.remove(playerId);
    }

    public static boolean isloggedin(UUID playerId) {
        return loggedin.contains(playerId);
    }

    public static void addSpectatingPlayer(UUID playerId) {
        spectatingPlayers.add(playerId);
    }

    public static void removeSpectatingPlayer(UUID playerId) {
        spectatingPlayers.remove(playerId);
    }

    public static boolean isSpectatingPlayer(UUID playerId) {
        return spectatingPlayers.contains(playerId);
    }
}
