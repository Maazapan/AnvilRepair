package io.github.maazapan.anvilrepair;

import io.github.maazapan.anvilrepair.commands.AnvilCommand;
import io.github.maazapan.anvilrepair.listener.PlayerListener;
import io.github.maazapan.anvilrepair.manager.AnvilManager;
import io.github.maazapan.anvilrepair.manager.hook.VaultHook;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnvilRepair extends JavaPlugin {

    private AnvilManager anvilManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        anvilManager = new AnvilManager(this);

        this.saveDefaultConfig();
        this.registerListener();
        this.registerCommands();
  //      this.registerHooks();
    }

    private void registerCommands() {
        this.getCommand("anvil").setExecutor(new AnvilCommand());
    }

    private void registerListener() {
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    private void registerHooks() {
        VaultHook vaultHook = new VaultHook(this);

        if (!vaultHook.setupEconomy()) {
            getLogger().warning("Han occurred error Vault economy not hooked.");
            return;
        }
        getLogger().info("Success Vault economy hooked.");
    }

    public AnvilManager getAnvilManager() {
        return anvilManager;
    }
}
