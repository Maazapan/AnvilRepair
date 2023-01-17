package io.github.maazapan.anvilrepair.listener;

import de.tr7zw.changeme.nbtapi.NBTBlock;
import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.maazapan.anvilrepair.AnvilRepair;
import io.github.maazapan.anvilrepair.manager.AnvilManager;
import io.github.maazapan.anvilrepair.manager.editing.EditingItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class PlayerListener implements Listener {

    private final AnvilRepair plugin;

    public PlayerListener(AnvilRepair plugin) {
        this.plugin = plugin;
    }

    /**
     * Check player is interacted with left click at anvil block.
     *
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public void onInteractBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            NBTBlock nbtBlock = new NBTBlock(event.getClickedBlock());
            ItemStack itemStack = player.getInventory().getItemInMainHand();

            if (nbtBlock.getData().hasTag("anvil-repair-block")) {
                event.setCancelled(true);

                if (itemStack.getType() == Material.AIR && !nbtBlock.getData().getBoolean("anvil-repair-using")) return;
                Damageable damageable = (Damageable) itemStack.getItemMeta();
                AnvilManager anvilManager = plugin.getAnvilManager();


                if (damageable != null && damageable.hasDamage() && !anvilManager.isEditingItem(player)) {
                    anvilManager.sendAnimation(player, event.getClickedBlock(), itemStack);
                }
            }
        }
    }

    /**
     * Check player is breaking anvil custom block.
     *
     * @param event BlockBreakEvent
     */
    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        NBTBlock nbtBlock = new NBTBlock(event.getBlock());

        if (nbtBlock.getData().hasTag("anvil-repair-block")) {
            nbtBlock.getData().removeKey("anvil-repair-block");
            nbtBlock.getData().removeKey("anvil-repair-using");
        }
    }

    /**
     * Check player is placing anvil custom block.
     *
     * @param event BlockPlaceEvent
     */
    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (event.getItemInHand().getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(event.getItemInHand());

        if (nbtItem.hasTag("anvil-repair-item")) {
            NBTBlock nbtBlock = new NBTBlock(block);
            nbtBlock.getData().setString("anvil-repair-block", "repair-block");
            nbtBlock.getData().setBoolean("anvil-repair-using", false);
        }
    }

    /**
     * Check player is quit the server.
     * and if the player is editing an item, remove it from the map.
     *
     * @param event PlayerQuitEvent
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        AnvilManager anvilManager = plugin.getAnvilManager();
        Player player = event.getPlayer();

        if (anvilManager.isEditingItem(player)) {
            EditingItem editingItem = anvilManager.getEditingItem(player);

            player.getInventory().addItem(editingItem.getItemStack());
            editingItem.getDropItem().remove();

            anvilManager.getEditingItem().remove(player.getUniqueId());
        }
    }
}
