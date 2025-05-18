package com.moray.moraymobs.rendersandmodels.render.entities.projectiles;

import com.moray.moraymobs.entity.projectiles.Stunwave;
import com.moray.moraymobs.rendersandmodels.model.entities.projectiles.Stunwavemodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class StunwaveRender extends GeoEntityRenderer<Stunwave> {
    public StunwaveRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Stunwavemodel());
    }
}
