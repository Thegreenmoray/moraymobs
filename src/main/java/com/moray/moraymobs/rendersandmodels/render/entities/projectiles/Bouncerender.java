package com.moray.moraymobs.rendersandmodels.render.entities.projectiles;

import com.moray.moraymobs.entity.projectiles.Bouncy_ball;
import com.moray.moraymobs.rendersandmodels.model.entities.projectiles.Bounceballmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Bouncerender extends GeoEntityRenderer<Bouncy_ball> {
    public Bouncerender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Bounceballmodel());
    }
}
