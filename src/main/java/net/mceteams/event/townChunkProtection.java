package net.mceteams.event;

import net.mceteams.functions.administrationSystemFunction;
import net.mceteams.functions.chuckFunction;
import net.mceteams.functions.dataManagementFunction;
import net.mceteams.functions.townManagementFunction;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static net.mceteams.functions.actionBarFunction.sendActionBarMessage;

public class townChunkProtection implements Listener {
    private final Map<UUID, String> playerLastChunk = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) throws FileNotFoundException {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        chuckFunction.prepare(player);
        String currentChunk = chuckFunction.detect(player);

        // Vérifier si le joueur a changé de chunk depuis la dernière fois qu'on a vérifié
        if (!isSameChunk(playerId, currentChunk)) {
            // Le joueur a changé de chunk
            String lastChunk = getLastChunk(playerId);
            sendEnterOrLeaveMessage(player, currentChunk, lastChunk);
            setLastChunk(playerId, currentChunk);
        }
    }

    private boolean isSameChunk(UUID playerId, String chunk) {
        return playerLastChunk.containsKey(playerId) && playerLastChunk.get(playerId).equals(chunk);
    }

    private void setLastChunk(UUID playerId, String chunk) {
        playerLastChunk.put(playerId, chunk);
    }

    private String getLastChunk(UUID playerId) {
        return playerLastChunk.getOrDefault(playerId, null);
    }

    private void sendEnterOrLeaveMessage(Player player, String chunk, String lastChunk) throws FileNotFoundException {
        String chunkowner = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
        String lastChunkOwner = dataManagementFunction.loadData(lastChunk, "/chunks/", "townowner");

        if (!chunkowner.isEmpty()) {
            if (!lastChunkOwner.equals(chunkowner)) {

                if(!dataManagementFunction.loadData(lastChunkOwner, "/towns/", "leavemsg").equals("false")) {
                    sendActionBarMessage(player,dataManagementFunction.loadData(lastChunkOwner, "/towns/", "leavemsg"));
                }

                if (!dataManagementFunction.loadData(chunkowner, "/towns/", "entermsg").equals("false")) {
                    sendActionBarMessage(player, dataManagementFunction.loadData(chunkowner, "/towns/", "entermsg"));
                }
            }
        } else {
            if(!dataManagementFunction.loadData(lastChunkOwner, "/towns/", "leavemsg").equals("false")) {
                sendActionBarMessage(player, dataManagementFunction.loadData(lastChunkOwner, "/towns/", "leavemsg"));
            }
        }

    }

    @EventHandler
    public void chunkblockplace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        chuckFunction.prepare(p);
        String chunk = chuckFunction.detect(e.getBlock());

        try {
            if (!administrationSystemFunction.bypassclaim(p)) {
                if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                    if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                        String town = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
                        if (!townManagementFunction.doesOwner(town, p)) {
                            if (!townManagementFunction.doesMember(town, p)) {
                                if(!townManagementFunction.doesPerm(town,p,"build"))
                                e.setCancelled(true);
                                p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas placer de blocs ici.");
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) { throw new RuntimeException(ex); }
    }

    @EventHandler
    public void chunkblockbreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        chuckFunction.prepare(p);
        String chunk = chuckFunction.detect(e.getBlock());

        try {
            if (!administrationSystemFunction.bypassclaim(p)) {
                if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                    if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                        String town = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
                        if (!townManagementFunction.doesOwner(town, p)) {
                            if (!townManagementFunction.doesMember(town, p)) {
                                e.setCancelled(true);
                                p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas détruire de blocs ici.");
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) { throw new RuntimeException(ex); }

    }

    @EventHandler
    public void chunkdropitem(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        chuckFunction.prepare(p);
        String chunk = chuckFunction.detect(e.getItemDrop());

        try {
            if (!administrationSystemFunction.bypassclaim(p)) {
                if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                    if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                        String town = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
                        if (!townManagementFunction.doesOwner(town, p)) {
                            if (!townManagementFunction.doesMember(town, p)) {
                                e.setCancelled(true);
                                p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas jeter des items ici.");
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) { throw new RuntimeException(ex); }
    }

    @EventHandler
    public void chunkgetitem(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        chuckFunction.prepare(p);
        String chunk = chuckFunction.detect(e.getItem());



        try {
            if (!administrationSystemFunction.bypassclaim(p)) {
                if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                    if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                        String town = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
                        if (!townManagementFunction.doesOwner(town, p)) {
                            if (!townManagementFunction.doesMember(town, p)) {
                                e.setCancelled(true);
                                p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas récupérer des items ici.");
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {throw new RuntimeException(ex);}
    }

    @EventHandler
    public void chuckinteractawithEntity(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        chuckFunction.prepare(p);
        String chunk = chuckFunction.detect(e.getRightClicked());
        Entity entity = e.getRightClicked();

        try {
            if (!administrationSystemFunction.bypassclaim(p)) {
                if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                    if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                        String town = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
                        if (!townManagementFunction.doesOwner(town, p)) {
                            if (!townManagementFunction.doesMember(town, p)) {
                                if (entity.getType() == EntityType.ITEM_FRAME || entity.getType() == EntityType.GLOW_ITEM_FRAME || entity.getType() == EntityType.ARMOR_STAND || entity.getType() == EntityType.COW || entity.getType() == EntityType.SHEEP || entity.getType() == EntityType.CAMEL || entity.getType() == EntityType.HORSE || entity.getType() == EntityType.SKELETON_HORSE || entity.getType() == EntityType.ZOMBIE_HORSE || entity.getType() == EntityType.ITEM_DISPLAY || entity.getType() == EntityType.VILLAGER || entity.getType() == EntityType.SNOWMAN || entity.getType() == EntityType.PIG || entity.getType() == EntityType.SALMON || entity.getType() == EntityType.TROPICAL_FISH || entity.getType() == EntityType.PUFFERFISH || entity.getType() == EntityType.CAT || entity.getType() == EntityType.WOLF || entity.getType() == EntityType.BOAT || entity.getType() == EntityType.MINECART || entity.getType() == EntityType.MINECART_CHEST || entity.getType() == EntityType.MINECART_COMMAND || entity.getType() == EntityType.MINECART_FURNACE || entity.getType() == EntityType.MINECART_HOPPER || entity.getType() == EntityType.MINECART_MOB_SPAWNER || entity.getType() == EntityType.MINECART_TNT || entity.getType() == EntityType.AXOLOTL || entity.getType() == EntityType.CHICKEN || entity.getType() == EntityType.RABBIT || entity.getType() == EntityType.PARROT || entity.getType() == EntityType.OCELOT || entity.getType() == EntityType.PANDA || entity.getType() == EntityType.MUSHROOM_COW || entity.getType() == EntityType.BEE || entity.getType() == EntityType.CHEST_BOAT || entity.getType() == EntityType.CREEPER || entity.getType() == EntityType.FOX) {
                                    e.setCancelled(true);
                                    p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas intéragir avec cette entité ici.");
                                }
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) { throw new RuntimeException(ex); }
    }

    @EventHandler
    public void chunkdamage(EntityDamageByEntityEvent e) {
        if((e.getDamager() instanceof Player p)) {
            Entity targ = e.getEntity();
            chuckFunction.prepare(targ);
            String chunk = chuckFunction.detect(targ);

            try {
                if (!administrationSystemFunction.bypassclaim(p)) {
                    if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                        if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                            String town = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
                            if (!townManagementFunction.doesOwner(town, p)) {
                                if (!townManagementFunction.doesMember(town, p)) {
                                    e.setCancelled(true);
                                    p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas infliger des dégâts ici.");
                                }
                            }
                        }
                    }
                }
            } catch (FileNotFoundException ex) {throw new RuntimeException(ex);}
        }
    }

    @EventHandler
    public void chunkexplosions(EntityExplodeEvent e) {
        chuckFunction.prepare(e.getEntity());
        String chunk = chuckFunction.detect(e.getEntity());
        try {
            if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                    e.setCancelled(true);
                }
            }
        } catch (FileNotFoundException ex) {throw new RuntimeException(ex);}
    }


    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        System.out.println("Stade 1 TNT");
        // Vérifier si l'entité qui change le bloc est du type TNT
        if (event.getEntityType() == EntityType.MINECART_TNT || event.getEntityType() == EntityType.PRIMED_TNT) {
            // Récupérer le chunk où la TNT essaie de modifier un bloc
            chuckFunction.prepare(event.getBlock());
            
            String chunk = chuckFunction.detect(event.getBlock());
            System.out.println("Stade 2 TNT");
            // Vérifier si le chunk est protégé
            try {
                System.out.println("Stade 3 TNT");
                if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                    System.out.println("Stade 4 TNT");
                    if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                        System.out.println("Stade 5 TNT");
                        event.setCancelled(true);
                    } else {
                        System.out.println("Else a isEmpty Owner");
                    }
                } else {
                    System.out.println("Else a does dataManagementFunction exist");
                }
            } catch (FileNotFoundException ex) {throw new RuntimeException(ex);}
        } else {
            System.out.println("else à Stade 1 TNT");
        }
    }

    @EventHandler
    public void chunkteleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        chuckFunction.prepare(Objects.requireNonNull(e.getTo()));
        String chunk = chuckFunction.detect(Objects.requireNonNull(e.getTo()));

        try {
            if (!administrationSystemFunction.bypassclaim(p)) {
                if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                    if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                        String town = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
                        if (!townManagementFunction.doesOwner(town, p)) {
                            if (!townManagementFunction.doesMember(town, p)) {
                                e.setCancelled(true);
                                p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas vous téléporter à " + e.getTo().getX() + " " + e.getTo().getY() + " " + e.getTo().getZ() + ".");
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {throw new RuntimeException(ex);}
    }

    @EventHandler
    public void chunksplashpotion(PotionSplashEvent e) {
        if(e.getEntity() instanceof Player p) {
            chuckFunction.prepare(e.getPotion());
            String chunk = chuckFunction.detect(e.getPotion());

            try {
                if (!administrationSystemFunction.bypassclaim(p)) {
                    if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                        if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                            String town = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
                            if (!townManagementFunction.doesOwner(town, p)) {
                                if (!townManagementFunction.doesMember(town, p)) {
                                    e.setCancelled(true);
                                    p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas lancer de potion ici.");
                                }
                            }
                        }
                    }
                }
            } catch (FileNotFoundException ex) {throw new RuntimeException(ex);}
        }
    }

    public boolean isDesiredBlockType(Block block) {
        return block != null && (isBed(block) || isRail(block) || isCandle(block) || isButton(block) || isChest(block) || isShulkerBox(block) || isDoor(block) || isTrapDoor(block) || isMinecart(block) || isCauldron(block) || isTable(block) || isFenceGate(block) || isPressurePlate(block));
    }

    public boolean isDesiredItemType(Material item) {
        return item != null && (isPotion(item));
    }

    public boolean isShulkerBox(Block block) {
        return block.getType().name().endsWith("_SHULKER_BOX");
    }

    public boolean isButton(Block block) {
        return block.getType().name().endsWith("_BUTTON");
    }

    public boolean isRail(Block block) {
        return block.getType().name().endsWith("RAIL");
    }

    public boolean isDoor(Block block) {
        return block.getType().name().endsWith("_DOOR");
    }

    public boolean isTrapDoor(Block block) {
        return block.getType().name().endsWith("_TRAPDOOR");
    }

    public boolean isChest(Block block) {
        return block.getType().name().endsWith("CHEST");
    }

    public boolean isMinecart(Block block) {
        return block.getType().name().endsWith("_MINECART");
    }

    public boolean isCauldron(Block block) {
        return block.getType().name().endsWith("_CAULDRON");
    }

    public boolean isTable(Block block) {
        return block.getType().name().endsWith("_TABLE");
    }

    public boolean isBed(Block block) {
        return block.getType().name().endsWith("_BED");
    }

    public boolean isFenceGate(Block block) {
        return block.getType().name().endsWith("_FENCE_GATE");
    }

    public boolean isPressurePlate(Block block) {
        return block.getType().name().endsWith("_PRESSURE_PLATE");
    }

    public boolean isCandle(Block block) {
        return block.getType().name().endsWith("_CANDLE");
    }

    public boolean isPotion(Material item) {
        return item.name().endsWith("POTION");
    }

    @EventHandler
    public void chunkuse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        chuckFunction.prepare(p);
        if(e.getClickedBlock() != null) {
            String chunk = chuckFunction.detect(e.getClickedBlock());

            Block clickedBlock = e.getClickedBlock();

            Material[] unAllowedItems = {
                    Material.BUCKET,
                    Material.WATER_BUCKET,
                    Material.LAVA_BUCKET,
                    Material.TROPICAL_FISH_BUCKET,
                    Material.COD_BUCKET,
                    Material.MILK_BUCKET,
                    Material.POWDER_SNOW_BUCKET,
                    Material.AXOLOTL_BUCKET,
                    Material.PUFFERFISH_BUCKET,
                    Material.SALMON_BUCKET,
                    Material.TADPOLE_BUCKET,
                    Material.FLINT_AND_STEEL,
            };

            Material[] unAllowedBlocks = {
                    Material.ENCHANTING_TABLE,
                    Material.RESPAWN_ANCHOR,
                    Material.REPEATER,
                    Material.BREWING_STAND,
                    Material.ANVIL,
                    Material.BEACON,
                    Material.TNT,
                    Material.ARMOR_STAND,
                    Material.CAKE,
                    Material.BARREL,
                    Material.ITEM_FRAME,
                    Material.DROPPER,
                    Material.BELL,
                    Material.FARMLAND,
                    Material.END_PORTAL_FRAME,
                    Material.STONECUTTER,
                    Material.TNT_MINECART,
                    Material.JUKEBOX,
                    Material.NOTE_BLOCK,
                    Material.SHULKER_BOX,
                    Material.BEE_NEST,
                    Material.BEEHIVE,
                    Material.WRITABLE_BOOK,
                    Material.LECTERN,
                    Material.CAMPFIRE,
                    Material.COMPOSTER,
                    Material.COMPARATOR,
                    Material.REDSTONE_LAMP,
                    Material.DAYLIGHT_DETECTOR,
                    Material.OBSERVER,
                    Material.DISPENSER,
                    Material.TRIPWIRE,
                    Material.BAMBOO_CHEST_RAFT,
                    Material.CHIPPED_ANVIL,
                    Material.DAMAGED_ANVIL,
                    Material.GRINDSTONE,
                    Material.LOOM,
                    Material.SOUL_CAMPFIRE,
                    Material.LIGHTNING_ROD,
                    Material.GLOW_ITEM_FRAME,
                    Material.DECORATED_POT,
                    Material.FLOWER_POT,
                    Material.LODESTONE,
                    Material.CHISELED_BOOKSHELF,
                    Material.HOPPER,
                    Material.SUSPICIOUS_GRAVEL,
                    Material.SUSPICIOUS_SAND,
                    Material.SUSPICIOUS_STEW,
                    // Fours
                    Material.BLAST_FURNACE,
                    Material.FURNACE,
                    Material.SMOKER,
                    // Leviers
                    Material.LEVER,
                    // Ajoutez d'autres types de blocs ici si nécessaire
            };

            try {
                if (!administrationSystemFunction.bypassclaim(p)) {
                    if (e.getItem() != null && containsMaterial(e.getItem().getType(), unAllowedItems)) {
                        if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                            if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                                String town = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
                                if (!townManagementFunction.doesOwner(town, p)) {
                                    if (!townManagementFunction.doesMember(town, p)) {
                                        e.setCancelled(true);
                                        p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas interagir avec cet item.");
                                    }
                                }
                            }
                        }
                    } else if (e.getItem() != null && isDesiredItemType(e.getItem().getType())) {
                        if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                            if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                                String town = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
                                if (!townManagementFunction.doesOwner(town, p)) {
                                    if (!townManagementFunction.doesMember(town, p)) {
                                        e.setCancelled(true);
                                        p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas interagir avec cet item.");
                                    }
                                }
                            }
                        }
                    }
                    // Blocs
                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
                        if (clickedBlock != null && containsMaterial(clickedBlock.getType(), unAllowedBlocks)) {
                            if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                                if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                                    String town = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
                                    if (!townManagementFunction.doesOwner(town, p)) {
                                        if (!townManagementFunction.doesMember(town, p)) {
                                            e.setCancelled(true);
                                            p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas interagir avec ce block.");
                                        }
                                    }
                                }
                            }
                        } else if (clickedBlock != null && isDesiredBlockType(clickedBlock)) {
                            if (dataManagementFunction.doesDataExist(chunk, "/chunks/", "townowner")) {
                                if (!dataManagementFunction.loadData(chunk, "/chunks/", "townowner").isEmpty()) {
                                    String town = dataManagementFunction.loadData(chunk, "/chunks/", "townowner");
                                    if (!townManagementFunction.doesOwner(town, p)) {
                                        if (!townManagementFunction.doesMember(town, p)) {
                                            e.setCancelled(true);
                                            p.sendMessage("§c§lErreur §8§l»§r Vous ne pouvez pas interagir avec ce block.");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (FileNotFoundException ex) {throw new RuntimeException(ex);}
        }
    }

    private boolean containsMaterial(Material material, Material[] materials) {
        for (Material mat : materials) {
            if (mat == material) {
                return true;
            }
        }
        return false;
    }

}
