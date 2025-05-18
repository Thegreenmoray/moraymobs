package com.moray.moraymobs.rendersandmodels.model.entities.animals;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.animal.Pronghorn;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Pronghornmodel extends GeoModel<Pronghorn> {
    @Override
    public ResourceLocation getModelResource(Pronghorn lavaPaddleFish) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/pronghorn.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pronghorn lavaPaddleFish) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/pronghorn.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Pronghorn lavaPaddleFish) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/pronghorn.animation.json");

    }
}
