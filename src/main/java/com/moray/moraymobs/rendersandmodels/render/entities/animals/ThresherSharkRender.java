package com.moray.moraymobs.rendersandmodels.render.entities.animals;

import com.moray.moraymobs.entity.living.animalornpc.Thresher_shark;
import com.moray.moraymobs.rendersandmodels.model.entities.animals.ThresherSharkmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ThresherSharkRender extends GeoEntityRenderer<Thresher_shark> {
    public ThresherSharkRender(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ThresherSharkmodel());
    }
}
