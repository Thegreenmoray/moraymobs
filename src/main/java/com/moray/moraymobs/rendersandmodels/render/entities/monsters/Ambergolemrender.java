package com.moray.moraymobs.rendersandmodels.render.entities.monsters;

import com.moray.moraymobs.entity.living.dungeonentities.Schinderhannes;
import com.moray.moraymobs.entity.living.monster.Amber_golem;
import com.moray.moraymobs.entity.living.monster.Body_Snatcher;
import com.moray.moraymobs.rendersandmodels.model.entities.monster.Ambergolemmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Ambergolemrender extends GeoEntityRenderer<Amber_golem> {
    public Ambergolemrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Ambergolemmodel());
    }

    protected float getDeathMaxRotation(Amber_golem entityLivingBaseIn) {
        return 0.0F;
    }


}
