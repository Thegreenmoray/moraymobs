package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.entity.living.monster.Body_Snatcher;
import com.moray.moraymobs.rendersandmodels.model.Bodysnatchermodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Bodysnatcherrenderer extends GeoEntityRenderer<Body_Snatcher> {
    public Bodysnatcherrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Bodysnatchermodel());
    }
}
