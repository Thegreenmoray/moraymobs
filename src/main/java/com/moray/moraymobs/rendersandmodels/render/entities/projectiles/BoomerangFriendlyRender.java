package com.moray.moraymobs.rendersandmodels.render.entities.projectiles;

import com.moray.moraymobs.entity.projectiles.friendlyprojectile.Friendly_Boomerrang;
import com.moray.moraymobs.rendersandmodels.model.entities.projectiles.BoomerangfriendlyModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BoomerangFriendlyRender extends GeoEntityRenderer<Friendly_Boomerrang> {
    public BoomerangFriendlyRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BoomerangfriendlyModel());
    }
}
