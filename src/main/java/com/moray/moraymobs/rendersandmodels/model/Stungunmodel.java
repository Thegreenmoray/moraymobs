package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.item.Stungun;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Stungunmodel extends GeoModel<Stungun> {
    @Override
    public ResourceLocation getModelResource(Stungun stungun) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/stungun3.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Stungun stungun) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/item/stungun3.png");

    }

    @Override
    public ResourceLocation getAnimationResource(Stungun stungun) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/stungun3.animation.json");
    }
}

