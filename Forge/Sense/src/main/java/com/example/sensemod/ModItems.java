package com.example.sensemod.item;

import com.example.sensemod.SenseMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SenseMod.MODID);

    public static final RegistryObject<Item> SENSE = ITEMS.register("sense",
            () -> new SwordItem(
                    Tiers.IRON,
                    3,              // zusätzlicher Schaden
                    -2.4F,          // Angriffsgeschwindigkeit (Eisenschwert)
                    new SenseItem().getProperties()
            ));
}
