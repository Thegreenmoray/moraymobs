package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.item.Beetlearmor;
import com.moray.moraymobs.item.Omnidensarmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class OmnidensArmorModel extends GeoModel<Omnidensarmor> {
    @Override
    public ResourceLocation getModelResource(Omnidensarmor animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/omnidens_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Omnidensarmor animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/armor/omnidens_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Omnidensarmor animatable) {
        return null;
    }
}
