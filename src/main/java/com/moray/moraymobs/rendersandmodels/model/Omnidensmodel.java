package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.boss.Omnidens;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Omnidensmodel extends GeoModel<Omnidens> {
    @Override
    public ResourceLocation getModelResource(Omnidens animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/omnidens.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(Omnidens animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/omnidens.png");

    }

    @Override
    public ResourceLocation getAnimationResource(Omnidens animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/omnidens.animation.json");
    }
}
