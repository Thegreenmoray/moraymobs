package com.moray.moraymobs.rendersandmodels.render.entities.projectiles;

import com.moray.moraymobs.entity.projectiles.Microprojectile;
import com.moray.moraymobs.rendersandmodels.model.entities.projectiles.Microprojectilemodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Microprojectilerander extends GeoEntityRenderer<Microprojectile> {
    public Microprojectilerander(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Microprojectilemodel());
    }
}
