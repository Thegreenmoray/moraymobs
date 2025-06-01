package com.moray.moraymobs.rendersandmodels.model.entities.monster;


import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.dungeonentities.Microdictyon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Microdictyonmodel extends GeoModel<Microdictyon> {
    @Override
    public ResourceLocation getModelResource(Microdictyon animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/microdictyon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Microdictyon animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/microdictyon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Microdictyon animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/microdictyon.animation.json");
    }
}
