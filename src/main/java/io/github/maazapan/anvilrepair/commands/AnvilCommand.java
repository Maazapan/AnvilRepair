package io.github.maazapan.anvilrepair.commands;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.maazapan.anvilrepair.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnvilCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("anvilrepair.use")) {
                ItemBuilder itemBuilder = new ItemBuilder(Material.ANVIL)
                        .setName("&aYunque magico")
                        .setLore("&7Puedes reparar cualquier item", "&7colocalo en el suelo.");

                NBTItem nbtItem = new NBTItem(itemBuilder.toItemStack());
                nbtItem.setString("anvil-repair-item", "anvil");
                nbtItem.applyNBT(itemBuilder.toItemStack());

                player.getInventory().addItem(itemBuilder.toItemStack());
            }
        }
        return false;
    }
}
