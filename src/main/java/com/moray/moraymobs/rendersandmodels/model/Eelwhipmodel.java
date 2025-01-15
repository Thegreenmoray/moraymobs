package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.item.Eelwhip;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Eelwhipmodel extends GeoModel<Eelwhip> {
    @Override
    public ResourceLocation getModelResource(Eelwhip eelwhip) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/eelwhip.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Eelwhip eelwhip) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/item/eelwhip.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Eelwhip eelwhip) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/eelwhip.json");
    }
}
