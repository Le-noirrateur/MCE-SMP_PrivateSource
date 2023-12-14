package net.mceteams;

/* net.mceteams */

import net.mceteams.commands.*;
import net.mceteams.event.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;



public class smpplugin extends JavaPlugin {

    private static smpplugin plugin;

    @Override
    public void onDisable() {
        super.onDisable();
        getServer().getConsoleSender().sendMessage(ChatColor.COLOR_CHAR +
                """
                                                
                        §4____________________________________________________________________________________________________________§7
                         §4___§f/\\\\\\\\§4____________§f/\\\\\\\\§4_____§f/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\§4___§f/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\§4_______§f/\\\\\\\\\\\\\\\\\\\\\\§4__§f/\\\\\\§4__________§f/\\\\\\§4__§7
                          §4__§f\\/\\\\\\\\\\\\§4________§f/\\\\\\\\\\\\§4____§f\\/\\\\\\////////////§4___§f\\/\\\\\\////////////§4_______§f\\/////\\\\\\///§4__§f\\/\\\\\\\\\\§4______§f/\\\\\\\\\\§4__§7
                           §4__§f\\/\\\\\\//\\\\\\§4____§f/\\\\\\//\\\\\\§4____§f\\/\\\\\\§4_______________§f\\/\\\\\\§4_______________________§f\\/\\\\\\§4_____§f\\/\\\\///\\\\§4_§f/\\\\////\\\\§4__§7
                            §4__§f\\/\\\\\\\\///\\\\\\/\\\\\\/§4_§f\\/\\\\\\§4____§f\\/\\\\\\§4_______________§f\\/\\\\\\§4_______________________§f\\/\\\\\\§4_____§f\\/\\\\_§f\\//\\\\//___§f\\/\\\\§4__§7
                             §4__§f\\/\\\\\\§4__§f\\///\\\\\\/§4___§f\\/\\\\\\§4____§f\\/\\\\\\§4_______________§f\\/\\\\\\\\\\\\\\\\\\\\§4________________§f\\/\\\\\\§4_____§f\\/\\\\§4__§f\\//§4______§f\\/\\\\§4__§7
                              §4__§f\\/\\\\\\§4____§f\\///§4_____§f\\/\\\\\\§4____§f\\/\\\\\\§4_______________§f\\/\\\\\\//////§4_________________§f\\///§4______§f\\//§4____________§f\\//§4___§7
                               §4__§f\\/\\\\\\§4_____________§f\\/\\\\\\§4____§f\\/\\\\\\§4_______________§f\\/\\\\\\§4______________________________________________________§7
                                §4__§f\\/\\\\\\§4_____________§f\\/\\\\\\§4____§f\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\§4___§f\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\§4__________________________________________§7
                                 §4__§f\\///§4______________§f\\///§4_____§f\\///////////////§4____§f\\///////////////§4___________________________________________§7
                                  §4____________________________________________________________________________________________________________§7

                        ----------------------------------------
                        The plugin has §4disabled successfully§r.
                        ----------------------------------------
                                                
                        """);
    }

//    private TabListManager tabListManager;

    @Override
    public void onEnable() {

        super.onEnable();
        PluginDescriptionFile pluginDescription = getDescription();
        String version = pluginDescription.getVersion();

        getServer().getConsoleSender().sendMessage("Version : §2" + version + "§r");
        getServer().getConsoleSender().sendMessage("§6Loading commands...§r");

        /* Chargement des commandes */
        Objects.requireNonNull(getCommand("delhome")).setExecutor(new delhome());
        Objects.requireNonNull(getCommand("sethome")).setExecutor(new sethome());
        Objects.requireNonNull(getCommand("vanish")).setExecutor(new vanish());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new fly());
        Objects.requireNonNull(getCommand("feed")).setExecutor(new feed());
        Objects.requireNonNull(getCommand("heal")).setExecutor(new health());
        Objects.requireNonNull(getCommand("enderChest")).setExecutor(new enderChest());
        Objects.requireNonNull(getCommand("enderchest")).setExecutor(new enderChest());
        Objects.requireNonNull(getCommand("jobs")).setExecutor(new jobs());
        Objects.requireNonNull(getCommand("changelog")).setExecutor(new changelogs());
        Objects.requireNonNull(getCommand("tempban")).setExecutor(new tempban());
        Objects.requireNonNull(getCommand("money")).setExecutor(new money());
        Objects.requireNonNull(getCommand("town")).setExecutor(new town());
        Objects.requireNonNull(getCommand("war")).setExecutor(new war());
        Objects.requireNonNull(getCommand("claim")).setExecutor(new claim());
        Objects.requireNonNull(getCommand("admin")).setExecutor(new admin());
        Objects.requireNonNull(getCommand("getversion")).setExecutor(new getversion());
        Objects.requireNonNull(getCommand("login")).setExecutor(new login());
        Objects.requireNonNull(getCommand("register")).setExecutor(new register());
        Objects.requireNonNull(getCommand("claim")).setExecutor(new claim());
        Objects.requireNonNull(getCommand("unclaim")).setExecutor(new unclaim());

