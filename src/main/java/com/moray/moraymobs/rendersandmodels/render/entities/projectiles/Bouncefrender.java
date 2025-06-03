package com.moray.moraymobs.rendersandmodels.render.entities.projectiles;

import com.moray.moraymobs.entity.projectiles.friendlyprojectile.Bouncy_ball_Friend;
import com.moray.moraymobs.rendersandmodels.model.entities.projectiles.Bounceballfmodel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Bouncefrender extends GeoEntityRenderer<Bouncy_ball_Friend> {
    public Bouncefrender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Bounceballfmodel());
    }

}
