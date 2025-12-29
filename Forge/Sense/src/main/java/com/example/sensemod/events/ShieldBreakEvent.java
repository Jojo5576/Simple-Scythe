package com.example.sensemod.events;

import com.example.sensemod.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ShieldBreakEvent {

    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {

        if (!(event.getSource().getEntity() instanceof Player player)) return;

        ItemStack weapon = player.getMainHandItem();

        if (weapon.is(ModItems.SENSE.get())) {
            LivingEntity target = event.getEntity();

            if (target.isBlocking()) {
                target.disableShield(true);
            }
        }
    }
}
