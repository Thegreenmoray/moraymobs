package com.moray.moraymobs.rendersandmodels.model.entities.projectiles;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.friendlyprojectile.Bouncy_ball_Friend;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Bounceballfmodel extends GeoModel<Bouncy_ball_Friend> {
    @Override
    public ResourceLocation getModelResource(Bouncy_ball_Friend animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/bouncy_ball.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Bouncy_ball_Friend animatable) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/bouncy_ball.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Bouncy_ball_Friend animatable) {
        return null;
    }
}
