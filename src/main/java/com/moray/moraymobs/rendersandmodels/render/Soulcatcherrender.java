package com.moray.moraymobs.rendersandmodels.render;

import com.moray.moraymobs.entity.living.monster.Soulcatcher;
import com.moray.moraymobs.rendersandmodels.model.Soulcatchermodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Soulcatcherrender extends GeoEntityRenderer<Soulcatcher> {
    public Soulcatcherrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Soulcatchermodel());
    }

    @Override
    protected float getDeathMaxRotation(Soulcatcher entityLivingBaseIn) {
        return 0.0F;
    }


}
