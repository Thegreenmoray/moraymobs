package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.Geyser;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Whirlpoolmodel extends GeoModel<Geyser> {

    @Override
    public ResourceLocation getModelResource(Geyser animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/pillar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Geyser animatable) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/whirlpool.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Geyser animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/pillar.animation.json");
    }
}
