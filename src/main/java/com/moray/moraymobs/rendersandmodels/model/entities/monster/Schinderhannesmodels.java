package com.moray.moraymobs.rendersandmodels.model.entities.monster;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.living.dungeonentities.Schinderhannes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Schinderhannesmodels extends GeoModel<Schinderhannes> {
    @Override
    public ResourceLocation getModelResource(Schinderhannes animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/schinderhannes.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Schinderhannes animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/schinderhannes.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Schinderhannes animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/schinderhannes.animation.json");
    }
}
