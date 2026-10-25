package com.jmgamer5727.teleportmodule;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

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

@Override
public void tick() {
    super.tick();

    if (!isNoGravity() && !isRemoved()) {
    Vec3 velocity = getDeltaMovement();
    setDeltaMovement(
        velocity.x,
        velocity.y - 0.015,
        velocity.z
    );
}
}

@Override
protected void onHit(HitResult hitResult) {
    super.onHit(hitResult);

    if (!(level() instanceof ServerLevel serverLevel)) {
        return;
    }

    Entity owner = getOwner();

    if (!(owner instanceof ServerPlayer player)) {
        discard();
        return;
    }

    double targetX = getX();
    double targetY = getY();
    double targetZ = getZ();

    if (hitResult instanceof BlockHitResult blockHit) {
        BlockPos blockPos = blockHit.getBlockPos();
        var direction = blockHit.getDirection();

        targetX = blockPos.getX() + 0.5 + direction.getStepX() * 0.5;
        targetY = blockPos.getY() + 0.5 + direction.getStepY() * 0.5;
        targetZ = blockPos.getZ() + 0.5 + direction.getStepZ() * 0.5;
    } else if (hitResult instanceof EntityHitResult entityHit) {
        Entity hitEntity = entityHit.getEntity();
        targetX = hitEntity.getX();
        targetY = hitEntity.getY();
        targetZ = hitEntity.getZ();
    }

    player.teleportTo(
        serverLevel,
        targetX,
        targetY,
        targetZ,
        Set.of(),
        player.getYRot(),
        player.getXRot(),
        false
    );

    discard();
}

}
