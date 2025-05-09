package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.entity.projectiles.Geyser;

import com.moray.moraymobs.rendersandmodels.model.Whirlpoolmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Whirlpoolrender extends GeoEntityRenderer<Geyser> {
    public Whirlpoolrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Whirlpoolmodel());
    }
}
