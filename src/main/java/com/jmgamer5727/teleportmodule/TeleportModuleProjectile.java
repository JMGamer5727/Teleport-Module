package com.jmgamer5727.teleportmodule;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TeleportModuleProjectile extends ThrowableItemProjectile {

public TeleportModuleProjectile(
    EntityType<? extends TeleportModuleProjectile> type,
    Level level
) {
    super(type, level);
}

public TeleportModuleProjectile(
    Level level,
    LivingEntity owner,
    ItemStack itemStack
) {
    super(
        TeleportModuleEntities.TELEPORT_MODULE_PROJECTILE,
        owner,
        level,
        itemStack
    );
}

@Override
protected Item getDefaultItem() {
    return TeleportModule.TELEPORT_MODULE;
}

}
