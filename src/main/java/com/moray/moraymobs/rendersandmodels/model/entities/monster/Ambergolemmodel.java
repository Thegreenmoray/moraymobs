package com.moray.moraymobs.rendersandmodels.model.entities.monster;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.monster.Amber_golem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Ambergolemmodel extends GeoModel<Amber_golem> {
    @Override
    public ResourceLocation getModelResource(Amber_golem animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/ambergolem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Amber_golem animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/ambergolem.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Amber_golem animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/ambergolem.animation.json");
    }
}
