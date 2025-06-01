package com.moray.moraymobs.rendersandmodels.model;


import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.block.blockentity.Smallcrystalentity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SmallCrystalmodel extends GeoModel<Smallcrystalentity> {


    @Override
    public ResourceLocation getModelResource(Smallcrystalentity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/small_orb.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Smallcrystalentity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/item/small_orb.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Smallcrystalentity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/small_orb_spin.animation.json");
    }
}
