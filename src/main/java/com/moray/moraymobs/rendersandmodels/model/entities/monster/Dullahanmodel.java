package com.moray.moraymobs.rendersandmodels.model.entities.monster;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.monster.Dullahan;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Dullahanmodel extends GeoModel<Dullahan> {
    @Override
    public ResourceLocation getModelResource(Dullahan animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/dullahan.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Dullahan animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/dullahan.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Dullahan animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/dullahan.animation.json");
    }
}
