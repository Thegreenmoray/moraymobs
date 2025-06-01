package com.moray.moraymobs.rendersandmodels.render.entities.monsters;

import com.moray.moraymobs.entity.living.dungeonentities.Microdictyon;
import com.moray.moraymobs.rendersandmodels.model.entities.monster.Microdictyonmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Microdictyonrenderer extends GeoEntityRenderer<Microdictyon> {
    public Microdictyonrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Microdictyonmodel());
    }


    protected float getDeathMaxRotation(Microdictyon entityLivingBaseIn) {
        return 0.0F;
    }

}
