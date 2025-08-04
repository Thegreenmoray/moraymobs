package com.moray.moraymobs.rendersandmodels.render.entities.projectiles;

import com.moray.moraymobs.entity.projectiles.friendlyprojectile.Amberportal;
import com.moray.moraymobs.rendersandmodels.model.entities.projectiles.Amberportalmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Amberportalrenderer extends GeoEntityRenderer<Amberportal> {
    public Amberportalrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Amberportalmodel());
    }
}
