package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.animal.Enderbowfin;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Bowfinmodel extends GeoModel<Enderbowfin> {
    @Override
    public ResourceLocation getModelResource(Enderbowfin enderbowfin) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/bowfin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Enderbowfin enderbowfin) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/enderbowfin.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Enderbowfin enderbowfin) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/bowfin.animation.json");

    }
}
