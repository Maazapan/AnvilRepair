package io.github.maazapan.anvilrepair.manager;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AnvilTask extends BukkitRunnable {

    private final AnvilManager anvilManager;

    private final Player player;
    private final Block block;

    private int time = 0;

    public AnvilTask(AnvilManager anvilManager, Player player, Block block) {
        this.anvilManager = anvilManager;
        this.player = player;
        this.block = block;
    }

    @Override
    public void run() {
        if (time < 5) {

            // Execute if the player is offline and remove block nbt.
            if (player == null || !player.isOnline()) {
                anvilManager.terminateAnimation(block);
                this.cancel();
                return;
            }

            // Spawn anvil particles at up anvil location.
            player.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 1.4, 0.5), 30, 0.2, 0.25, 0.2, 0, Material.ANVIL.createBlockData());
            player.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_BREAK, 10, 1);

            if (time == 0 || time == 1) {
                player.getWorld().playSound(block.getLocation().add(0.5, 1.5, 0.5), Sound.BLOCK_ANVIL_LAND, 0.5f, 2f);
            }

            if (time == 2 || time == 3) {
                player.getWorld().playSound(block.getLocation().add(0.5, 1.5, 0.5), Sound.BLOCK_ANVIL_LAND, 0.5f, 1.5f);
            }

            if (time == 4) {
                player.getWorld().playSound(block.getLocation().add(0.5, 1.5, 0.5), Sound.BLOCK_ANVIL_LAND, 0.5f, 1.3f);
            }
            time++;
            return;
        }
        anvilManager.terminateAnimation(player, block);
        cancel();
    }
}
