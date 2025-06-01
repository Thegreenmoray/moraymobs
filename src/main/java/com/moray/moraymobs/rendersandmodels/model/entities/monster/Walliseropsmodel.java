package com.moray.moraymobs.rendersandmodels.model.entities.monster;


import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.dungeonentities.Walliserops;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Walliseropsmodel extends GeoModel<Walliserops> {
    @Override
    public ResourceLocation getModelResource(Walliserops animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/walliserops.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Walliserops animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/walliserops.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Walliserops animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/walliserops.animation.json");
    }
}
