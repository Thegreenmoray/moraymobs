package com.moray.moraymobs.rendersandmodels.model.entities.projectiles;

import com.moray.moraymobs.MorayMobs;
import com.moray.moraymobs.entity.projectiles.Boomerrang;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BoomerangModel extends GeoModel<Boomerrang> {
    @Override
    public ResourceLocation getModelResource(Boomerrang animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"geo/omniprojectile.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(Boomerrang animatable) {
        return  ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"textures/entity/boomerang.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Boomerrang animatable) {
        return ResourceLocation.fromNamespaceAndPath(MorayMobs.MODID,"animations/boomerang.spin.json");

    }
}
