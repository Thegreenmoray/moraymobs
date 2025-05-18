package com.moray.moraymobs.rendersandmodels.render.entities.animals;

import com.moray.moraymobs.entity.living.animal.LavaPaddleFish;
import com.moray.moraymobs.rendersandmodels.model.entities.animals.Paddlefishmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Paddlefishrender extends GeoEntityRenderer<LavaPaddleFish> {
    public Paddlefishrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Paddlefishmodel());
    }
}
