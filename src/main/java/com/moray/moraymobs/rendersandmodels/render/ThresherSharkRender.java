package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.entity.living.animal.Thresher_shark;
import com.moray.moraymobs.rendersandmodels.model.ThresherSharkmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ThresherSharkRender extends GeoEntityRenderer<Thresher_shark> {
    public ThresherSharkRender(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ThresherSharkmodel());
    }
}
