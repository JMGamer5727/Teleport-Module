package com.jmgamer5727.teleportmodule;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.projectile.Projectile;
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

    if (level instanceof ServerLevel serverLevel) {
        Projectile.spawnProjectileFromRotation(
            TeleportModuleProjectile::new,
            serverLevel,
            stack,
            player,
            0.0F,
            2.5F,
            0.0F
        );
    }

    player.awardStat(Stats.ITEM_USED.get(this));
    stack.consume(1, player);

    return InteractionResult.SUCCESS;
}

}
