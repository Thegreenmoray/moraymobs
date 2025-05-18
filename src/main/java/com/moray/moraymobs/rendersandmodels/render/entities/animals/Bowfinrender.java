package com.moray.moraymobs.rendersandmodels.render.entities.animals;

import com.moray.moraymobs.entity.living.animal.Enderbowfin;
import com.moray.moraymobs.rendersandmodels.model.entities.animals.Bowfinmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Bowfinrender extends GeoEntityRenderer<Enderbowfin> {
    public Bowfinrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Bowfinmodel());
    }
}
