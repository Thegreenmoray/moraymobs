package com.moray.moraymobs.rendersandmodels.render.entities.monsters;

import com.moray.moraymobs.entity.living.dungeonentities.Schinderhannes;
import com.moray.moraymobs.rendersandmodels.model.entities.monster.Schinderhannesmodels;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Schinderhannesrenderer extends GeoEntityRenderer<Schinderhannes> {
    public Schinderhannesrenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Schinderhannesmodels());
    }



    protected float getDeathMaxRotation(Schinderhannes entityLivingBaseIn) {
        return 0.0F;
    }



}
