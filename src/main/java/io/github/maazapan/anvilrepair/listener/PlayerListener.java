package io.github.maazapan.anvilrepair.listener;

import io.github.maazapan.anvilrepair.AnvilRepair;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener {

    private AnvilRepair plugin;

    public PlayerListener(AnvilRepair plugin){
        this.plugin = plugin;
    }


    public void onInteractBlock(){

    }
}
