package com.example.senseplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class SenseListener implements Listener {

    private final JavaPlugin plugin;
    private final HashMap<UUID, Long> cooldownMap = new HashMap<>();

    public SenseListener(JavaPlugin plugin) {
        this.plugin = plugin;
        registerRecipe();
    }

    private void registerRecipe() {
        ItemStack sense = createSense();
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "sense"), sense);
        recipe.shape(" II", " SI", " S ");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(recipe);
    }

    private ItemStack createSense() {
        ItemStack sense = new ItemStack(Material.IRON_HOE);
        ItemMeta meta = sense.getItemMeta();
        meta.setDisplayName("§cEisensense");
        meta.setLore(Arrays.asList("§7Eine mächtige Sense"));
        sense.setItemMeta(meta);
        return sense;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item.hasItemMeta() && "§cEisensense".equals(item.getItemMeta().getDisplayName())) {

            long now = System.currentTimeMillis();
            if (cooldownMap.containsKey(p.getUniqueId()) && cooldownMap.get(p.getUniqueId()) > now) return;

            Vector direction = p.getLocation().getDirection().multiply(1.5);
            p.setVelocity(direction);

            p.getWorld().spawnParticle(Particle.SWEEP_ATTACK, p.getLocation().add(0,1,0), 15, 0.3, 0, 0.3);

            cooldownMap.put(p.getUniqueId(), now + 3000);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player p)) return;

        ItemStack item = p.getInventory().getItemInMainHand();
        if (item.hasItemMeta() && "§cEisensense".equals(item.getItemMeta().getDisplayName())) {
            if (e.getEntity() instanceof Player target && target.isBlocking()) {
                target.setBlocking(false);
            }
        }
    }
}
