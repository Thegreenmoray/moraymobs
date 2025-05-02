package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.entity.living.monster.Moray;
import com.moray.moraymobs.rendersandmodels.model.Omnidensmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Omnidensrender extends GeoEntityRenderer<Omnidens> {
    public Omnidensrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Omnidensmodel());
    }

    @Override
    protected float getDeathMaxRotation(Omnidens entityLivingBaseIn) {
        return 0.0F;
    }


}
