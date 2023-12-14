package net.mceteams.functions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class sanctionsManagerFunction {

    public static void kick(Player p, String reason, String WhoBanned) {
        p.kickPlayer("§c§lMCE §8§l»§r§7 Vous avez été §4§lExclus§r\n\n§cRaison §8§l»§r§7 " + reason + "\n§cPar §8§l»§r§7 " + WhoBanned);
    }

    public static void ban(Player p, String reason, String WhoBanned) {
        if (dataManagementFunction.searchData("/bans/", "status", p.getUniqueId().toString()) == null) {
            dataManagementFunction.saveData(p.getUniqueId().toString(),"/bans/","status", "true");
            dataManagementFunction.addData(p.getUniqueId().toString(),"/bans/","reason", reason);
            dataManagementFunction.addData(p.getUniqueId().toString(),"/bans/", "Administrator", WhoBanned);
            p.kickPlayer("§7§l---------------------------------- §4l'SMP §7---------- §6Punition §7----------------------------------§r\n§4§lVous avez été BANNI de ce serveur !§r\n\n§7Raison : §e§l" + reason + "§r\n§7Temps restant : §4§l Bannissement PERMANENT.§r\n\n§9Pour §b§lrevenir sur la désision finale de ce bannissement§r§9 car vous ne la trouvez pas valable !§r\n§9Merci de passer sur §6§ltickets.mceteams.net/lsmp/appeals.§r\n\n§7-----------------------------------------------------------------------------------------------------------§r");
            Bukkit.broadcastMessage("§6§l" + p.getName() + "§r a été §4§lBANNI§r\n§7Temps : §4§lBannissement PERMANENT§r.\n§7Raison : §6§l" + reason + "§r.\n\n§7Pour éviter de ne pas prendre une sanction similaire. Merci de prendre compte du règlement.");
        }
    }

    public static void tempban(Player p, String reason, String WhoBanned, String duration) {
        if (dataManagementFunction.searchData("/tempbans/", "status", p.getUniqueId().toString()) == null) {
            dataManagementFunction.saveData(p.getUniqueId().toString(),"/tempbans/","status", "true");
            dataManagementFunction.addData(p.getUniqueId().toString(),"/tempbans/","reason", reason);
            dataManagementFunction.addData(p.getUniqueId().toString(),"/tempbans/", "Administrator", WhoBanned);

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endDateTime = calculateEndDateTime(now, duration);
            String formattedEndDateTime = endDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            dataManagementFunction.addData(p.getUniqueId().toString(),"/tempbans/", "duration", formattedEndDateTime);

            p.kickPlayer("§c§lMCE §8§l»§r§7 Vous avez été §4§lBANNI TEMPORAIREMENT§r\n\n§cRaison §8§l»§r§7 " + reason + "\n§cDurée §8§l»§r§7 " + duration + "\n§cFin §8§l»§r§7 " + formattedEndDateTime + "\n§cPar §8§l»§r§7 " + WhoBanned);
        }
    }

    private static LocalDateTime calculateEndDateTime(LocalDateTime startDateTime, String duration) {
        char unit = duration.charAt(duration.length() - 1);
        int amount = Integer.parseInt(duration.substring(0, duration.length() - 1));

        return switch (unit) {
            case 's' -> startDateTime.plusSeconds(amount);
            case 'm' -> startDateTime.plusMinutes(amount);
            case 'h' -> startDateTime.plusHours(amount);
            case 'd' -> startDateTime.plusDays(amount);
            default ->
                // Invalid duration format, handle accordingly
                    throw new IllegalArgumentException("Invalid duration format: " + duration);
        };
    }
}
