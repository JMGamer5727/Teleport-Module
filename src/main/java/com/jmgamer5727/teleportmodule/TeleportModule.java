package com.jmgamer5727.teleportmodule;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class TeleportModule implements ModInitializer {
    public static final String MOD_ID = "teleport_module";

    public static final ResourceKey<Item> TELEPORT_MODULE_KEY =
        ResourceKey.create(
            BuiltInRegistries.ITEM.key(),
            Identifier.fromNamespaceAndPath(
                MOD_ID,
                "teleport_module"
            )
        );

    public static final Item TELEPORT_MODULE =
        new TeleportModuleItem(
            new Item.Properties()
                .setId(TELEPORT_MODULE_KEY)
                .stacksTo(16)
        );

    @Override
    public void onInitialize() {
        Registry.register(
            BuiltInRegistries.ITEM,
            Identifier.fromNamespaceAndPath(
                MOD_ID,
                "teleport_module"
            ),
            TELEPORT_MODULE
        );
    }
}
