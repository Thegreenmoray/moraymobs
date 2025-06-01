package com.moray.moraymobs.rendersandmodels.render.entities.projectiles;

import com.moray.moraymobs.entity.projectiles.Sea_Mine;
import com.moray.moraymobs.rendersandmodels.model.entities.projectiles.SeamineModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Seaminerenderer extends GeoEntityRenderer<Sea_Mine> {
    public Seaminerenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SeamineModel());
    }
}
