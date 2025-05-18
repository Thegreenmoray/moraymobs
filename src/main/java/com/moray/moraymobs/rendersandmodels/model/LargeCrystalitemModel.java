package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.item.Largecrystalblockitem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LargeCrystalitemModel extends GeoModel<Largecrystalblockitem> {


    @Override
    public ResourceLocation getModelResource(Largecrystalblockitem animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/large_orb.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Largecrystalblockitem animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/item/large_orb.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Largecrystalblockitem animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/large_orb_spin.animation.json");
    }
}
