package io.github.maazapan.anvilrepair.manager;

import de.tr7zw.changeme.nbtapi.NBTBlock;
import io.github.maazapan.anvilrepair.AnvilRepair;
import io.github.maazapan.anvilrepair.manager.editing.EditingItem;
import io.github.maazapan.anvilrepair.manager.hook.VaultHook;
import io.github.maazapan.anvilrepair.utils.KatsuUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnvilManager {

    private final Map<UUID, EditingItem> editingItemMap;
    private final AnvilRepair plugin;

    public AnvilManager(AnvilRepair plugin) {
        this.editingItemMap = new HashMap<>();
        this.plugin = plugin;
    }

    /**
     * Send anvil animation at player, and repair item.
     *
     * @param player    Player Interacted
     * @param block     Block AnvilBlock
     * @param itemStack ItemStack Item to repair
     */
    public void sendAnimation(Player player, Block block, ItemStack itemStack) {
        FileConfiguration config = plugin.getConfig();
        NBTBlock nbtBlock = new NBTBlock(block);
        Economy economy = VaultHook.getEconomy();

        // Check player has money and if the player has permission bypass the cost.
        if (economy != null) {
            double cost = config.getDouble("cost");

            if (!player.hasPermission("anvilrepair.bypass")) {
                if (!economy.has(player, cost)) {
                    player.sendMessage(KatsuUtils.coloredHex(config.getString("no-money")));
                    return;
                }
                economy.withdrawPlayer(player, cost);
            }
        }

        nbtBlock.getData().setBoolean("anvil-repair-using", true);
        player.getInventory().setItemInMainHand(null);

        // Spawn Custom drop item.
        Item dropItem = player.getWorld().dropItem(block.getLocation().add(+0.5, +1, +0.5), itemStack);
        dropItem.setVelocity(new Vector(0, 0, 0));
        dropItem.setPickupDelay(999999999);
        dropItem.setInvulnerable(true);

        this.getEditingItem().put(player.getUniqueId(), new EditingItem(itemStack, dropItem));

        // Start animation.
        new AnvilTask(this, player, block).runTaskTimer(plugin, 0, 10);
    }

    /**
     * Terminate anvil animation, and give item to player.
     * and set available to use anvil block.
     *
     * @param player Player Interacted
     * @param block  Block AnvilBlock
     */
    public void terminateAnimation(Player player, Block block) {
        NBTBlock nbtBlock = new NBTBlock(block);
        nbtBlock.getData().setBoolean("anvil-repair-using", false);

        EditingItem editingItem = this.getEditingItem().get(player.getUniqueId());

        // Repair item.
        ItemStack itemStack = editingItem.getItemStack();
        itemStack.setDurability((short) 0);

        player.getInventory().addItem(itemStack);
        editingItem.getDropItem().remove();

        this.getEditingItem().remove(player.getUniqueId());
    }

    /**
     * Remove nbt at anvil, and set available to use anvil block.
     * to use anvil block.
     *
     * @param block Block AnvilBlock
     */
    public void terminateAnimation(Block block) {
        NBTBlock nbtBlock = new NBTBlock(block);
        nbtBlock.getData().setBoolean("anvil-repair-using", false);
    }

    public EditingItem getEditingItem(Player player) {
        return editingItemMap.get(player.getUniqueId());
    }

    public boolean isEditingItem(Player player) {
        return this.editingItemMap.containsKey(player.getUniqueId());
    }

    public Map<UUID, EditingItem> getEditingItem() {
        return editingItemMap;
    }
}
