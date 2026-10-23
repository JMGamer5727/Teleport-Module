package com.jmgamer5727.teleportmodule;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TeleportModuleItem extends Item {

public TeleportModuleItem(Properties properties) {
    super(properties);
}

@Override
public InteractionResult use(
    Level level,
    Player player,
    InteractionHand hand
) {
    ItemStack stack = player.getItemInHand(hand);

    if (!level.isClientSide()) {
        // Projectile spawning will be added after this API
        // step is verified.
    }

    return InteractionResult.SUCCESS;
}

}
