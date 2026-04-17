package com.moray.moraymobs.rendersandmodels.render.entities.monsters;

import com.moray.moraymobs.entity.living.animalornpc.Spriggan;
import com.moray.moraymobs.entity.living.monster.Body_Snatcher;
import com.moray.moraymobs.entity.living.monster.Dullahan;
import com.moray.moraymobs.rendersandmodels.model.entities.monster.Dullahanmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Dullahanrender extends GeoEntityRenderer<Dullahan> {
    public Dullahanrender(EntityRendererProvider.Context context) {
        super(context, new Dullahanmodel());
    }

    @Override
    protected float getDeathMaxRotation(Dullahan entityLivingBaseIn) {
        return 0.0F;
    }


}
