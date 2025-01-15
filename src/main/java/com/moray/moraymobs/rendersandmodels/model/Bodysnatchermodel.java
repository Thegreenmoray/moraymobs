package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.monster.Body_Snatcher;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class Bodysnatchermodel extends GeoModel<Body_Snatcher> {


    @Override
    public ResourceLocation getModelResource(Body_Snatcher bodySnatcher) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/bodysnatcher.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Body_Snatcher bodySnatcher) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/bodysnatcher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Body_Snatcher bodySnatcher) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/bodysnatcher.animation.json");
    }
}
