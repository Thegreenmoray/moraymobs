package com.moray.moraymobs.rendersandmodels.model.entities.projectiles;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.Bouncy_ball;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Bounceballmodel extends GeoModel<Bouncy_ball> {
    @Override
    public ResourceLocation getModelResource(Bouncy_ball animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/bouncy_ball.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Bouncy_ball animatable) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/bouncy_ball.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Bouncy_ball animatable) {
        return null;
    }
}