        home home = new home(this);
        Objects.requireNonNull(getCommand("home")).setExecutor(home);

        tpa tpa = new tpa(this); // "this" représente l'instance de votre plugin
        Objects.requireNonNull(getCommand("tpa")).setExecutor(tpa);

        TpAcceptCommand tpAcceptCommand = new TpAcceptCommand(this, tpa.getTpaRequests()); // Créez une instance de TpAcceptCommand avec la même map tpaRequests
        Objects.requireNonNull(getCommand("tpaccept")).setExecutor(tpAcceptCommand);

        TpDenyCommand tpDenyCommand = new TpDenyCommand(this, tpa.getTpaRequests()); // Créez une instance de TpDenyCommand avec la même map tpaRequests
        Objects.requireNonNull(getCommand("tpdeny")).setExecutor(tpDenyCommand);

        getServer().getConsoleSender().sendMessage("§2Loaded commands successfully§r!");

        plugin = this;

//        tabListManager = new TabListManager("YourServer", "Subtitle");

        // Enregistrez l'écouteur d'événements
//        getServer().getPluginManager().registerEvents(TabListManager, this);

        /* Chargement des events */

        getServer().getConsoleSender().sendMessage( "§6Loading events listeners...§r");
        getServer().getPluginManager().registerEvents(new townChunkProtection(), this);
        getServer().getPluginManager().registerEvents(new chatScannerProtector(), this);
        getServer().getPluginManager().registerEvents(new logEveryImportantActions(), this);
        getServer().getPluginManager().registerEvents(new banevent(), this);
        getServer().getPluginManager().registerEvents(new defaultMinecraftCommandsEdit(), this);
        getServer().getPluginManager().registerEvents(new onJoin(), this);
        getServer().getPluginManager().registerEvents(new onLeave(), this);
        getServer().getPluginManager().registerEvents(new deathEvent(), this);
        getServer().getPluginManager().registerEvents(new playerJoinOrDeathSecure(), this);
        getServer().getConsoleSender().sendMessage("""
                        §2Loaded events listeners  successfully§r!
                        
                        §a____________________________________________________________________________________________________________§7
                         §a___§f/\\\\\\\\§a____________§f/\\\\\\\\§a_____§f/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\§a___§f/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\§a_______§f/\\\\\\\\\\\\\\\\\\\\\\§a__§f/\\\\\\§a__________§f/\\\\\\§a__§7
                          §a__§f\\/\\\\\\\\\\\\§a________§f/\\\\\\\\\\\\§a____§f\\/\\\\\\////////////§a___§f\\/\\\\\\////////////§a_______§f\\/////\\\\\\///§a__§f\\/\\\\\\\\\\§a______§f/\\\\\\\\\\§a__§7
                           §a__§f\\/\\\\\\//\\\\\\§a____§f/\\\\\\//\\\\\\§a____§f\\/\\\\\\§a_______________§f\\/\\\\\\§a_______________________§f\\/\\\\\\§a_____§f\\/\\\\///\\\\§a_§f/\\\\////\\\\§a__§7
                            §a__§f\\/\\\\\\\\///\\\\\\/\\\\\\/§a_§f\\/\\\\\\§a____§f\\/\\\\\\§a_______________§f\\/\\\\\\§a_______________________§f\\/\\\\\\§a_____§f\\/\\\\_§f\\//\\\\//___§f\\/\\\\§a__§7
                             §a__§f\\/\\\\\\§a__§f\\///\\\\\\/§a___§f\\/\\\\\\§a____§f\\/\\\\\\§a_______________§f\\/\\\\\\\\\\\\\\\\\\\\§a________________§f\\/\\\\\\§a_____§f\\/\\\\§a__§f\\//§a______§f\\/\\\\§a__§7
                              §a__§f\\/\\\\\\§a____§f\\///§a_____§f\\/\\\\\\§a____§f\\/\\\\\\§a_______________§f\\/\\\\\\//////§a_________________§f\\///§a______§f\\//§a____________§f\\//§a___§7
                               §a__§f\\/\\\\\\§a_____________§f\\/\\\\\\§a____§f\\/\\\\\\§a_______________§f\\/\\\\\\§a______________________________________________________§7
                                §a__§f\\/\\\\\\§a_____________§f\\/\\\\\\§a____§f\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\§a___§f\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\§a__________________________________________§7
                                 §a__§f\\///§a______________§f\\///§a_____§f\\///////////////§a____§f\\///////////////§a___________________________________________§7
                                  §a____________________________________________________________________________________________________________§7

                        ----------------------------------------
                        The plugin has §2enabled successfully§r.
                        ----------------------------------------
                        
                        """);
    }

    public static smpplugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(smpplugin plugin) {
        smpplugin.plugin = plugin;
    }
}