package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.animal.LavaPaddleFish;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Paddlefishmodel extends GeoModel<LavaPaddleFish> {
    @Override
    public ResourceLocation getModelResource(LavaPaddleFish lavaPaddleFish) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/lavapaddlefish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LavaPaddleFish lavaPaddleFish) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/paddlefish1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LavaPaddleFish lavaPaddleFish) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/lavapaddlefish.animation.json");

    }
}
