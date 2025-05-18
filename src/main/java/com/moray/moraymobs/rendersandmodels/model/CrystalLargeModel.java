package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.block.blockentity.Largecrystalentity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CrystalLargeModel extends GeoModel<Largecrystalentity> {
    @Override
    public ResourceLocation getModelResource(Largecrystalentity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/large_orb.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Largecrystalentity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/block/large_orb.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Largecrystalentity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/large_orb.animation.json");
    }
}
