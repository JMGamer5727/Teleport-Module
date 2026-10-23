package com.jmgamer5727.teleportmodule;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.core.Registry;

public class TeleportModule implements ModInitializer {
    public static final String MOD_ID = "teleport_module";

    public static final Item TELEPORT_MODULE = new Item(
        new Item.Properties().setId(
            net.minecraft.resources.ResourceKey.create(
                BuiltInRegistries.ITEM.key(),
                Identifier.fromNamespaceAndPath(MOD_ID, "teleport_module")
            )
        ).stacksTo(16)
    );

    @Override
    public void onInitialize() {
        Registry.register(
            BuiltInRegistries.ITEM,
            Identifier.fromNamespaceAndPath(MOD_ID, "teleport_module"),
            TELEPORT_MODULE
        );
    }
}
