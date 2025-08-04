package com.moray.moraymobs.rendersandmodels.model.entities.projectiles;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.Ambercrystal;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Ambercrystalmodel extends GeoModel<Ambercrystal> {
    @Override
    public ResourceLocation getModelResource(Ambercrystal ambercrystal) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/amberspike.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(Ambercrystal ambercrystal) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/ambercrystal1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Ambercrystal ambercrystal) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/amberspike.animation.json");

    }
}
