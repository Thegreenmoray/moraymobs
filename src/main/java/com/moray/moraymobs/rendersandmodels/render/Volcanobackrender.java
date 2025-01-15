package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.entity.living.monster.Volcanoback;
import com.moray.moraymobs.rendersandmodels.model.Volcanobackmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Volcanobackrender extends GeoEntityRenderer<Volcanoback> {
    public Volcanobackrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Volcanobackmodel());
    }



    @Override
    protected float getDeathMaxRotation(Volcanoback entityLivingBaseIn) {
        return 0.0F;
    }

}
