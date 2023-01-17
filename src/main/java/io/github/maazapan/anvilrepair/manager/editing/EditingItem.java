package io.github.maazapan.anvilrepair.manager.editing;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class EditingItem {

    private final ItemStack itemStack;
    private final Item dropItem;

    public EditingItem(ItemStack itemStack, Item dropItem) {
        this.itemStack = itemStack;
        this.dropItem = dropItem;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Item getDropItem() {
        return dropItem;
    }
}
