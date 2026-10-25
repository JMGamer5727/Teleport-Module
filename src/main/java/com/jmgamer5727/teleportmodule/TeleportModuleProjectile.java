package com.jmgamer5727.teleportmodule;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
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

        // Preserve the entity position-swap feature.
        if (hitResult instanceof EntityHitResult entityHit) {
            Entity hitEntity = entityHit.getEntity();

            if (hitEntity == player || hitEntity.isRemoved()) {
                discard();
                return;
            }

            double playerX = player.getX();
            double playerY = player.getY();
            double playerZ = player.getZ();

            double targetX = hitEntity.getX();
            double targetY = hitEntity.getY();
            double targetZ = hitEntity.getZ();

            hitEntity.teleportTo(playerX, playerY, playerZ);

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
            return;
        }

        // Find a position outside the block that was hit.
        if (hitResult instanceof BlockHitResult blockHit) {
            Vec3 safePosition = findSafeBlockPosition(
                serverLevel,
                player,
                blockHit.getBlockPos(),
                blockHit.getDirection()
            );

            // Never teleport into a block if no safe position is found.
            if (safePosition == null) {
                discard();
                return;
            }

            player.teleportTo(
                serverLevel,
                safePosition.x,
                safePosition.y,
                safePosition.z,
                Set.of(),
                player.getYRot(),
                player.getXRot(),
                false
            );

            discard();
            return;
        }

        // Preserve the original behavior for other hit types.
        player.teleportTo(
            serverLevel,
            getX(),
            getY(),
            getZ(),
            Set.of(),
            player.getYRot(),
            player.getXRot(),
            false
        );

        discard();
    }

    private Vec3 findSafeBlockPosition(
        ServerLevel level,
        ServerPlayer player,
        BlockPos blockPos,
        Direction direction
    ) {
        double halfWidth = player.getBbWidth() / 2.0;
        double halfHeight = player.getBbHeight() / 2.0;

        // Account for the player's size along the impacted face.
        double playerExtent = direction.getAxis().isVertical()
            ? halfHeight
            : halfWidth;

        double baseX = blockPos.getX() + 0.5
            + direction.getStepX() * (0.5 + playerExtent + 0.05);

        double baseY = blockPos.getY() + 0.5
            + direction.getStepY() * (0.5 + playerExtent + 0.05);

        double baseZ = blockPos.getZ() + 0.5
            + direction.getStepZ() * (0.5 + playerExtent + 0.05);

        // Try progressively farther positions away from the impacted face.
        for (double extra = 0.0; extra <= 1.5; extra += 0.1) {
            double x = baseX + direction.getStepX() * extra;
            double y = baseY + direction.getStepY() * extra;
            double z = baseZ + direction.getStepZ() * extra;

            double dx = x - player.getX();
            double dy = y - player.getY();
            double dz = z - player.getZ();

            AABB destinationBox = player.getBoundingBox().move(
                dx,
                dy,
                dz
            );

            if (level.noCollision(player, destinationBox)) {
                return new Vec3(x, y, z);
            }
        }

        return null;
    }
}
