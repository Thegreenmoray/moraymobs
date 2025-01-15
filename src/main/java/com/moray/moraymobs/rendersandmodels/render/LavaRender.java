package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.entity.projectiles.Fireheap;
import com.moray.moraymobs.rendersandmodels.model.Lavamodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LavaRender extends GeoEntityRenderer<Fireheap> {
    public LavaRender(EntityRendererProvider.Context renderManager) {
        super(renderManager,new Lavamodel());
    }
}
