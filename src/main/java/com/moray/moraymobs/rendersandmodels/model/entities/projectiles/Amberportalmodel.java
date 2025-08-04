package com.moray.moraymobs.rendersandmodels.model.entities.projectiles;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.friendlyprojectile.Amberportal;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Amberportalmodel extends GeoModel<Amberportal> {
    @Override
    public ResourceLocation getModelResource(Amberportal amberportal) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/golemportal.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(Amberportal amberportal) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/amber_portal1.png");

    }

    @Override
    public ResourceLocation getAnimationResource(Amberportal amberportal) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/golemportal.animation.json");

    }
}
