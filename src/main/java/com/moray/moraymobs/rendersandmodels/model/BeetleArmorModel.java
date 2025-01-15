package com.moray.moraymobs.rendersandmodels.model;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.item.Beetlearmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BeetleArmorModel extends GeoModel<Beetlearmor> {
    @Override
    public ResourceLocation getModelResource(Beetlearmor beetlearmor) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/beetlearmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Beetlearmor beetlearmor) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/armor/beetlearmor.png");

    }

    @Override
    public ResourceLocation getAnimationResource(Beetlearmor beetlearmor) {
        return null;
    }
}
