package com.jmgamer5727.teleportmodule;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class TeleportModuleProjectile extends ThrowableItemProjectile {

    public TeleportModuleProjectile(
        EntityType<? extends TeleportModuleProjectile> type,
        Level level
    ) {
        super(type, level);
    }

    @Override
    protected Item getDefaultItem() {
        return TeleportModule.TELEPORT_MODULE;
    }
}
