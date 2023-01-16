package io.github.maazapan.anvilrepair.listener;

import de.tr7zw.changeme.nbtapi.NBTBlock;
import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.maazapan.anvilrepair.AnvilRepair;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

    private AnvilRepair plugin;

    public PlayerListener(AnvilRepair plugin) {
        this.plugin = plugin;
    }

    /**
     * Check player is interacted with left click at anvil block.
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public void onInteractBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            NBTBlock nbtBlock = new NBTBlock(block);

            if (nbtBlock.getData().hasTag("anvil-repair-block")) {

            }
        }
    }

    /**
     * Check player is breaking anvil custom block.
     * @param event BlockBreakEvent
     */
    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        NBTBlock nbtBlock = new NBTBlock(event.getBlock());

        if(nbtBlock.getData().hasTag("anvil-repair-block")){
            nbtBlock.getData().removeKey("anvil-repair-block");
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
        }
    }
}
