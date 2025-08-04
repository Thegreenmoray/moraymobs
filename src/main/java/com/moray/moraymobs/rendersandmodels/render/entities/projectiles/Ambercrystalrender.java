package com.moray.moraymobs.rendersandmodels.render.entities.projectiles;

import com.moray.moraymobs.entity.projectiles.Ambercrystal;
import com.moray.moraymobs.rendersandmodels.model.entities.projectiles.Ambercrystalmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Ambercrystalrender extends GeoEntityRenderer<Ambercrystal> {
    public Ambercrystalrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Ambercrystalmodel());
    }
}
