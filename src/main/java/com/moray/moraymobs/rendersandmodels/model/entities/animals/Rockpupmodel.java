package com.moray.moraymobs.rendersandmodels.model.entities.animals;


import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.animalornpc.Rockpup;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Rockpupmodel extends GeoModel<Rockpup> {
    @Override
    public ResourceLocation getModelResource(Rockpup animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/rockpup.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Rockpup animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/rockpup.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Rockpup animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/rockpup_animation.json");
    }
}
