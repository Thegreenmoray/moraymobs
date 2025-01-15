package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.entity.living.animal.LavaPaddleFish;
import com.moray.moraymobs.rendersandmodels.model.Paddlefishmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Paddlefishrender extends GeoEntityRenderer<LavaPaddleFish> {
    public Paddlefishrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Paddlefishmodel());
    }
}
