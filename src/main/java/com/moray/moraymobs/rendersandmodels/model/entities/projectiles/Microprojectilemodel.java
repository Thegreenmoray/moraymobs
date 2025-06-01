package com.moray.moraymobs.rendersandmodels.model.entities.projectiles;


import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.Microprojectile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Microprojectilemodel extends GeoModel<Microprojectile>  {

    @Override
    public ResourceLocation getModelResource(Microprojectile animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/microdictyon_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Microprojectile animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/microdictyon_projectile.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Microprojectile animatable) {
        return null;
    }
}
