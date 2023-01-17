package io.github.maazapan.anvilrepair.manager.hook;

import io.github.maazapan.anvilrepair.AnvilRepair;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    private final AnvilRepair plugin;
    private static Economy econ = null;

    public VaultHook(AnvilRepair plugin) {
        this.plugin = plugin;
    }

    /**
     * Setup vault economy.
     */
    public boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        // create matrix

        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}