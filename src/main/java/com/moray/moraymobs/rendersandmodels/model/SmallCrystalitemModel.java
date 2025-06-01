package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.item.SmallCrystalitem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SmallCrystalitemModel extends GeoModel<SmallCrystalitem> {
    @Override
    public ResourceLocation getModelResource(SmallCrystalitem animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/small_orb.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SmallCrystalitem animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/item/small_orb.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SmallCrystalitem animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/small_orb_spin.animation.json");
    }
}
