package com.moray.moraymobs.rendersandmodels.render.entities.monsters;

import com.moray.moraymobs.entity.living.dungeonentities.Walliserops;
import com.moray.moraymobs.rendersandmodels.model.entities.monster.Walliseropsmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Walliseropsrenderer extends GeoEntityRenderer<Walliserops> {
    public Walliseropsrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Walliseropsmodel());
    }

    protected float getDeathMaxRotation(Walliserops entityLivingBaseIn) {
        return 0.0F;
    }


}
