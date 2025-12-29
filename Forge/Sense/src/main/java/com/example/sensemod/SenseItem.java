package com.example.sensemod.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SenseItem extends Item {

    private static final int DASH_COOLDOWN = 60; // 3 Sekunden (20 Ticks = 1 Sek)
    private static final double DASH_STRENGTH = 1.5;

    public SenseItem() {
        super(new Item.Properties()
                .durability(500)
                .stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {

            if (!player.getCooldowns().isOnCooldown(this)) {

                Vec3 look = player.getLookAngle();
                player.setDeltaMovement(
                        look.x * DASH_STRENGTH,
                        0.1,
                        look.z * DASH_STRENGTH
                );
                player.hurtMarked = true;

                // Partikel
                ServerLevel serverLevel = (ServerLevel) level;
                for (int i = 0; i < 15; i++) {
                    serverLevel.sendParticles(
                            ParticleTypes.SWEEP_ATTACK,
                            player.getX(),
                            player.getY() + 1.0,
                            player.getZ(),
                            1,
                            look.x * 0.3,
                            0.0,
                            look.z * 0.3,
                            0.0
                    );
                }

                // Cooldown
                player.getCooldowns().addCooldown(this, DASH_COOLDOWN);

                // Durability-Verlust
                stack.hurtAndBreak(1, player,
                        p -> p.broadcastBreakEvent(hand));
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
