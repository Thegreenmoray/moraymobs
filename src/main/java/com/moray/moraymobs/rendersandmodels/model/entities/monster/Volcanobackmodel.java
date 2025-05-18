package com.moray.moraymobs.rendersandmodels.model.entities.monster;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.monster.Volcanoback;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Volcanobackmodel extends GeoModel<Volcanoback> {
    @Override
    public ResourceLocation getModelResource(Volcanoback volcanoback) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/volcanoback.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Volcanoback volcanoback) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/volcanoback.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Volcanoback volcanoback) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/volcanoback.animation.json");
    }
}
