package com.moray.moraymobs.rendersandmodels.model.entities.monster;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.monster.Soulcatcher;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Soulcatchermodel extends GeoModel<Soulcatcher> {

    @Override
    public ResourceLocation getModelResource(Soulcatcher soulcatcher) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/soulcatcher.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Soulcatcher soulcatcher) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/soulcatcher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Soulcatcher soulcatcher) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/soulcatcher.animation.json");

    }
}
