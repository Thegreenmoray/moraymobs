package com.moray.moraymobs.rendersandmodels.render.entities.monsters;

import com.moray.moraymobs.entity.living.boss.Omnidens;
import com.moray.moraymobs.rendersandmodels.model.entities.monster.Omnidensmodel;
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
