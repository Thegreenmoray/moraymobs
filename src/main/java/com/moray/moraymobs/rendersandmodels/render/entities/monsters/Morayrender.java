package com.moray.moraymobs.rendersandmodels.render.entities.monsters;


import com.moray.moraymobs.entity.living.monster.Moray;
import com.moray.moraymobs.rendersandmodels.model.entities.monster.Moraymodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Morayrender extends GeoEntityRenderer<Moray> {
    public Morayrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Moraymodel());
    }


    @Override
    protected float getDeathMaxRotation(Moray entityLivingBaseIn) {
        return 0.0F;
    }



}
