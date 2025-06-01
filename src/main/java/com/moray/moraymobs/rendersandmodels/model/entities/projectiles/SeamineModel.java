package com.moray.moraymobs.rendersandmodels.model.entities.projectiles;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.Sea_Mine;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SeamineModel extends GeoModel<Sea_Mine> {
    @Override
    public ResourceLocation getModelResource(Sea_Mine animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/sea_mine.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Sea_Mine animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/sea_mine.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Sea_Mine animatable) {
        return null;
    }
}
