package com.moray.moraymobs.rendersandmodels.render.entities.animals;

import com.moray.moraymobs.entity.living.animalornpc.Spriggan;
import com.moray.moraymobs.rendersandmodels.model.entities.animals.Sprigganmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Sprigganrender extends GeoEntityRenderer<Spriggan> {
    public Sprigganrender(EntityRendererProvider.Context context) {
        super(context, new Sprigganmodel());
    }


    @Override
    protected float getDeathMaxRotation(Spriggan entityLivingBaseIn) {
        return 0.0F;
    }


}
