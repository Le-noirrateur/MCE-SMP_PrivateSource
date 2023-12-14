package net.mceteams.functions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class TabListManager {
    private final Scoreboard scoreboard;
    private final Objective objective;
    private final Map<Player, List<String>> playerInfoMap;
    private final Team column1Team;
    private final Team column2Team;
    private final Team column3Team;

    public TabListManager(String serverName, String subTitle) {
        scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        objective = scoreboard.registerNewObjective("tablist", "dummy", serverName);
        objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        playerInfoMap = new HashMap<>();

        column1Team = scoreboard.registerNewTeam("column1");
        column2Team = scoreboard.registerNewTeam("column2");
        column3Team = scoreboard.registerNewTeam("column3");
        Team column4Team = scoreboard.registerNewTeam("column4");

        column1Team.addEntry("a");
        column2Team.addEntry("b");
        column3Team.addEntry("c");
        column4Team.addEntry("d");

        objective.getScore("a").setScore(10);
        objective.getScore("b").setScore(9);
        objective.getScore("c").setScore(8);
        objective.getScore("d").setScore(7);

        Team titleTeam = scoreboard.registerNewTeam("title");
        titleTeam.addEntry("e");
        titleTeam.setPrefix(ChatColor.GOLD + serverName);
        titleTeam.setSuffix(ChatColor.YELLOW + subTitle);
        objective.getScore("e").setScore(6);
    }

    public void setPlayerInfo(Player player, List<String> playerInfo) {
        playerInfoMap.put(player, playerInfo);
        updatePlayerColumn(player, column1Team, playerInfo);
    }

    public void setConnectedPlayers(Player[] onlinePlayers) {
        updateConnectedPlayersColumn(onlinePlayers);
    }

    private void updatePlayerColumn(Player player, Team team, List<String> playerInfo) {
        int maxLines = 10;
        for (int i = 0; i < maxLines; i++) {
            if (i < playerInfo.size()) {
                team.setSuffix(playerInfo.get(i));
            } else {
                team.setSuffix(""); // Laisse la ligne vide si elle est absente
            }
        }

        scoreboard.resetScores(player.getName());
        objective.getScore(player.getName()).setScore(5);
    }

    private void updateConnectedPlayersColumn(Player[] onlinePlayers) {
        List<String> playerNames = new ArrayList<>();
        for (Player onlinePlayer : onlinePlayers) {
            playerNames.add(onlinePlayer.getName());
        }

        List<String> column2Entries = playerNames.subList(0, Math.min(10, playerNames.size()));
        List<String> column3Entries = playerNames.subList(10, Math.min(20, playerNames.size()));

        updateColumnTeam(column2Team, column2Entries);
        updateColumnTeam(column3Team, column3Entries);
    }

    private void updateColumnTeam(Team team, List<String> entries) {
        team.getEntries().forEach(team::removeEntry);
        for (String entry : entries) {
            team.addEntry(entry);
        }
    }
}
