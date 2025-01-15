package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.entity.projectiles.Soulpiece;
import com.moray.moraymobs.rendersandmodels.model.Soulballmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Soulballrenderer extends GeoEntityRenderer<Soulpiece> {
    public Soulballrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Soulballmodel());
    }
}
