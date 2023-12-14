package net.mceteams.functions;

import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class logsRegister {
    private static String getCurrentDate() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(now);
    }

    private static String getCurrentTime() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        return sdf.format(now);
    }

    public static void createLog(Player p) {
        File folder = new File("plugins/mce-smp/logs/" + p.getUniqueId());
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                String logFileName = getCurrentDate() + ".log";
                File file = new File(folder, logFileName);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void addLogEntry(Player p, String logEntry) {
        File folder = new File("plugins/mce-smp/logs/" + p.getUniqueId());
        String logFileName = getCurrentDate() + ".log";
        File logFile = new File(folder, logFileName);

        try (FileWriter writer = new FileWriter(logFile, true)) {
            String timestamp = getCurrentTime();
            String entry = "[" + timestamp + "]: " + logEntry + System.lineSeparator();
            writer.write(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
