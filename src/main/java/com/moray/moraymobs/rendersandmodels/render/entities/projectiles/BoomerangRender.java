package com.moray.moraymobs.rendersandmodels.render.entities.projectiles;

import com.moray.moraymobs.entity.projectiles.Boomerrang;
import com.moray.moraymobs.rendersandmodels.model.entities.projectiles.BoomerangModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BoomerangRender extends GeoEntityRenderer<Boomerrang> {
    public BoomerangRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BoomerangModel());
    }
}
