package com.jmgamer5727.teleportmodule;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class TeleportModuleClient implements ClientModInitializer {

@Override
public void onInitializeClient() {
    EntityRenderers.register(
        TeleportModuleEntities.TELEPORT_MODULE_PROJECTILE,
        ThrownItemRenderer::new
    );
}

}
