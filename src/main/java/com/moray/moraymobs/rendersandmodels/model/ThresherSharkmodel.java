package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.animal.Thresher_shark;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ThresherSharkmodel extends GeoModel<Thresher_shark> {
    @Override
    public ResourceLocation getModelResource(Thresher_shark thresherShark) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/thresher.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(Thresher_shark thresherShark) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/thresher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Thresher_shark thresherShark) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/thresher.animation.json");
    }
}
