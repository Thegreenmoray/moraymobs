package com.moray.moraymobs.rendersandmodels.model.entities.animals;


import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.animalornpc.Lamprey;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Lampreymodel extends GeoModel<Lamprey> {
    @Override
    public ResourceLocation getModelResource(Lamprey animatable) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,
                "geo/lamprey.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(Lamprey animatable) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,
                "textures/entity/lamprey.png");

    }

    @Override
    public ResourceLocation getAnimationResource(Lamprey animatable) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,
                "animations/lamprey.animation.json");

    }
}
